package com.mondyxue.xrouter.demo.api.navigator;

import com.mondyxue.xrouter.annotation.Extra;
import com.mondyxue.xrouter.annotation.Route;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.demo.api.constant.Extras;

/**
 * Navigator for demo module
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface DemoNavigator{

    String _UserInfoFragment = "/fragment/UserInfoFragment";
    String _TextFragment = "/fragment/TextFragment";
    String _MainActivity = "/activity/MainActivity";

    /**
     * navigation to fragment with container acitivity
     * @see com.mondyxue.xrouter.interceptor.FragmentInterceptor
     */
    @Route(path = _TextFragment, title = "TextFragment")
    void toTextFragment(
            @Extra(Extras.Text) String text
    );

    /**
     * navigation to fragment with container acitivity
     * @see com.mondyxue.xrouter.interceptor.FragmentInterceptor
     */
    @Route(path = _UserInfoFragment, title = "UserInfoFragment")
    void toUserInfoFragment();

    /**
     * navigation to {@link com.mondyxue.xrouter.constant.RouteType#Main} activity with extras
     * @see com.mondyxue.xrouter.interceptor.MainInterceptor
     */
    @Route(path = _MainActivity)
    void toMainActivity(
            @Extra(RouteExtras.Action) String action
    );

}
