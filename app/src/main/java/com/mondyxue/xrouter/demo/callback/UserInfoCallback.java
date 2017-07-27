package com.mondyxue.xrouter.demo.callback;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.mondyxue.xrouter.callback.RouteCallback;
import com.mondyxue.xrouter.demo.constant.Extras;
import com.mondyxue.xrouter.demo.data.UserInfo;

/**
 * Callback for {@link UserInfo}
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public abstract class UserInfoCallback extends RouteCallback<UserInfo>{

    @Override public UserInfo parseData(int requestCode, int resultCode, @NonNull Intent data){
        return (UserInfo) data.getSerializableExtra(Extras.UserInfo);
    }

}
