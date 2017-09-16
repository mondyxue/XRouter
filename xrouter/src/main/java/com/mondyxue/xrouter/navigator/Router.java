package com.mondyxue.xrouter.navigator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.mondyxue.xrouter.callback.RouteCallback;
import com.mondyxue.xrouter.navigator.impl.NavigatorBuilder;
import com.mondyxue.xrouter.service.ActivityManager;
import com.mondyxue.xrouter.service.Scheduler;

/**
 * Router with support features
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface Router{

    String PATH = "/xrouter/navigator";

    /**
     * Inject params and services
     * @param container Activity or Fragment
     */
    void inject(Object container);

    /**
     * Create a navigator with interfaces
     * @param navigator the interfaces
     * @return the delegate of your navigator interfaces
     */
    <T> T create(Class<T> navigator);

    /** Getting a service with path */
    <T> T service(String path);

    /** Getting a service with interfaces */
    <T extends IProvider> T service(Class<T> service);

    /** Create a {@link NavigatorBuilder} with intent */
    NavigatorBuilder build(Intent intent);

    /** Create a {@link NavigatorBuilder} with path */
    NavigatorBuilder build(String path);

    /** Create a {@link NavigatorBuilder} with uri */
    NavigatorBuilder build(Uri uri);

    void startActivity(Postcard postcard);

    /**
     * Invoke startActivityForResult with a callback
     * @param postcard    route postcard
     * @param requestCode requestCode
     * @param callback    callback for getting the result and data
     */
    void startActivityForResult(Postcard postcard, int requestCode, RouteCallback callback);

    /** Getting the top activity or application context */
    Context getContext();

    ActivityManager getActivityManager();
    void setActivityManager(ActivityManager activityManager);

    Scheduler getScheduler();
    void setScheduler(Scheduler scheduler);

    <PARSER extends SerializationService> PARSER getSerializationService();
    <PARSER extends SerializationService> void setSerializationService(PARSER parser);

}
