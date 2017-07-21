package com.mondyxue.xrouter.demo.navigator;

import com.mondyxue.xrouter.annotation.Extra;
import com.mondyxue.xrouter.annotation.Route;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.demo.constant.Extras;
import com.mondyxue.xrouter.demo.data.UserInfo;
import com.mondyxue.xrouter.demo.service.Logger;
import com.mondyxue.xrouter.demo.service.UserService;
import com.mondyxue.xrouter.navigator.ActivityNavigator;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public interface DemoNavigator{

    String _UserInfoFragment = "/fragment/UserInfoFragment";
    String _TextFragment = "/fragment/TextFragment";
    String _WebActivity = "/activity/WebViewActivity";
    String _LoginFragment = "/fragment/LoginFragment";
    String _MainActivity = "/activity/MainActivity";
    String _ContanierActivity = "/contanier/ContanierActivity";

    String _Logger = "/service/Logger";
    String _UserService = "/service/UserService";

    @Route(path = _TextFragment, title = "TextFragment")
    void toTextFragment(
            @Extra(Extras.Text) String text
    );

    @Route(path = _LoginFragment)
    ActivityNavigator<UserInfo> toLoginFragment();

    @Route(path = _UserInfoFragment, title = "UserInfoFragment")
    void toUserInfoFragment();

/*
    @Route(path = _MainActivity, flags = Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
    void toMainActivity(
            @Extra(RouteExtras.Action) String action
    );
*/

    @Route(path = _MainActivity)
    void toMainActivity(
            @Extra(RouteExtras.Action) String action
    );

    @Route(path = _WebActivity)
    void toWebViewActivity(
            @Extra(Extras.Url) String url
    );

    @Route(path = _Logger)
    Logger getLogger();

    @Route(path = _UserService)
    UserService getUserService();

}
