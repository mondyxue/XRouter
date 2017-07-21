package com.togojoy.trouter.navigator.impl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.togojoy.trouter.TRouter;
import com.togojoy.trouter.annotation.DefaultExtras;
import com.togojoy.trouter.annotation.Extra;
import com.togojoy.trouter.annotation.Extras;
import com.togojoy.trouter.annotation.Route;
import com.togojoy.trouter.data.BundleWrapper;
import com.togojoy.trouter.data.IBundleWrapper;
import com.togojoy.trouter.navigator.ActivityNavigator;
import com.togojoy.trouter.navigator.FragmentNavigator;
import com.togojoy.trouter.navigator.Navigator;
import com.togojoy.trouter.navigator.ServiceNavigator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
final class NavigationMethod{

    private Class<?> mReturnType;
    private Annotation[][] mParameterAnnotations;

    private Route mRoute;
    private DefaultExtras mDefaultExtras;

    NavigationMethod(Method method){
        mReturnType = method.getReturnType();
        mParameterAnnotations = method.getParameterAnnotations();
        Annotation[] methodAnnotations = method.getAnnotations();
        for(Annotation annotation : methodAnnotations){
            if(annotation instanceof Route){
                mRoute = (Route) annotation;
            }else if(annotation instanceof DefaultExtras){
                mDefaultExtras = (DefaultExtras) annotation;
            }
        }
        if(mRoute == null){
            throw new IllegalArgumentException("no Route annotation found");
        }
    }

    private static boolean isExtendsOf(Class<?> returnType, Class<?> clazz){
        return returnType == clazz || clazz.isAssignableFrom(returnType);
    }
    private static boolean isNavigator(Class<?> returnType){
        return returnType == ActivityNavigator.class
               || returnType == FragmentNavigator.class
               || returnType == ServiceNavigator.class
               || returnType == Navigator.class;
    }

    Object invoke(Object[] args){

        NavigatorBuilder builder = TRouter.getRouter().build(mRoute.path());

        IBundleWrapper extras = processExtras(args);
        if(extras != null && !extras.isEmpty()){
            builder.with(extras.getBundle());
        }

        Navigator navigator = builder.setGreenChannel(mRoute.greenChannel())
                                     .withRequestCode(mRoute.requestCode())
                                     .withFlags(mRoute.flags())
                                     .navigator();

        Class<?> returnType = mReturnType;
        if(returnType == Void.TYPE){
            navigator.startActivity();
        }else if(isNavigator(returnType)){
            return navigator;
        }else if(isExtendsOf(returnType, Fragment.class)){
            return navigator.fragment();
        }else if(returnType == Intent.class){
            return navigator.intent();
        }else if(returnType == Uri.class){
            return navigator.uri();
        }else{
            IProvider service = TRouter.getRouter().service(mRoute.path());
            if(service == null){
                throw new RuntimeException(
                        new ClassNotFoundException("no route find: " + mRoute.path())
                );
            }else{
                if(isExtendsOf(service.getClass(), mReturnType)){
                    return service;
                }else{
                    throw new RuntimeException(
                            new ClassCastException(
                                    "Couldn't convert " + service.getClass().getCanonicalName()
                                    + " to " + mReturnType.getCanonicalName()
                            )
                    );
                }
            }
        }
        return navigator;

    }

    private IBundleWrapper processExtras(Object[] args){
        IBundleWrapper bundle = new BundleWrapper();
        for(int i = 0; i < mParameterAnnotations.length; i++){
            Annotation[] annotations = mParameterAnnotations[i];
            for(Annotation annotation : annotations){
                Object arg = args[i];
                if(annotation instanceof Extra){
                    String key = ((Extra) annotation).value();
                    try{
                        bundle.put(key, arg);
                    }catch(Exception e){
                        throw new IllegalArgumentException("unsupport extra:" + key + "=" + arg + " in Bundle", e);
                    }
                }else if(annotation instanceof Extras){
                    if(arg instanceof Bundle){
                        bundle.put((Bundle) arg);
                    }else if(arg instanceof Map){
                        Map<String,Object> extrasMap;
                        try{
                            extrasMap = ((Map<String,Object>) arg);
                        }catch(Exception e){
                            throw new IllegalArgumentException("@Extras only support Map<String,Object>", e);
                        }
                        for(String key : extrasMap.keySet()){
                            bundle.put(key, extrasMap.get(key));
                        }
                    }else{
                        throw new IllegalArgumentException("unsupport extras:" + arg);
                    }
                }
            }
        }
        if(mDefaultExtras != null){
            int[] types = mDefaultExtras.type();
            String[] keys = mDefaultExtras.key();
            String[] values = mDefaultExtras.value();
            if(types.length != keys.length || types.length != values.length){
                throw new IllegalArgumentException("default extras must be one-to-one correspondence");
            }else{
                for(int i = 0; i < types.length; i++){
                    bundle.put(types[i], keys[i], values[i]);
                }
            }
        }
        return bundle;
    }

}
