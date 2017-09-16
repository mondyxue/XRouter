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
import com.mondyxue.xrouter.service.RouteProcessor;
import com.mondyxue.xrouter.utils.ClassUtils;
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

    private final Method mMethod;

    NavigationMethod(Method method){
        mMethod = method;
    }

    Object invoke(Object[] args){

        Route routeAnnotation = mMethod.getAnnotation(Route.class);
        if(routeAnnotation == null){
            throw new RuntimeException("no @Route annotation found");
        }

        NavigatorBuilder builder = XRouter.getRouter().build(routeAnnotation.path());

        IBundleWrapper extras = processExtras(args);

        // custrom process for method annotations and extras
        RouteProcessor routeProcessor = XRouter.getRouter().service(RouteProcessor.class);
        if(routeProcessor != null){
            routeProcessor.processExtras(mMethod.getAnnotations(), extras);
        }

        if(!extras.isEmpty()){
            builder.with(extras.getBundle());
        }

        Transition transitionAnnotation = mMethod.getAnnotation(Transition.class);
        if(transitionAnnotation != null){
            builder.withTransition(transitionAnnotation.enterAnim(), transitionAnnotation.exitAnim());
        }
        builder.withString(RouteExtras.Title, routeAnnotation.title())
               .setGreenChannel(routeAnnotation.greenChannel())
               .withRequestCode(routeAnnotation.requestCode())
               .withFlags(routeAnnotation.flags());

        // check to invoke
        Class<?> returnType = mMethod.getReturnType();
        if(returnType == Void.TYPE){
            builder.navigator().startActivity();
        }else if(ClassUtils.isNavigator(returnType)){
            return builder.navigator();
        }else if(ClassUtils.isExtendsOf(returnType, Fragment.class)){
            return builder.navigator().fragment();
        }else if(returnType == Intent.class){
            return builder.navigator().intent();
        }else if(returnType == Uri.class){
            return builder.navigator().uri();
        }else{
            Object service = XRouter.getRouter().service(routeAnnotation.path());
            if(service == null){
                throw new RuntimeException("no route found: " + routeAnnotation.path());
            }else{
                if(ClassUtils.isExtendsOf(service.getClass(), returnType)){
                    return service;
                }else{
                    throw new ClassCastException(
                            "Couldn't convert " + service.getClass().getCanonicalName()
                            + " to " + returnType.getCanonicalName()
                    );
                }
            }
        }

        if(routeProcessor == null){
            // default invoke
            return builder.navigator();
        }else{
            // custom invoke
            return routeProcessor.invokeMethod(returnType, builder);
        }

    }

    private IBundleWrapper processExtras(Object[] args){

        IBundleWrapper extras = new BundleWrapper();

        // process default extras
        DefaultExtras extrasAnnotation = mMethod.getAnnotation(DefaultExtras.class);
        if(extrasAnnotation != null){
            int[] types = extrasAnnotation.type();
            String[] keys = extrasAnnotation.key();
            String[] values = extrasAnnotation.value();
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
                        extras.put(key, parsedValue);
                    }
                }
            }
        }

        // process parameter annotations
        Annotation[][] parameterAnnotations = mMethod.getParameterAnnotations();
        for(int i = 0; i < parameterAnnotations.length; i++){
            Annotation[] annotations = parameterAnnotations[i];
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
                        extras.put(key, arg);
                    }else{
                        SerializationService service = XRouter.getRouter().getSerializationService();
                        if(service != null){
                            extras.put(key, service.object2Json(arg));
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
                        extras.put((Bundle) arg);
                    }else if(arg instanceof Map){
                        if(((Map) arg).size() > 0){
                            Map mapExtra = (Map) arg;
                            for(Object key : mapExtra.keySet()){
                                if(key instanceof String){
                                    extras.put((String) key, mapExtra.get(key));
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

        return extras;
    }

}
