package com.mondyxue.xrouter.demo.service.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.data.UserInfo;
import com.mondyxue.xrouter.demo.navigator.DemoNavigator;
import com.mondyxue.xrouter.demo.service.UserService;

/**
 * The implementation of {@link UserService}
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Route(path = DemoNavigator._UserService, extras = RouteType.GreenService)
public class UserServiceImpl implements UserService, IProvider{

    private boolean mLogin;
    private UserInfo mUserInfo;

    @Override public boolean isLogin(){
        return mLogin;
    }

    @Override public UserInfo getUserInfo(){
        return mUserInfo;
    }

    @Override public void login(long userId){
        mLogin = true;
        mUserInfo = new UserInfo();
        mUserInfo.setUserId(userId);
    }

    @Override public void logout(){
        mLogin = false;
        mUserInfo = null;
    }

    @Override public void init(Context context){}

}
