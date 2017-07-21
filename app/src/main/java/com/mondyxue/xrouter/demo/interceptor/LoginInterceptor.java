package com.mondyxue.xrouter.demo.interceptor;

import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.demo.callback.UserInfoCallback;
import com.mondyxue.xrouter.demo.constant.Extras;
import com.mondyxue.xrouter.demo.data.UserInfo;
import com.mondyxue.xrouter.demo.navigator.DemoNavigator;
import com.mondyxue.xrouter.navigator.Navigator;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
@Interceptor(priority = 4, name = "LoginInterceptor")
public class LoginInterceptor extends com.mondyxue.xrouter.interceptor.LoginInterceptor implements IInterceptor{

    @Override public boolean isLogin(){
        return XRouter.getRouter()
                      .create(DemoNavigator.class)
                      .getUserService()
                      .isLogin();
    }

    @Override protected void onInterrupt(final Postcard postcard, final InterceptorCallback callback){
        XRouter.getRouter()
               .create(DemoNavigator.class)
               .toLoginFragment()
               .startActivityForResult(new UserInfoCallback(){
                   @Override public void onResponse(@NonNull UserInfo data){
                       postcard.withSerializable(Extras.UserInfo, data);
                       callback.onContinue(postcard);
                   }
                   @Override public void onError(Throwable throwable){
                       callback.onInterrupt(throwable);
                   }
                   @Override public void onCancel(){
                       callback.onInterrupt(new RuntimeException("login cancel!"));
                   }
               });
    }

}
