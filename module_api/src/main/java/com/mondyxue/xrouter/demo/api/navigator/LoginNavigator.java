package com.mondyxue.xrouter.demo.api.navigator;

import com.mondyxue.xrouter.annotation.Route;
import com.mondyxue.xrouter.demo.api.data.UserInfo;
import com.mondyxue.xrouter.demo.api.service.UserService;
import com.mondyxue.xrouter.navigator.ActivityNavigator;

/**
 * Navigator for login module
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface LoginNavigator{

    String _LoginFragment = "/login/fragment/LoginFragment";
    String _UserService = "/login/service/UserService";

    /** navigation to fragment with container acitivity by {@link ActivityNavigator} */
    @Route(path = _LoginFragment)
    ActivityNavigator<UserInfo> toLoginFragment();

    /** navigation to service {@link UserService} */
    @Route(path = _UserService)
    UserService getUserService();

}
