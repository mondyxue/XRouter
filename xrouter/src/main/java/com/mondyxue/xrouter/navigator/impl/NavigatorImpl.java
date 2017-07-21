package com.mondyxue.xrouter.navigator.impl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.activity.ReforwardActivity;
import com.mondyxue.xrouter.callback.RouteCallback;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.navigator.Navigator;
import com.mondyxue.xrouter.utils.TypeUtils;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
class NavigatorImpl implements Navigator{

    private Postcard mPostcard;

    NavigatorImpl(Postcard postcard){
        mPostcard = postcard;
    }

    @Override public <T extends Fragment> T fragment(){
        return (T) mPostcard.navigation();
    }

    @Override public <T extends IProvider> T service(){
        return (T) mPostcard.navigation();
    }

    @Override public void startActivity(){
        startActivityForResult(null);
    }

    @Override public void startActivityForResult(RouteCallback callback){
        int requestCode = -1;
        Bundle extras = mPostcard.getExtras();
        if(extras != null){
            requestCode = extras.getInt(RouteExtras.RequestCode, requestCode);
            if(requestCode == -1){
                requestCode = 200;
            }
        }
        XRouter.getRouter().startActivityForResult(mPostcard, requestCode, callback);
    }

    @Override public Uri uri(){
        Uri uri = mPostcard.getUri();
        if(uri != null){
            return uri;
        }
        String path = mPostcard.getPath();
        path = path.replaceAll("\\/+", "/");
        if(path.startsWith("/")){
            path = path.substring(1);
        }
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(XRouter.SCHEME)
               .authority(XRouter.AUTHORITY)
               .appendPath(path);
        Bundle extras = mPostcard.getExtras();
        if(extras != null){
            for(String key : extras.keySet()){
                Object value = extras.get(key);
                if(value != null){
                    if(TypeUtils.isFundamentalType(value)){
                        builder.appendQueryParameter(key, value.toString());
                    }else{
                        throw new IllegalArgumentException("unsupport query:" + value);
                    }
                }
            }
        }
        return builder.build();
    }

    @Override public Intent intent(){
        Intent intent = new Intent(XRouter.getRouter().getContext(), ReforwardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = mPostcard.getUri();
        if(uri != null){
            intent.setData(mPostcard.getUri());
        }else{
            intent.putExtra(RouteExtras.PathTo, mPostcard.getPath());
        }
        Bundle extras = mPostcard.getExtras();
        if(extras != null && !extras.isEmpty()){
            intent.putExtras(extras);
        }
        return intent;
    }

}
