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
 * Navigator for demo pages and services
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
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

    /**
     * navigation to {@link com.mondyxue.xrouter.demo.ui.fragment.TextFragment} with container acitivity
     * @see com.mondyxue.xrouter.demo.interceptor.FragmentInterceptor
     */
    @Route(path = _TextFragment, title = "TextFragment")
    void toTextFragment(
            @Extra(Extras.Text) String text
    );

    /**
     * navigation to {@link com.mondyxue.xrouter.demo.ui.fragment.UserInfoFragment} with container acitivity
     * @see com.mondyxue.xrouter.demo.interceptor.FragmentInterceptor
     */
    @Route(path = _UserInfoFragment, title = "UserInfoFragment")
    void toUserInfoFragment();

    /** navigation to {@link com.mondyxue.xrouter.demo.ui.fragment.LoginFragment} with container acitivity by {@link ActivityNavigator} */
    @Route(path = _LoginFragment)
    ActivityNavigator<UserInfo> toLoginFragment();

    /**
     * navigation to {@link com.mondyxue.xrouter.constant.RouteType#Main} activity with extras
     * @see com.mondyxue.xrouter.demo.interceptor.MainInterceptor
     */
    @Route(path = _MainActivity)
    void toMainActivity(
            @Extra(RouteExtras.Action) String action
    );

    /** navigation to activity with extras */
    @Route(path = _WebActivity)
    void toWebViewActivity(
            @Extra(Extras.Url) String url
    );

    /** navigation to service {@link Logger} */
    @Route(path = _Logger)
    Logger getLogger();

    /** navigation to service {@link UserService} */
    @Route(path = _UserService)
    UserService getUserService();

}
