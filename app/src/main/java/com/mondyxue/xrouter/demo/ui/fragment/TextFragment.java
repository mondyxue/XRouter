package com.mondyxue.xrouter.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.R;
import com.mondyxue.xrouter.demo.constant.Extras;
import com.mondyxue.xrouter.demo.navigator.DemoNavigator;

/**
 * <br>Created by MondyXue
 * <br>E-Mail: mondyxue@gmail.com
 */
@Route(path = DemoNavigator._TextFragment, extras = RouteType.Fragment)
public class TextFragment extends BaseFragment{

    @Override protected int getRootLayout(){
        return R.layout.fragment_text;
    }

    @Override protected void initView(View rootView){
        Bundle arguments = getArguments();
        if(arguments != null){

            String text = arguments.getString(Extras.Text);

            TextView tvText = (TextView) rootView.findViewById(R.id.tv_text);
            tvText.setText(text);

        }
    }

}
