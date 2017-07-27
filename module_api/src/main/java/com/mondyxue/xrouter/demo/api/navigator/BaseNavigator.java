package com.mondyxue.xrouter.demo.api.navigator;

import com.mondyxue.xrouter.annotation.Route;
import com.mondyxue.xrouter.demo.api.service.Logger;

/**
 * Navigator for base module
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface BaseNavigator{

    String _ContanierActivity = "/base/contanier/ContanierActivity";

    String _Logger = "/base/service/Logger";

    /** navigation to service {@link Logger} */
    @Route(path = _Logger)
    Logger getLogger();

}
