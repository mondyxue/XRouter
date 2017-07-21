package com.mondyxue.xrouter.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mondyxue.xrouter.constant.AcitivityStatus;

import java.util.List;

/**
 * <br></br>Created by MondyXue
 * <br></br>E-MAIL: mondyxue@gmial.com
 */
public interface ActivityManager{

    String PATH = "/xrouter/service/activityManager";

    void updateActivity(Activity activity, @AcitivityStatus int status);

    Context getContext();

    Activity getTopActivity();

    <T extends Activity> T getActivity(Class<T> clazz);
    <T extends Activity> List<T> getActivities(Class<T> clazz);
    List<Activity> getAllActivities();

    void finishActivity(Class<? extends Activity> clazz);
    void finishActivities(Class<? extends Activity> clazz);
    void finishAllActivity();

    boolean isOnBackground();

    void safeFinish(Activity activity);

    void addOnStatusChangedListener(OnStatusChangedListener listener);
    void removeOnStatusChangedListener(OnStatusChangedListener listener);

    void addOnActivityResultListener(OnActivityResultListener listener);
    void removeOnActivityResultListener(OnActivityResultListener listener);

    void onActivityResult(Activity context, int requestCode, int resultCode, Intent data);

    interface OnActivityResultListener{

        void onActivityResult(Activity context, int requestCode, int resultCode, Intent data);
    }

    interface OnStatusChangedListener{

        void onCreated(Activity activity);
        void onStarted(Activity activity);
        void onResumed(Activity activity);
        void onPaused(Activity activity);
        void onStopped(Activity activity);
        void onSaveInstanceState(Activity activity);
        void onDestroyed(Activity activity);
    }

}
