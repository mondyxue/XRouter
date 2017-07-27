package com.mondyxue.xrouter.demo.base.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.api.navigator.BaseNavigator;
import com.mondyxue.xrouter.demo.base.R;

/**
 * Contanier activity for {@link com.mondyxue.xrouter.interceptor.FragmentInterceptor}
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Route(path = BaseNavigator._ContanierActivity, extras = RouteType.Activity | RouteType.GreenChannel)
public class ContanierActivity extends BaseActivity{

    @Override protected int getRootLayout(){
        boolean withinTitlebar = getIntent().getBooleanExtra(RouteExtras.WithinTitlebar, false);
        return withinTitlebar ? R.layout.activity_contanier
                              : R.layout.activity_contanier_within_titlebar;
    }

    @Override protected void init(){

        Intent intent = getIntent();

        boolean withinTitlebar = intent.getBooleanExtra(RouteExtras.WithinTitlebar, false);
        if(!withinTitlebar){
            String title = intent.getStringExtra(RouteExtras.Title);
            ((TextView) findViewById(R.id.tv_title)).setText(title);
        }

        String path = intent.getStringExtra(RouteExtras.PathTo);
        if(!TextUtils.isEmpty(path)){

            final Fragment fragment = XRouter.getRouter().build(path).navigator().fragment();
            if(fragment != null){
                fragment.setArguments(intent.getExtras());
                runOnUiThread(new Runnable(){
                    @Override public void run(){
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fl_contanier, fragment)
                                .hide(fragment)
                                .show(fragment)
                                .commitAllowingStateLoss();
                    }
                });
                return;
            }

        }

        finish();

    }

}
