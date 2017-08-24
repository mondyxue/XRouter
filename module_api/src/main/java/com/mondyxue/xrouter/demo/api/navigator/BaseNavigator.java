package com.mondyxue.xrouter.demo.api.navigator;

import com.mondyxue.xrouter.annotation.Route;
import com.mondyxue.xrouter.demo.api.service.DataParser;
import com.mondyxue.xrouter.demo.api.service.Logger;

/**
 * Navigator for base module
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface BaseNavigator{

    String _ContanierActivity = "/base/contanier/ContanierActivity";

    String _Logger = "/base/service/Logger";
    String _DataParser = "/base/service/DataParser";

    /** navigation to service {@link Logger} */
    @Route(path = _Logger)
    Logger getLogger();

    /** navigation to service {@link DataParser} */
    @Route(path = _DataParser)
    DataParser getDataParser();

}
