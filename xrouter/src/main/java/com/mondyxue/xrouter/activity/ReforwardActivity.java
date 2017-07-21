package com.mondyxue.xrouter.activity;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.callback.NavigationCallbackWrapper;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.navigator.Router;
import com.mondyxue.xrouter.navigator.impl.NavigatorBuilder;

public class ReforwardActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Router router = XRouter.getRouter();
        NavigatorBuilder build = router.build(getIntent());
        if(build != null){
            Postcard postcard = build.postcard();
            postcard.navigation(this,
                                postcard.getExtras().getInt(RouteExtras.RequestCode, -1),
                                new NavigationCallbackWrapper(new NavigationCallback(){
                                    @Override public void onFound(Postcard postcard){
                                        if(!isFinishing()){
                                            finish();
                                        }
                                    }
                                    @Override public void onLost(Postcard postcard){
                                        if(!isFinishing()){
                                            finish();
                                        }
                                    }
                                    @Override public void onArrival(Postcard postcard){
                                        if(!isFinishing()){
                                            finish();
                                        }
                                    }
                                    @Override public void onInterrupt(Postcard postcard){
                                        if(!isFinishing()){
                                            finish();
                                        }
                                    }
                                }));
        }else{
            if(!isFinishing()){
                finish();
            }
        }
    }

}
