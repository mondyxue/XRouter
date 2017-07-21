package com.mondyxue.xrouter.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.demo.R;

/**
 * <br>Created by MondyXue
 * <br>E-Mail: mondyxue@gmail.com
 */
public abstract class BaseFragment extends Fragment{

    private TextView mTvTitle;
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(getRootLayout(), container, false);
        View ivBack = rootView.findViewById(R.id.iv_back);
        if(ivBack != null){
            ivBack.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    onBackPressed();
                }
            });
        }
        mTvTitle = (TextView) getActivity().findViewById(R.id.tv_title);
        initView(rootView);
        return rootView;
    }

    @LayoutRes
    protected abstract int getRootLayout();

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
        XRouter.getRouter()
               .getActivityManager()
               .onActivityResult(getActivity(), requestCode, resultCode, data);
    }
}
