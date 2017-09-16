package com.mondyxue.xrouter.utils;

import com.mondyxue.xrouter.navigator.ActivityNavigator;
import com.mondyxue.xrouter.navigator.FragmentNavigator;
import com.mondyxue.xrouter.navigator.Navigator;
import com.mondyxue.xrouter.navigator.ServiceNavigator;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public final class ClassUtils{

    public static boolean isExtendsOf(Class<?> returnType, Class<?> clazz){
        return returnType == clazz || clazz.isAssignableFrom(returnType);
    }

    public static boolean isNavigator(Class<?> returnType){
        return returnType == ActivityNavigator.class
               || returnType == FragmentNavigator.class
               || returnType == ServiceNavigator.class
               || returnType == Navigator.class;
    }

}
