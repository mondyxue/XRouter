package com.togojoy.trouter.navigator;

import android.support.v4.app.Fragment;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public interface FragmentNavigator{

    <T extends Fragment> T fragment();

}
