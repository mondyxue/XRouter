package com.mondyxue.xrouter.service;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.mondyxue.xrouter.data.IBundleWrapper;
import com.mondyxue.xrouter.navigator.impl.NavigatorBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * This for custom route process and adapt
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface RouteProcessor extends IProvider{

    /**
     * custrom process for method annotations and extras
     * @param methodAnnotations route method annotations
     * @param extras            extras for navigation
     */
    void processExtras(Annotation[] methodAnnotations, IBundleWrapper extras);

    /**
     * adapt custom return type
     * @param returnType       method's return type
     * @param navigatorBuilder a builder for building navigator
     * @return method's return type
     */
    Object invokeMethod(Type returnType, NavigatorBuilder navigatorBuilder);

}
