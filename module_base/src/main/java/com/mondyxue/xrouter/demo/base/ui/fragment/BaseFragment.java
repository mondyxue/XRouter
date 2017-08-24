package com.mondyxue.xrouter.demo.base.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.demo.base.R;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public abstract class BaseFragment extends Fragment{

    private TextView mTvTitle;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(getRootLayout(), container, false);
        View ivBack = rootView.findViewById(R.id.btn_back);
        if(ivBack != null){
            ivBack.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    onBackPressed();
                }
            });
        }
        mTvTitle = getActivity().findViewById(R.id.tv_title);
        if(autoInjectExtras()){
            XRouter.getRouter().inject(BaseFragment.this);
        }
        initView(rootView);
        return rootView;
    }

    @LayoutRes
    protected abstract int getRootLayout();

    protected boolean autoInjectExtras(){
        return false;
    }

    protected abstract void initView(View rootView);

    protected void setTitle(String title){
        if(mTvTitle != null){
            mTvTitle.setText(title);
        }
    }

    protected void onBackPressed(){
        getActivity().finish();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // handle the activity result
        XRouter.getRouter()
               .getActivityManager()
               .onActivityResult(getActivity(), requestCode, resultCode, data);
    }

}
