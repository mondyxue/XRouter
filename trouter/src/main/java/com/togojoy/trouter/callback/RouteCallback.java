package com.togojoy.trouter.callback;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public abstract class RouteCallback<T>{

    public void onResponse(int requestCode, int resultCode, Intent data){
        if(data != null){
            try{
                T parseData = parseData(requestCode, resultCode, data);
                if(parseData != null){
                    onResponse(parseData);
                }
            }catch(Exception ignored){}
        }
    }

    public abstract T parseData(int requestCode, int resultCode, Intent data);

    public abstract void onResponse(@NonNull T data);

    public void onCancel(){}

}
