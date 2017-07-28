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
import com.alibaba.android.arouter.launcher.ARouter;
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
 * The implementation of {@link Router}
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Route(path = Router.PATH, extras = RouteType.GreenService)
public class RouterImpl implements IProvider, Router, ActivityManager.OnActivityResultListener{

    private Scheduler mScheduler;
    private ActivityManager mActivityManager;

    private LruCache<String,Object> mServiceCache;
    private LruCache<Class<?>,Object> mNavigatorCache;
    private Map<Activity,RouteCallback> mRouteCallbackHolder;

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

    @Override public void inject(Object container){
        ARouter.getInstance().inject(container);
    }

    @Override public <T> T create(final Class<T> navigator){
        if(!navigator.isInterface()){
            throw new IllegalArgumentException("navigator declarations must be interfaces.");
        }else if(navigator.getInterfaces().length > 0){
            throw new IllegalArgumentException("navigator interfaces must not extend other interfaces.");
        }
        LruCache<Class<?>,Object> navigators = getNavigatorCache();
        Object o = navigators.get(navigator);
        if(o == null){
            // create the proxy for navigator interfaces
            o = Proxy.newProxyInstance(navigator.getClassLoader(), new Class[]{navigator}, new InvocationHandler(){
                LruCache<Method,NavigationMethod> mNavigatorMethods;
                @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
                    if(mNavigatorMethods == null){
                        mNavigatorMethods = new LruCache<>(5);
                    }
                    NavigationMethod navigationMethod = mNavigatorMethods.get(method);
                    if(navigationMethod == null){
                        // create method's processer
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
        LruCache<String,Object> routeServices = getServiceCache();
        Object o = routeServices.get(path);
        if(o == null){
            // navigating with service route
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
                // hold the callback
                getRouteCallbackHolder().put(activity, callback);
            }
            Intent intent = activity.getIntent();
            if(intent != null){
                // with the path from
                postcard.withString(RouteExtras.PathFrom, intent.getStringExtra(RouteExtras.PathTo));
            }
            // with request code
            postcard.withInt(RouteExtras.RequestCode, requestCode);
            postcard.navigation(activity, requestCode, new NavigationCallbackWrapper(new NavigationCallback(){
                @Override public void onLost(Postcard postcard){
                    // remove the callback and invoke onCancel when the postcard lost
                    RouteCallback routeCallback = getRouteCallbackHolder().remove(context);
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
            //dispatch the callback
            RouteCallback routeCallback = getRouteCallbackHolder().remove(context);
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
                return build(path).with(intent.getExtras());
            }
        }else{
            return build(data).with(intent.getExtras());
        }
        throw new IllegalArgumentException("can't create a NavigatorBuilder without uri and path");
    }

    /** get or create services cache */
    private LruCache<String,Object> getServiceCache(){
        if(mServiceCache == null){
            mServiceCache = new LruCache<>(6);
        }
        return mServiceCache;
    }
    /** get or create navigators cache */
    private LruCache<Class<?>,Object> getNavigatorCache(){
        if(mNavigatorCache == null){
            mNavigatorCache = new LruCache<>(5);
        }
        return mNavigatorCache;
    }
    /** get or create callbacks holder */
    private Map<Activity,RouteCallback> getRouteCallbackHolder(){
        if(mRouteCallbackHolder == null){
            mRouteCallbackHolder = new HashMap<>();
        }
        return mRouteCallbackHolder;
    }

}
