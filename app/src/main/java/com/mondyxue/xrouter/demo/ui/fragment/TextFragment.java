package com.mondyxue.xrouter.demo.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.R;
import com.mondyxue.xrouter.demo.api.constant.Extras;
import com.mondyxue.xrouter.demo.api.navigator.DemoNavigator;
import com.mondyxue.xrouter.demo.base.ui.fragment.BaseFragment;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Route(path = DemoNavigator._TextFragment, extras = RouteType.Fragment)
public class TextFragment extends BaseFragment{

    @Autowired(name = Extras.Text)
    public String mExtraText;

    @Override protected int getRootLayout(){
        return R.layout.fragment_text;
    }

    @Override protected boolean autoInjectExtras(){
        return true;
    }

    @Override protected void initView(View rootView){

        TextView tvText = rootView.findViewById(R.id.tv_text);
        tvText.setText(mExtraText);

    }

}
