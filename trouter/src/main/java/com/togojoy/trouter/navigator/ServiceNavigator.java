package com.togojoy.trouter.navigator;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public interface ServiceNavigator{

    <T extends IProvider> T service();

}
