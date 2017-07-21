package com.mondyxue.xrouter.service.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.service.Scheduler;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
@Route(path = Scheduler.PATH, extras = RouteType.GreenService)
public class SchedulerImpl implements IProvider, Scheduler{

    private Handler mUiHandler;
    private ThreadPoolExecutor mExcutor;

    @Override public void init(Context context){}
    @Override public void setExecutor(ThreadPoolExecutor excutor){
        mExcutor = excutor;
    }
    @Override public void runOnUiThread(Runnable r){
        getUiHandler().post(r);
    }
    @Override public void runOnUiThreadDelayed(Runnable r, long millis){
        getUiHandler().postDelayed(r, millis);
    }
    @Override public void removeUiRunnable(Runnable r){
        getUiHandler().removeCallbacks(r);
    }
    @Override public void runOnWorkerThread(Runnable r){
        getExcutor().execute(r);
    }
    @Override public void removeWorkerThreadRunnable(Runnable r){
        getExcutor().remove(r);
    }
    private ThreadPoolExecutor getExcutor(){
        if(mExcutor == null){
            mExcutor = new ThreadPoolExecutor(
                    0, Integer.MAX_VALUE,
                    60L, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>()
            );
        }
        return mExcutor;
    }
    private Handler getUiHandler(){
        if(mUiHandler == null){
            mUiHandler = new Handler(Looper.getMainLooper());
        }
        return mUiHandler;
    }
    @Override public void setUiHandler(Handler handler){
        mUiHandler = handler;
    }

}
