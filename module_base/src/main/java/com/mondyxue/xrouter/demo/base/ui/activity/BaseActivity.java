package com.mondyxue.xrouter.demo.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.demo.base.R;

public abstract class BaseActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getRootLayout());
        View ivBack = findViewById(R.id.btn_back);
        if(ivBack != null){
            ivBack.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    onBackPressed();
                }
            });
        }
        init();
    }

    @LayoutRes
    protected abstract int getRootLayout();

    protected abstract void init();

    @Override public void onBackPressed(){
        finish();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // handle the activity result
        XRouter.getRouter()
               .getActivityManager()
               .onActivityResult(BaseActivity.this, requestCode, resultCode, data);
    }

}
