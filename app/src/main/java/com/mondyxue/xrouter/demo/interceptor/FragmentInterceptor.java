package com.mondyxue.xrouter.demo.interceptor;

import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.mondyxue.xrouter.demo.navigator.DemoNavigator;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
@Interceptor(priority = 8, name = "FragmentInterceptor")
public class FragmentInterceptor extends com.mondyxue.xrouter.interceptor.FragmentInterceptor implements IInterceptor{

    @Override protected String getFragmentContainerPath(){
        return DemoNavigator._ContanierActivity;
    }

}
