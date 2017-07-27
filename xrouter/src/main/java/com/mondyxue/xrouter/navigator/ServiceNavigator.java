package com.mondyxue.xrouter.navigator;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * A service navigator
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface ServiceNavigator{

    <T extends IProvider> T service();

}
