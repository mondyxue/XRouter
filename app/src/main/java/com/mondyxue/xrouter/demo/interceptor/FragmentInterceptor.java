package com.mondyxue.xrouter.demo.interceptor;

import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.mondyxue.xrouter.demo.navigator.DemoNavigator;

/**
 * The implementation of {@link com.mondyxue.xrouter.interceptor.FragmentInterceptor}
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Interceptor(priority = 8, name = "FragmentInterceptor")
public class FragmentInterceptor extends com.mondyxue.xrouter.interceptor.FragmentInterceptor implements IInterceptor{

    @Override protected String getFragmentContainerPath(){
        return DemoNavigator._ContanierActivity;
    }

}
