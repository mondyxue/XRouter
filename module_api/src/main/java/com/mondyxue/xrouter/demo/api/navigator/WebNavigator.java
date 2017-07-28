package com.mondyxue.xrouter.demo.api.navigator;

import com.mondyxue.xrouter.annotation.Extra;
import com.mondyxue.xrouter.annotation.Route;
import com.mondyxue.xrouter.demo.api.constant.Extras;

/**
 * Navigator for web module
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface WebNavigator{

    String _WebActivity = "/web/activity/WebViewActivity";

    /** navigation to activity with extras */
    @Route(path = _WebActivity)
    void toWebViewActivity(
            @Extra(Extras.Url) String url
    );

}
