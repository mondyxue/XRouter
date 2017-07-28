package com.mondyxue.xrouter.utils;

import com.mondyxue.xrouter.constant.RouteType;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public final class RouteTypeUtils{

    private RouteTypeUtils(){}

    public static boolean isGreen(int type){
        return equlas(type, RouteType.GreenChannel);
    }
    public static boolean isFragment(int type){
        return equlas(type, RouteType.Fragment);
    }
    public static boolean isWithinTitlebar(int type){
        return equlas(type, RouteType.WithinTitlebar);
    }
    public static boolean isLogin(int type){
        return equlas(type, RouteType.Login);
    }
    public static boolean isMain(int type){
        return equlas(type, RouteType.Main);
    }

    public static boolean equlas(int a, int b){
        return (a & b) == b;
    }

}
