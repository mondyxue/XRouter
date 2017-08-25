package com.mondyxue.xrouter.navigator.impl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.annotation.DefaultExtras;
import com.mondyxue.xrouter.annotation.Extra;
import com.mondyxue.xrouter.annotation.Extras;
import com.mondyxue.xrouter.annotation.Route;
import com.mondyxue.xrouter.annotation.Transition;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.data.BundleWrapper;
import com.mondyxue.xrouter.data.IBundleWrapper;
import com.mondyxue.xrouter.exception.SerializationException;
import com.mondyxue.xrouter.navigator.ActivityNavigator;
import com.mondyxue.xrouter.navigator.FragmentNavigator;
import com.mondyxue.xrouter.navigator.Navigator;
import com.mondyxue.xrouter.navigator.ServiceNavigator;
import com.mondyxue.xrouter.utils.TypeUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * A helper for invoking navigator's method
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
final class NavigationMethod{

    private Class<?> mReturnType;
    private Annotation[][] mParameterAnnotations;

    private Route mRoute;
    private DefaultExtras mDefaultExtras;
    private Transition mTransition;

    NavigationMethod(Method method){
        // init annotations
        mReturnType = method.getReturnType();
        mParameterAnnotations = method.getParameterAnnotations();
        Annotation[] methodAnnotations = method.getAnnotations();
        for(Annotation annotation : methodAnnotations){
            if(annotation instanceof Route){
                mRoute = (Route) annotation;
            }else if(annotation instanceof DefaultExtras){
                mDefaultExtras = (DefaultExtras) annotation;
            }else if(annotation instanceof Transition){
                mTransition = (Transition) annotation;
            }
        }
        if(mRoute == null){
            throw new RuntimeException("no Route annotation found");
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

        NavigatorBuilder builder = XRouter.getRouter().build(mRoute.path());

        IBundleWrapper extras = processExtras(args);
        if(extras != null && !extras.isEmpty()){
            builder.with(extras.getBundle());
        }
        if(mTransition != null){
            builder.withTransition(mTransition.enterAnim(), mTransition.exitAnim());
        }

        Navigator navigator = builder.setGreenChannel(mRoute.greenChannel())
                                     .withRequestCode(mRoute.requestCode())
                                     .withFlags(mRoute.flags())
                                     .navigator();

        // check to return
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
            Object service = XRouter.getRouter().service(mRoute.path());
            if(service == null){
                throw new RuntimeException("no route found: " + mRoute.path());
            }else{
                if(isExtendsOf(service.getClass(), mReturnType)){
                    return service;
                }else{
                    throw new ClassCastException(
                            "Couldn't convert " + service.getClass().getCanonicalName()
                            + " to " + mReturnType.getCanonicalName()
                    );
                }
            }
        }
        return navigator;

    }

    private IBundleWrapper processExtras(Object[] args){
        IBundleWrapper bundle = new BundleWrapper();

        bundle.put(RouteExtras.Title, mRoute.title());

        // process default extras
        if(mDefaultExtras != null){
            int[] types = mDefaultExtras.type();
            String[] keys = mDefaultExtras.key();
            String[] values = mDefaultExtras.value();
            if(types.length != keys.length || types.length != values.length){
                throw new IllegalArgumentException("default extras must be one-to-one correspondence");
            }else{
                for(int i = 0; i < types.length; i++){
                    String key = keys[i];
                    String value = values[i];
                    Object parsedValue = TypeUtils.parse(types[i], value);
                    if(parsedValue == null){
                        throw new IllegalArgumentException("Unsupported default extra {" + key + ":" + value + "}");
                    }else{
                        bundle.put(key, parsedValue);
                    }
                }
            }
        }

        // process parameter annotations
        for(int i = 0; i < mParameterAnnotations.length; i++){
            Annotation[] annotations = mParameterAnnotations[i];
            Object arg = args[i];
            if(arg == null){
                continue;
            }
            boolean hasExtraAnnotation = false;
            for(Annotation annotation : annotations){
                if(annotation instanceof Extra){
                    Extra annotationExtra = (Extra) annotation;
                    String key = annotationExtra.value();
                    if(annotationExtra.serializable() && ((arg instanceof Serializable) || (arg instanceof Parcelable))){
                        bundle.put(key, arg);
                    }else{
                        SerializationService service = XRouter.getRouter().getSerializationService();
                        if(service != null){
                            bundle.put(key, service.object2Json(arg));
                        }else{
                            throw new SerializationException(key, arg);
                        }
                    }
                    hasExtraAnnotation = true;
                }else if(annotation instanceof Extras){
                    if(hasExtraAnnotation){
                        throw new RuntimeException("@Extra and @Extras can't work together.");
                    }
                    if(arg instanceof Bundle){
                        bundle.put((Bundle) arg);
                    }else if(arg instanceof Map){
                        if(((Map) arg).size() > 0){
                            Map mapExtra = (Map) arg;
                            for(Object key : mapExtra.keySet()){
                                if(key instanceof String){
                                    bundle.put((String) key, mapExtra.get(key));
                                }else{
                                    throw new IllegalArgumentException("Unsupported key [" + key + "], it must be a String.");
                                }
                            }
                        }
                    }else{
                        throw new IllegalArgumentException("Unsupported extras:" + arg + ", is should be Bundle or Map<String,Object>");
                    }
                    break;
                }
            }
        }

        return bundle;
    }

}
