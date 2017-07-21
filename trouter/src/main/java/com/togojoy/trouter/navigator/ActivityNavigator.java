package com.togojoy.trouter.navigator;

import android.content.Intent;
import android.net.Uri;

import com.togojoy.trouter.callback.RouteCallback;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public interface ActivityNavigator<T>{

    void startActivity();
    void startActivityForResult(RouteCallback<T> callback);

    Uri uri();
    Intent intent();

}
