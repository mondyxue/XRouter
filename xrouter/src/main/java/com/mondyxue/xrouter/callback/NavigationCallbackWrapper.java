package com.mondyxue.xrouter.callback;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.enums.RouteType;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.utils.RouteTypeUtils;

import java.lang.reflect.Field;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public class NavigationCallbackWrapper implements NavigationCallback{

    private NavigationCallback mDelegate;

    private boolean mFragmentInterception;

    public NavigationCallbackWrapper(){
        this(null);
    }
    public NavigationCallbackWrapper(boolean fragmentInterception){
        this(null, fragmentInterception);
    }
    public NavigationCallbackWrapper(NavigationCallback delegate){
        this(delegate, true);
    }
    public NavigationCallbackWrapper(NavigationCallback delegate, boolean fragmentInterception){
        mDelegate = delegate;
        mFragmentInterception = fragmentInterception;
    }

    @Override public void onFound(Postcard postcard){
        if(mFragmentInterception){
            if(RouteTypeUtils.isGreen(postcard.getExtra())){
                postcard.greenChannel();
            }else{
                if(postcard.getType() == RouteType.FRAGMENT
                   && postcard.isGreenChannel()
                   && !postcard.getExtras().getBoolean(RouteExtras.GreenChannel, false)){
                    try{
                        Field field = postcard.getClass().getDeclaredField("greenChannel");
                        field.setAccessible(true);
                        field.set(postcard, false);
                    }catch(Exception ignored){}
                }
            }
        }
        if(mDelegate != null) mDelegate.onFound(postcard);
    }
    @Override public void onLost(Postcard postcard){
        if(mDelegate != null) mDelegate.onLost(postcard);
    }
    @Override public void onArrival(Postcard postcard){
        if(mDelegate != null) mDelegate.onArrival(postcard);
    }
    @Override public void onInterrupt(Postcard postcard){
        if(mDelegate != null) mDelegate.onInterrupt(postcard);
    }

}
