package com.mondyxue.xrouter.demo.base.service.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.api.navigator.BaseNavigator;
import com.mondyxue.xrouter.demo.api.service.Logger;

/**
 * The implementation of {@link Logger}
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Route(path = BaseNavigator._Logger, extras = RouteType.GreenService)
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
