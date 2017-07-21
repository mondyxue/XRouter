package com.togojoy.trouter.activity;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.togojoy.trouter.TRouter;
import com.togojoy.trouter.constant.RouteExtras;
import com.togojoy.trouter.navigator.impl.NavigatorBuilder;

public class RedirectActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        NavigatorBuilder build = TRouter.getRouter().build(getIntent());
        if(build != null){
            Postcard postcard = build.postcard();
            postcard.navigation(this, postcard.getExtras().getInt(RouteExtras.RequestCode, -1), new NavigationCallback(){
                @Override public void onFound(Postcard postcard){}
                @Override public void onLost(Postcard postcard){
                    finish();
                }
                @Override public void onArrival(Postcard postcard){
                    finish();
                }
                @Override public void onInterrupt(Postcard postcard){
                    finish();
                }
            });
        }else{
            finish();
        }
    }

}
