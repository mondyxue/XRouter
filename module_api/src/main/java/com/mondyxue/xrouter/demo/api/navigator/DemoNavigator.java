package com.mondyxue.xrouter.demo.api.navigator;

import com.mondyxue.xrouter.annotation.Extra;
import com.mondyxue.xrouter.annotation.Route;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.demo.api.constant.Extras;
import com.mondyxue.xrouter.demo.api.data.UserInfo;

/**
 * Navigator for demo module
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface DemoNavigator{

    String _UserInfoFragment = "/demo/fragment/UserInfoFragment";
    String _TextFragment = "/demo/fragment/TextFragment";
    String _MainActivity = "/demo/activity/MainActivity";

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
     * navigation to fragment with container acitivity
     * @see com.mondyxue.xrouter.interceptor.FragmentInterceptor
     */
    @Route(path = _UserInfoFragment, title = "UserInfoFragment")
    void toUserInfoFragment(
            /* set serializable true for autowired  */
            @Extra(value = Extras.UserInfo) UserInfo userInfo
    );

    /**
     * navigation to {@link com.mondyxue.xrouter.constant.RouteType#Main} activity with extras
     * @see com.mondyxue.xrouter.interceptor.MainInterceptor
     */
    @Route(path = _MainActivity)
    void toMainActivity(
            @Extra(RouteExtras.Action) String action
    );

}
