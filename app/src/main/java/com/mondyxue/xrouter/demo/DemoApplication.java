package com.mondyxue.xrouter.demo;

import android.app.Application;

import com.mondyxue.xrouter.XRouter;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public class DemoApplication extends Application{

    @Override public void onCreate(){
        super.onCreate();

        // init XRouter
        XRouter.setScheme("xrouter");
        XRouter.setAuthority("mondyxue.github.io");
        XRouter.init(DemoApplication.this);

    }

}
