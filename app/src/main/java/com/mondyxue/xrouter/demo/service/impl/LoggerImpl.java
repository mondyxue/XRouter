package com.mondyxue.xrouter.demo.service.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.navigator.DemoNavigator;
import com.mondyxue.xrouter.demo.service.Logger;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
@Route(path = DemoNavigator._Logger, extras = RouteType.GreenService)
public class LoggerImpl implements Logger, IProvider{

    @Override public void e(String tag, @NonNull String... msgs){
        StringBuilder msgBuilder = new StringBuilder();
        for(String msg : msgs){
            msgBuilder.append(">").append(msg);
        }
        Log.e(tag, msgBuilder.toString());
    }

    @Override public void init(Context context){}

}
