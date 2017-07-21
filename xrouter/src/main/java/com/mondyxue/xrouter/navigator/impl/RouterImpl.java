package com.mondyxue.xrouter.navigator.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.util.LruCache;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.callback.NavigationCallbackWrapper;
import com.mondyxue.xrouter.callback.RouteCallback;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.navigator.Router;
import com.mondyxue.xrouter.service.ActivityManager;
import com.mondyxue.xrouter.service.Scheduler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
@Route(path = Router.PATH, extras = RouteType.GreenService)
public class RouterImpl implements IProvider, Router, ActivityManager.OnActivityResultListener{

    private ActivityManager mActivityManager;
    private LruCache<String,Object> mServices;
    private LruCache<Class<?>,Object> mNavigators;
    private Map<Activity,RouteCallback> mRouteCallbacks;
    private Scheduler mScheduler;

    @Override public void init(Context context){}
    @Override public Context getContext(){
        return getActivityManager().getContext();
    }
    @Override public ActivityManager getActivityManager(){
        if(mActivityManager == null){
            mActivityManager = build(ActivityManager.PATH).navigator().service();
        }
        return mActivityManager;
    }
    @Override public void setActivityManager(ActivityManager activityManager){
        mActivityManager = activityManager;
    }
    @Override public Scheduler getScheduler(){
        if(mScheduler == null){
            mScheduler = build(Scheduler.PATH).navigator().service();
        }
        return mScheduler;
    }
    @Override public void setScheduler(Scheduler scheduler){
        mScheduler = scheduler;
    }

    @Override public <T> T create(final Class<T> navigator){
        if(!navigator.isInterface()){
            throw new IllegalArgumentException("navigator declarations must be interfaces.");
        }else if(navigator.getInterfaces().length > 0){
            throw new IllegalArgumentException("navigator interfaces must not extend other interfaces.");
        }
        LruCache<Class<?>,Object> navigators = getNavigators();
        Object o = navigators.get(navigator);
        if(o == null){
            o = Proxy.newProxyInstance(navigator.getClassLoader(), new Class[]{navigator}, new InvocationHandler(){
                LruCache<Method,NavigationMethod> mNavigatorMethods;
                @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
                    if(mNavigatorMethods == null){
                        mNavigatorMethods = new LruCache<>(5);
                    }
                    NavigationMethod navigationMethod = mNavigatorMethods.get(method);
                    if(navigationMethod == null){
                        navigationMethod = new NavigationMethod(method);
                        mNavigatorMethods.put(method, navigationMethod);
                    }
                    return navigationMethod.invoke(args);
                }
            });
            navigators.put(navigator, o);
        }
        return (T) o;
    }

    @Override public <T> T service(String path){
        LruCache<String,Object> routeServices = getServices();
        Object o = routeServices.get(path);
        if(o == null){
            o = build(path).navigator().service();
            if(o != null){
                routeServices.put(path, o);
            }
        }
        return (T) o;
    }

    @Override public void startActivityForResult(Postcard postcard, int requestCode, RouteCallback callback){
        final Context context = getContext();
        XRouter.getRouter().getActivityManager().addOnActivityResultListener(RouterImpl.this);
        if(context instanceof Activity && requestCode > 0){
            Activity activity = (Activity) context;
            if(callback != null){
                getRouteCallbacks().put(activity, callback);
            }
            Intent intent = activity.getIntent();
            if(intent != null){
                postcard.withString(RouteExtras.PathFrom, intent.getStringExtra(RouteExtras.PathTo));
            }
            postcard.withInt(RouteExtras.RequestCode, requestCode);
            postcard.navigation(activity, requestCode, new NavigationCallbackWrapper(new NavigationCallback(){
                @Override public void onLost(Postcard postcard){
                    RouteCallback routeCallback = getRouteCallbacks().remove(context);
                    if(routeCallback != null){
                        routeCallback.onCancel();
                    }
                }
                @Override public void onFound(Postcard postcard){}
                @Override public void onArrival(Postcard postcard){}
                @Override public void onInterrupt(Postcard postcard){}
            }));
        }else{
            postcard.navigation(context, new NavigationCallbackWrapper());
        }
    }

    @Override public void onActivityResult(Activity context, int requestCode, int resultCode, Intent data){
        if(context != null){
            RouteCallback routeCallback = getRouteCallbacks().remove(context);
            if(routeCallback != null){
                routeCallback.onResponse(requestCode, resultCode, data);
            }
        }
    }

    @Override public NavigatorBuilder build(String path){
        return new NavigatorBuilder(path);
    }
    @Override public NavigatorBuilder build(Uri uri){
        return new NavigatorBuilder(uri);
    }
    @Override public NavigatorBuilder build(Intent intent){
        Uri data = intent.getData();
        if(data == null){
            String path = intent.getStringExtra(RouteExtras.PathTo);
            if(path != null){
                return XRouter.getRouter().build(path);
            }
        }else{
            return build(data);
        }
        throw new IllegalArgumentException("can't create a NavigatorBuilder without uri and path");
    }

    private LruCache<String,Object> getServices(){
        if(mServices == null){
            mServices = new LruCache<>(6);
        }
        return mServices;
    }
    private LruCache<Class<?>,Object> getNavigators(){
        if(mNavigators == null){
            mNavigators = new LruCache<>(5);
        }
        return mNavigators;
    }
    private Map<Activity,RouteCallback> getRouteCallbacks(){
        if(mRouteCallbacks == null){
            mRouteCallbacks = new HashMap<>();
        }
        return mRouteCallbacks;
    }

}
