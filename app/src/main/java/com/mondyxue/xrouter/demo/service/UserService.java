package com.mondyxue.xrouter.demo.service;

import com.mondyxue.xrouter.demo.data.UserInfo;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public interface UserService{

    void login(long userId);
    void logout();

    boolean isLogin();
    UserInfo getUserInfo();

}
