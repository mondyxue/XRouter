package com.mondyxue.xrouter.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mondyxue.xrouter.utils.BundleUtils;

/**
 * The implementation of {@link IBundleWrapper}
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@SuppressWarnings("unchecked")
public class BundleWrapper implements IBundleWrapper{

    private Bundle mBundle;

    public BundleWrapper(){
        this(new Bundle());
    }
    public BundleWrapper(@NonNull Bundle bundle){
        this.mBundle = bundle;
    }

    @Override public IBundleWrapper put(String key, Object value){
        if(!BundleUtils.checkToPut(mBundle, key, value)){
            throw new IllegalArgumentException("unsupport extra:" + value);
        }
        return this;
    }
    @Override public <T> T get(String key){
        return get(key, null);
    }
    @Override public <T> T get(String key, T def){
        Object value = mBundle.get(key);
        if(value != null){
            return (T) value;
        }else{
            return def;
        }
    }
    @Override public IBundleWrapper put(Bundle bundle){
        mBundle.putAll(bundle);
        return this;
    }
    @Override public Bundle getBundle(){
        return mBundle;
    }
    @Override public boolean isEmpty(){
        return mBundle.isEmpty();
    }
    @Override public void clear(){
        mBundle.clear();
    }

}