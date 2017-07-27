package com.mondyxue.xrouter.demo.service;

import com.mondyxue.xrouter.demo.data.UserInfo;

/**
 * Demo service for userinfo
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface UserService{

    void login(long userId);
    void logout();

    boolean isLogin();
    UserInfo getUserInfo();

}
