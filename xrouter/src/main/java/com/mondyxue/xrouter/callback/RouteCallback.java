package com.mondyxue.xrouter.callback;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.Postcard;

/**
 * A callback for
 * <ul>
 * <li>{@link com.mondyxue.xrouter.navigator.ActivityNavigator#startActivityForResult(RouteCallback)} </li>
 * <li>{@link com.mondyxue.xrouter.navigator.Router#startActivityForResult(Postcard, int, RouteCallback)}</li>
 * </ul>
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public abstract class RouteCallback<T>{

    /**
     * Invoked when {@link Activity#onActivityResult(int, int, Intent)}
     * @see com.mondyxue.xrouter.navigator.impl.RouterImpl#onActivityResult(Activity, int, int, Intent)
     */
    public void onResponse(int requestCode, int resultCode, Intent data){
        if(data != null){
            try{
                T parseData = parseData(requestCode, resultCode, data);
                if(parseData != null){
                    onResponse(parseData);
                }else{
                    onError(new RuntimeException("no data parsed"));
                }
            }catch(Exception e){
                onError(new RuntimeException("an exception been catched when parsing data", e));
            }
        }else{
            onCancel();
        }
    }

    /**
     * Parsing data from {@link Activity#onActivityResult(int, int, Intent)}
     * @return parsing result
     */
    public abstract T parseData(int requestCode, int resultCode, @NonNull Intent data);

    /** Invoked when parsing not null data by {@link #parseData(int, int, Intent)} */
    public abstract void onResponse(@NonNull T data);

    /**
     * Invoked when the intent is null
     * @see #onResponse(int, int, Intent)
     */
    public void onCancel(){}

    /** Invoked when no data parsed by {@link #parseData(int, int, Intent)} or exception throws */
    public void onError(Throwable throwable){}

}
