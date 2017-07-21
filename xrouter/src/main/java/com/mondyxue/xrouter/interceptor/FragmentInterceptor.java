package com.mondyxue.xrouter.interceptor;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.utils.MapUtils;
import com.alibaba.android.arouter.utils.TextUtils;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.navigator.impl.NavigatorBuilder;
import com.mondyxue.xrouter.utils.RouteTypeUtils;

import java.util.Map;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public abstract class FragmentInterceptor extends RouteTypeInterceptor{

    @Override protected boolean onCheckRouteType(int routeTypes){
        return RouteTypeUtils.isFragment(routeTypes);
    }

    @Override protected void onInterrupt(final Postcard postcard, final InterceptorCallback callback){
        XRouter.getRouter().getScheduler()
               .runOnUiThread(new Runnable(){
                   @Override public void run(){
                       Postcard redirect = buildRedirectPostcard(postcard);
                       NavigationCallback navigationCallback = new NavigationCallback(){
                           @Override public void onFound(Postcard postcard){
                               callback.onInterrupt(new RuntimeException("postcard found"));
                           }
                           @Override public void onLost(Postcard postcard){
                               callback.onContinue(postcard);
                           }
                           @Override public void onArrival(Postcard postcard){
                               callback.onInterrupt(new RuntimeException("postcard arrived"));
                           }
                           @Override public void onInterrupt(Postcard postcard){
                               callback.onInterrupt(new RuntimeException("postcard been interrupted"));
                           }
                       };
                       Context context = XRouter.getRouter().getContext();
                       int requestCode = postcard.getExtras().getInt(RouteExtras.RequestCode);
                       if(requestCode > 0 && context instanceof Activity){
                           redirect.navigation((Activity) context, requestCode, navigationCallback);
                       }else{
                           redirect.navigation(context, navigationCallback);
                       }
                   }
               });
    }

    protected abstract String getFragmentContainerPath();

    protected Postcard buildRedirectPostcard(Postcard postcard){
        NavigatorBuilder redirect = XRouter.getRouter().build(getFragmentContainerPath());
        int extra = postcard.getExtra();
        redirect.with(postcard.getExtras())
                .withFlags(postcard.getFlags())
                .withTransition(postcard.getEnterAnim(), postcard.getExitAnim())
                .withInt(RouteExtras.RouteType, extra)
                .withString(RouteExtras.PathTo, postcard.getPath())
                .withBoolean(RouteExtras.WithinTitlebar, RouteTypeUtils.isWithinTitlebar(extra));
        Uri uri = postcard.getUri();
        if(uri != null){
            Map<String,String> params = TextUtils.splitQueryParameters(uri);
            if(MapUtils.isNotEmpty(params)){
                for(Map.Entry<String,String> entry : params.entrySet()){
                    redirect.withString(entry.getKey(), entry.getValue());
                }
            }
        }
        redirect.setGreenChannel(true);
        return redirect.postcard();
    }

}
