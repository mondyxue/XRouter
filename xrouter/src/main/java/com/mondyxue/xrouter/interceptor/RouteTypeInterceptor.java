package com.mondyxue.xrouter.interceptor;

import android.content.Context;
import android.support.annotation.WorkerThread;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public abstract class RouteTypeInterceptor implements IInterceptor{

    private Context mContext;

    @Override public void init(Context context){
        mContext = context;
    }

    protected Context getContext(){
        return mContext;
    }

    @Override public void process(final Postcard postcard, final InterceptorCallback callback){
        if(onCheckRouteType(postcard.getExtra())){
            onInterrupt(postcard, callback);
        }else{
            callback.onContinue(postcard);
        }
    }

    protected abstract boolean onCheckRouteType(int routeTypes);

    @WorkerThread
    protected abstract void onInterrupt(Postcard postcard, InterceptorCallback callback);

}
