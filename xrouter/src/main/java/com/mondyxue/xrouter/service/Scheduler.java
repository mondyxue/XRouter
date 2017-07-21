package com.mondyxue.xrouter.service;

import android.os.Handler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public interface Scheduler{

    String PATH = "/xrouter/service/scheduler";

    void setUiHandler(Handler handler);
    void setExecutor(ThreadPoolExecutor excutor);

    void runOnUiThread(Runnable r);
    void runOnUiThreadDelayed(Runnable r, long millis);
    void removeUiRunnable(Runnable r);

    void runOnWorkerThread(Runnable r);
    void removeWorkerThreadRunnable(Runnable r);

}
