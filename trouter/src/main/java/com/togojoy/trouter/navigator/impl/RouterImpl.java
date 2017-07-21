package com.togojoy.trouter.navigator.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.util.LruCache;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.togojoy.trouter.TRouter;
import com.togojoy.trouter.callback.NavigationCallbackWrapper;
import com.togojoy.trouter.callback.RouteCallback;
import com.togojoy.trouter.constant.RouteExtras;
import com.togojoy.trouter.constant.RouteType;
import com.togojoy.trouter.navigator.Router;
import com.togojoy.trouter.service.ActivityManager;

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

    @Override public <T extends IProvider> T service(String path){
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
        TRouter.getRouter().getActivityManager().addOnActivityResultListener(RouterImpl.this);
        if(context instanceof Activity && requestCode > 0){
            Activity activity = (Activity) context;
            if(callback != null){
                getRouteCallbacks().put(activity, callback);
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
            postcard.navigation(context, new NavigationCallbackWrapper(null));
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
            String path = intent.getStringExtra(RouteExtras.Path);
            if(path != null){
                return TRouter.getRouter().build(path);
            }
        }else{
            return build(data);
        }
        throw new IllegalArgumentException("can't create a NavigatorBuilder with no uri or path");
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
