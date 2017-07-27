package com.mondyxue.xrouter.demo.web.ui.activity;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.api.constant.Extras;
import com.mondyxue.xrouter.demo.api.navigator.WebNavigator;
import com.mondyxue.xrouter.demo.base.ui.activity.BaseActivity;
import com.mondyxue.xrouter.demo.web.R;

/**
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Route(path = WebNavigator._WebActivity, extras = RouteType.Activity | RouteType.GreenChannel)
public class WebViewActivity extends BaseActivity{

    private WebView mWebView;

    @Override protected int getRootLayout(){
        return R.layout.activity_webview;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override protected void init(){

        mWebView = findViewById(R.id.webview);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override public void onReceivedTitle(WebView view, String title){
                setTitle(title);
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener(){
            @Override public boolean onKey(View v, int keyCode, KeyEvent event){
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
                    if(mWebView.canGoBack()){
                        mWebView.goBack();
                    }else{
                        onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });

        String url = getIntent().getStringExtra(Extras.Url);
        if(TextUtils.isEmpty(url)){
            url = "https://github.com/MondyXue/XRouter";
        }
        mWebView.loadUrl(url);

    }

}
