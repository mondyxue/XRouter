package com.togojoy.trouter.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
@IntDef(flag = true, value = {
        RouteType.GreenChannel,
        RouteType.Fragment,
        RouteType.Activity,
        RouteType.Service,
        RouteType.WithinTitlebar,
        RouteType.TitlebarFragment,
        RouteType.Login,
        RouteType.Main,
        RouteType.LoginFragment,
        RouteType.LoginActivity,
        RouteType.MainFragment,
        RouteType.GreenService,
        RouteType.MainActivity
})
@Retention(RetentionPolicy.CLASS)
public @interface RouteType{

    int GreenChannel = 1;
    int Fragment = 1 << 1;
    int Activity = 1 << 2;
    int Service = 1 << 3;
    int WithinTitlebar = 1 << 4;
    int Login = 1 << 5;
    int Main = 1 << 6;

    int TitlebarFragment = Fragment | WithinTitlebar;
    int LoginFragment = Fragment | Login;
    int MainFragment = Fragment | Main;
    int MainActivity = Activity | Main;
    int LoginActivity = Activity | Login;
    int GreenService = Service | GreenChannel;

}
