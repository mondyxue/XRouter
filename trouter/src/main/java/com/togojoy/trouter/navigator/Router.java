package com.togojoy.trouter.navigator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.togojoy.trouter.callback.RouteCallback;
import com.togojoy.trouter.navigator.impl.NavigatorBuilder;
import com.togojoy.trouter.service.ActivityManager;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public interface Router{

    String PATH = "/trouter/navigator";

    Context getContext();

    ActivityManager getActivityManager();

    <T> T create(Class<T> navigator);

    <T extends IProvider> T service(String path);

    NavigatorBuilder build(Intent intent);
    NavigatorBuilder build(String path);
    NavigatorBuilder build(Uri uri);

    void startActivityForResult(Postcard postcard, int requestCode, RouteCallback callback);

}
