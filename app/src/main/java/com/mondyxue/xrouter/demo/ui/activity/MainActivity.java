package com.mondyxue.xrouter.demo.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.R;
import com.mondyxue.xrouter.demo.api.callback.UserInfoCallback;
import com.mondyxue.xrouter.demo.api.data.UserInfo;
import com.mondyxue.xrouter.demo.api.navigator.BaseNavigator;
import com.mondyxue.xrouter.demo.api.navigator.DemoNavigator;
import com.mondyxue.xrouter.demo.api.navigator.LoginNavigator;
import com.mondyxue.xrouter.demo.api.navigator.WebNavigator;
import com.mondyxue.xrouter.demo.base.ui.activity.BaseActivity;

@Route(path = DemoNavigator._MainActivity, extras = RouteType.MainActivity)
public class MainActivity extends BaseActivity implements View.OnClickListener{

    private EditText mEtContent;

    @Override protected int getRootLayout(){
        return R.layout.activity_main;
    }

    @Override protected void init(){
        mEtContent = findViewById(R.id.et_content);
        findViewById(R.id.btn_webview).setOnClickListener(MainActivity.this);
        findViewById(R.id.btn_userinfo).setOnClickListener(MainActivity.this);
        findViewById(R.id.btn_login).setOnClickListener(MainActivity.this);
        findViewById(R.id.btn_logout).setOnClickListener(MainActivity.this);
        findViewById(R.id.btn_logger).setOnClickListener(MainActivity.this);
        findViewById(R.id.btn_text).setOnClickListener(MainActivity.this);
    }

    @Override public void onClick(View v){

        switch(v.getId()){

        case R.id.btn_userinfo:
            XRouter.getRouter()
                   .create(DemoNavigator.class)
                   .toUserInfoFragment();
            break;

        case R.id.btn_login:
            XRouter.getRouter()
                   .create(LoginNavigator.class)
                   .toLoginFragment()
                   .startActivityForResult(new UserInfoCallback(){
                       @Override public void onResponse(@NonNull UserInfo data){
                           Toast.makeText(MainActivity.this, "login success: " + data.toString(), Toast.LENGTH_SHORT).show();
                       }
                   });
            break;

        case R.id.btn_logout:
            XRouter.getRouter()
                   .create(LoginNavigator.class)
                   .getUserService()
                   .logout();
            Toast.makeText(MainActivity.this, "logout success!", Toast.LENGTH_SHORT).show();
            break;

        case R.id.btn_logger:
            XRouter.getRouter()
                   .create(BaseNavigator.class)
                   .getLogger()
                   .e("onClick", "Logger", mEtContent.getText().toString());
            break;

        case R.id.btn_text:
            XRouter.getRouter()
                   .create(DemoNavigator.class)
                   .toTextFragment(mEtContent.getText().toString());
            break;

        case R.id.btn_webview:
            XRouter.getRouter()
                   .create(WebNavigator.class)
                   .toWebViewActivity("file:///android_asset/scheme_test.html");
            break;

        }

    }

    @Override protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        String action = intent.getStringExtra(RouteExtras.Action);
        if(!TextUtils.isEmpty(action)){
            Toast.makeText(MainActivity.this, "onNewIntent:Action=" + action, Toast.LENGTH_SHORT).show();
        }
    }

}
