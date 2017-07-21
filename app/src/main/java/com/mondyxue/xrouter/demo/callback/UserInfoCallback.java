package com.mondyxue.xrouter.demo.callback;

import android.content.Intent;

import com.mondyxue.xrouter.callback.RouteCallback;
import com.mondyxue.xrouter.demo.constant.Extras;
import com.mondyxue.xrouter.demo.data.UserInfo;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public abstract class UserInfoCallback extends RouteCallback<UserInfo>{

    @Override public UserInfo parseData(int requestCode, int resultCode, Intent data){
        return (UserInfo) data.getSerializableExtra(Extras.UserInfo);
    }

}
