package com.mondyxue.xrouter.demo;

import android.app.Application;

import com.mondyxue.xrouter.XRouter;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public class DemoApplication extends Application{

    @Override public void onCreate(){
        super.onCreate();

        XRouter.setScheme("xrouter");
        XRouter.setAuthority("mondyxue.github.io");
        XRouter.init(DemoApplication.this);

    }

}
