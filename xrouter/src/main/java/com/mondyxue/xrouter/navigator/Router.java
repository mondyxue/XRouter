package com.mondyxue.xrouter.navigator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.alibaba.android.arouter.facade.Postcard;
import com.mondyxue.xrouter.callback.RouteCallback;
import com.mondyxue.xrouter.navigator.impl.NavigatorBuilder;
import com.mondyxue.xrouter.service.ActivityManager;
import com.mondyxue.xrouter.service.Scheduler;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public interface Router{

    String PATH = "/xrouter/navigator";
    String FRAGMENT_CONTAINER = "/xrouter/page/container";

    <T> T create(Class<T> navigator);

    <T> T service(String path);

    NavigatorBuilder build(Intent intent);
    NavigatorBuilder build(String path);
    NavigatorBuilder build(Uri uri);

    void startActivityForResult(Postcard postcard, int requestCode, RouteCallback callback);

    Context getContext();

    ActivityManager getActivityManager();
    void setActivityManager(ActivityManager activityManager);

    Scheduler getScheduler();
    void setScheduler(Scheduler scheduler);

}
