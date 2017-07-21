package com.togojoy.trouter.service.impl;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.togojoy.trouter.constant.AcitivityStatus;
import com.togojoy.trouter.constant.RouteType;
import com.togojoy.trouter.service.ActivityManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * <br>Created by MondyXue
 * <br>E-Mail: mondyxue@163.com
 */
@Route(path = ActivityManager.PATH, extras = RouteType.GreenService)
public class ActivityManagerImpl implements IProvider, ActivityManager, Application.ActivityLifecycleCallbacks{

    private List<OnStatusChangedListener> mOnStatusChangedListeners;
    private List<OnActivityResultListener> mOnActivityResultListeners;
    private Stack<AcitivityHolder> mActivityStack = new Stack<>();

    private Context mContext;

    @Override public void init(Context context){
        mContext = context;
        ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(this);
    }

    @Override public Context getContext(){
        Activity topActivity = getTopActivity();
        return topActivity == null ? mContext : topActivity;
    }

    @Override public void updateActivity(Activity activity, @AcitivityStatus int status){
        if(status == AcitivityStatus.UNKNOWN){
            return;
        }
        if(mOnStatusChangedListeners != null){
            for(OnStatusChangedListener listener : mOnStatusChangedListeners){
                if(listener != null){
                    switch(status){
                    case AcitivityStatus.ON_CREATED:
                        listener.onCreated(activity);
                        break;
                    case AcitivityStatus.ON_DESTROYED:
                        listener.onDestroyed(activity);
                        break;
                    case AcitivityStatus.ON_PAUSED:
                        listener.onPaused(activity);
                        break;
                    case AcitivityStatus.ON_RESUMED:
                        listener.onResumed(activity);
                        break;
                    case AcitivityStatus.ON_SAVEINSTANCESTATE:
                        listener.onSaveInstanceState(activity);
                        break;
                    case AcitivityStatus.ON_STARTED:
                        listener.onStarted(activity);
                        break;
                    case AcitivityStatus.ON_STOPPED:
                        listener.onStopped(activity);
                        break;
                    }
                }
            }
        }
        if(status == AcitivityStatus.ON_DESTROYED){
            Iterator<AcitivityHolder> iterator = mActivityStack.iterator();
            while(iterator.hasNext()){
                AcitivityHolder next = iterator.next();
                if(next.activity == activity){
                    iterator.remove();
                    return;
                }
            }
        }else{
            for(AcitivityHolder holder : mActivityStack){
                if(holder.activity == activity){
                    holder.status = status;
                    return;
                }
            }
            AcitivityHolder holder = new AcitivityHolder();
            holder.activity = activity;
            holder.clazz = activity.getClass();
            holder.status = status;
            mActivityStack.add(holder);
        }
    }

    @Override public void addOnStatusChangedListener(@NonNull OnStatusChangedListener listener){
        if(mOnStatusChangedListeners == null){
            mOnStatusChangedListeners = new ArrayList<>();
        }
        if(!mOnStatusChangedListeners.contains(listener)){
            mOnStatusChangedListeners.add(listener);
        }
    }
    @Override public void removeOnStatusChangedListener(@NonNull OnStatusChangedListener listener){
        if(mOnStatusChangedListeners != null){
            mOnStatusChangedListeners.remove(listener);
        }
    }

    @Override public void addOnActivityResultListener(OnActivityResultListener listener){
        if(mOnActivityResultListeners == null){
            mOnActivityResultListeners = new ArrayList<>();
        }
        if(!mOnActivityResultListeners.contains(listener)){
            mOnActivityResultListeners.add(listener);
        }
    }
    @Override public void removeOnActivityResultListener(OnActivityResultListener listener){
        if(mOnActivityResultListeners != null){
            mOnActivityResultListeners.remove(listener);
        }
    }

    @Override public void onActivityResult(Activity context, int requestCode, int resultCode, Intent data){
        if(mOnActivityResultListeners != null && !mOnActivityResultListeners.isEmpty()){
            for(OnActivityResultListener listener : mOnActivityResultListeners){
                listener.onActivityResult(context, requestCode, resultCode, data);
            }
        }
    }

    @Override public Activity getTopActivity(){
        return mActivityStack.isEmpty() ? null : mActivityStack.lastElement().activity;
    }

    @Override public boolean isOnBackground(){
        for(AcitivityHolder next : mActivityStack){
            if(next.status < AcitivityStatus.ON_STOPPED){
                return false;
            }
        }
        return true;
    }

    @Override public <T extends Activity> T getActivity(@NonNull Class<T> clazz){
        for(AcitivityHolder next : mActivityStack){
            if(next.clazz.getCanonicalName().equals(clazz.getCanonicalName())){
                return (T) next.activity;
            }
        }
        return null;
    }
    @Override public <T extends Activity> List<T> getActivities(Class<T> clazz){
        List<T> activities = new ArrayList<>();
        for(AcitivityHolder next : mActivityStack){
            if(next.clazz.getCanonicalName().equals(clazz.getCanonicalName())){
                activities.add((T) next.activity);
            }
        }
        return activities;
    }
    @Override public List<Activity> getAllActivities(){
        List<Activity> activities = new ArrayList<>();
        for(AcitivityHolder next : mActivityStack){
            activities.add(next.activity);
        }
        return activities;
    }

    @Override public void finishActivity(@NonNull Class<? extends Activity> clazz){
        Activity activity = getActivity(clazz);
        safeFinish(activity);
    }
    @Override public void finishActivities(Class<? extends Activity> clazz){
        List<? extends Activity> activities = getActivities(clazz);
        for(Activity activity : activities){
            safeFinish(activity);
        }
    }
    @Override public void finishAllActivity(){
        Iterator<AcitivityHolder> iterator = mActivityStack.iterator();
        while(iterator.hasNext()){
            Activity activity = iterator.next().activity;
            safeFinish(activity);
            iterator.remove();
        }
    }

    @Override public void safeFinish(Activity activity){
        if(activity != null && !activity.isFinishing()){
            activity.finish();
        }
    }

    @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState){
        updateActivity(activity, AcitivityStatus.ON_CREATED);
    }
    @Override public void onActivityStarted(Activity activity){
        updateActivity(activity, AcitivityStatus.ON_STARTED);
    }
    @Override public void onActivityResumed(Activity activity){
        updateActivity(activity, AcitivityStatus.ON_RESUMED);
    }
    @Override public void onActivityPaused(Activity activity){
        updateActivity(activity, AcitivityStatus.ON_PAUSED);
    }
    @Override public void onActivityStopped(Activity activity){
        updateActivity(activity, AcitivityStatus.ON_STOPPED);
    }
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState){
        updateActivity(activity, AcitivityStatus.ON_SAVEINSTANCESTATE);
    }
    @Override public void onActivityDestroyed(Activity activity){
        updateActivity(activity, AcitivityStatus.ON_DESTROYED);
    }

    private class AcitivityHolder{

        Class<? extends Activity> clazz;
        Activity activity;
        @AcitivityStatus int status;
    }

}