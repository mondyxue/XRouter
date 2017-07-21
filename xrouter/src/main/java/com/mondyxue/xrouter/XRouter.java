package com.mondyxue.xrouter;

import android.app.Application;

import com.alibaba.android.arouter.facade.template.ILogger;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mondyxue.xrouter.navigator.Router;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public class XRouter{

    public static String SCHEME = "togojoy";
    public static String AUTHORITY = "router";

    private static Router sRouter;

    public static Router getRouter(){
        if(sRouter == null){
            sRouter = (Router) ARouter.getInstance().build(Router.PATH).navigation();
        }
        return sRouter;
    }

    public static void init(Application application){
        ARouter.init(application);
    }

    public static void setScheme(String scheme){
        SCHEME = scheme;
    }
    public static void setAuthority(String authority){
        AUTHORITY = authority;
    }

    public static synchronized void openDebug(){
        ARouter.openDebug();
    }

    public static boolean debuggable(){
        return ARouter.debuggable();
    }

    public static synchronized void openLog(){
        ARouter.openLog();
    }

    public static synchronized void printStackTrace(){
        ARouter.printStackTrace();
    }

    public static synchronized void setExecutor(ThreadPoolExecutor tpe){
        ARouter.setExecutor(tpe);
    }

    public static boolean canAutoInject(){
        return ARouter.canAutoInject();
    }

    public static synchronized void monitorMode(){
        ARouter.monitorMode();
    }

    public static boolean isMonitorMode(){
        return ARouter.isMonitorMode();
    }

    public static void setLogger(ILogger userLogger){
        ARouter.setLogger(userLogger);
    }

}
