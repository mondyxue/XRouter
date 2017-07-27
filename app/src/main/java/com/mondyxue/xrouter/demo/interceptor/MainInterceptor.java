package com.mondyxue.xrouter.demo.interceptor;

import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * The implementation of {@link com.mondyxue.xrouter.interceptor.MainInterceptor}
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Interceptor(priority = 12, name = "MainInterceptor")
public class MainInterceptor extends com.mondyxue.xrouter.interceptor.MainInterceptor implements IInterceptor{}
