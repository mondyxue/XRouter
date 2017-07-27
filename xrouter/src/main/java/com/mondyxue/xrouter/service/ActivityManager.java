package com.mondyxue.xrouter.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mondyxue.xrouter.constant.AcitivityStatus;

import java.util.List;

/**
 * A manager for listening activity's lifecycle
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface ActivityManager{

    String PATH = "/xrouter/service/activityManager";

    void updateActivity(Activity activity, @AcitivityStatus int status);

    /**
     * return the top activity or the application context
     * @see #getTopActivity()
     */
    Context getContext();

    Activity getTopActivity();

    <T extends Activity> T getActivity(Class<T> clazz);
    <T extends Activity> List<T> getActivities(Class<T> clazz);
    List<Activity> getAllActivities();

    void finishActivity(Class<? extends Activity> clazz);
    void finishActivities(Class<? extends Activity> clazz);
    void finishAllActivity();

    /**
     * return true if no activity in the forground else false
     */
    boolean isOnBackground();

    void safeFinish(Activity activity);

    void addOnStatusChangedListener(OnStatusChangedListener listener);
    void removeOnStatusChangedListener(OnStatusChangedListener listener);

    void addOnActivityResultListener(OnActivityResultListener listener);
    void removeOnActivityResultListener(OnActivityResultListener listener);

    /** Invoke this in {@link Activity#onActivityResult(int, int, Intent)} for dispatching to {@link OnActivityResultListener} */
    void onActivityResult(Activity context, int requestCode, int resultCode, Intent data);

    /**
     * Using this for listening {@link Activity#onActivityResult(int, int, Intent)}
     * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
     */
    interface OnActivityResultListener{

        void onActivityResult(Activity context, int requestCode, int resultCode, Intent data);
    }

    /**
     * Using this for listening activity's lifecycle
     * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
     * @see Activity
     */
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
