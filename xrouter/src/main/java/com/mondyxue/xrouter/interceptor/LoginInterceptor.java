package com.mondyxue.xrouter.interceptor;

import com.mondyxue.xrouter.utils.RouteTypeUtils;

/**
 * A interceptor for checking {@link com.mondyxue.xrouter.constant.RouteType#Login}
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public abstract class LoginInterceptor extends RouteTypeInterceptor{

    @Override protected boolean onCheckRouteType(int routeTypes){
        return RouteTypeUtils.isLogin(routeTypes) && !isLogin();
    }

    public abstract boolean isLogin();

}
