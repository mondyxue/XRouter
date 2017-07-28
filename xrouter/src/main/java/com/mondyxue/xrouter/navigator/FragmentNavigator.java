package com.mondyxue.xrouter.navigator;

import android.support.v4.app.Fragment;

/**
 * A fragment navigator
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface FragmentNavigator{

    /** return the target fragment */
    <T extends Fragment> T fragment();

}
