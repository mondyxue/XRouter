package com.mondyxue.xrouter.navigator.impl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.activity.ReforwardActivity;
import com.mondyxue.xrouter.callback.RouteCallback;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.exception.SerializationException;
import com.mondyxue.xrouter.navigator.Navigator;
import com.mondyxue.xrouter.utils.BundleUtils;

import java.util.Map;

/**
 * The implementation of {@link Navigator}
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@SuppressWarnings("unchecked")
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
        XRouter.getRouter().startActivity(mPostcard);
    }

    @Override public void startActivityForResult(RouteCallback callback){
        int requestCode = -1;
        Bundle extras = mPostcard.getExtras();
        if(extras != null){
            // check the request code
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
        builder.scheme(XRouter.getScheme())
               .authority(XRouter.getAuthority())
               .appendPath(path);
        Bundle extras = mPostcard.getExtras();
        if(extras != null && extras.size() > 0){
            Map<String,String> params = BundleUtils.toQueryParameters(extras, new BundleUtils.StringConverter(){
                @Override public String convert(Object object){
                    SerializationService service = XRouter.getRouter().getSerializationService();
                    if(service != null){
                        return service.object2Json(object);
                    }else{
                        throw new SerializationException(object);
                    }
                }
            });
            for(String key : params.keySet()){
                builder.appendQueryParameter(key, params.get(key));
            }
        }
        return builder.build();
    }

    @Override public Intent intent(){
        //build a reforwaring activity intent for processing the target
        Intent intent = new Intent(XRouter.getRouter().getContext(), ReforwardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = mPostcard.getUri();
        if(uri != null){
            intent.setData(uri);
            intent.putExtra(RouteExtras.PathTo, uri.getPath());
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
