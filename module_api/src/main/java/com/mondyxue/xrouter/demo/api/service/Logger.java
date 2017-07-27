package com.mondyxue.xrouter.demo.api.service;

import android.support.annotation.NonNull;

/**
 * Demo service for logger
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface Logger{

    void e(String tag, @NonNull String... msgs);

}
