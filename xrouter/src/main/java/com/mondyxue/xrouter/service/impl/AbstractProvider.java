package com.mondyxue.xrouter.service.impl;

import android.content.Context;
import android.support.annotation.CallSuper;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * A simple implementation of {@link IProvider}
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public abstract class AbstractProvider implements IProvider{

    private Context mContext;

    @CallSuper
    @Override public void init(Context context){
        mContext = context;
    }

    protected Context getContext(){
        return mContext;
    }

}
