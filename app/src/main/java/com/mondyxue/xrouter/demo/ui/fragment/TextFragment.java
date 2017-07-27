package com.mondyxue.xrouter.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.R;
import com.mondyxue.xrouter.demo.base.ui.fragment.BaseFragment;
import com.mondyxue.xrouter.demo.api.constant.Extras;
import com.mondyxue.xrouter.demo.api.navigator.DemoNavigator;

/**
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
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

            TextView tvText = rootView.findViewById(R.id.tv_text);
            tvText.setText(text);

        }
    }

}
