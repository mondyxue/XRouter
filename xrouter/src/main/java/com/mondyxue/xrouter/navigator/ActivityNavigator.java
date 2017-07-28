package com.mondyxue.xrouter.navigator;

import android.content.Intent;
import android.net.Uri;

import com.mondyxue.xrouter.callback.RouteCallback;

/**
 * A activity navigator
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface ActivityNavigator<T>{

    void startActivity();

    /**
     * startActivityForResult with callback
     * @param callback callback for processing result
     */
    void startActivityForResult(RouteCallback<T> callback);

    /** return the target uri */
    Uri uri();

    /** return the reforward intent */
    Intent intent();

}
