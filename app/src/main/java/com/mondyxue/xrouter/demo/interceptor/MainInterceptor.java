package com.mondyxue.xrouter.demo.interceptor;

import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
@Interceptor(priority = 12, name = "MainInterceptor")
public class MainInterceptor extends com.mondyxue.xrouter.interceptor.MainInterceptor implements IInterceptor{}
