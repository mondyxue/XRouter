package com.mondyxue.xrouter.interceptor;

import com.mondyxue.xrouter.utils.RouteTypeUtils;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public abstract class LoginInterceptor extends RouteTypeInterceptor{

    @Override protected boolean onCheckRouteType(int routeTypes){
        return RouteTypeUtils.isLogin(routeTypes) && !isLogin();
    }

    public abstract boolean isLogin();

}
