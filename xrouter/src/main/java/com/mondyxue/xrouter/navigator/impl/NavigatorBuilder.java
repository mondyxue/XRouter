package com.mondyxue.xrouter.navigator.impl;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.MapUtils;
import com.alibaba.android.arouter.utils.TextUtils;
import com.mondyxue.xrouter.constant.RouteExtras;
import com.mondyxue.xrouter.navigator.Navigator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * A navigator builder replaced by {@link Postcard}
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public final class NavigatorBuilder{

    private Postcard mPostcard;

    NavigatorBuilder(Uri uri){
        this(uri.getPath());
        Map<String,String> params = TextUtils.splitQueryParameters(uri);
        if(MapUtils.isNotEmpty(params)){
            for(Map.Entry<String,String> entry : params.entrySet()){
                mPostcard.withString(entry.getKey(), entry.getValue());
            }
        }
    }
    NavigatorBuilder(String path){
        mPostcard = ARouter.getInstance().build(path);
    }
    NavigatorBuilder(Postcard postcard){
        mPostcard = postcard;
    }

    public Navigator navigator(){
        return new NavigatorImpl(mPostcard);
    }

    public Postcard postcard(){
        return mPostcard;
    }

    public NavigatorBuilder setGreenChannel(boolean greenChannel){
        if(greenChannel){
            mPostcard.greenChannel();
            mPostcard.withBoolean(RouteExtras.GreenChannel, true);
        }else{
            mPostcard.withBoolean(RouteExtras.GreenChannel, false);
        }
        return this;
    }

    public NavigatorBuilder setTimeout(int timeout){
        mPostcard.setTimeout(timeout);
        return this;
    }

    public NavigatorBuilder withRequestCode(int requestCode){
        mPostcard.withInt(RouteExtras.RequestCode, requestCode);
        return this;
    }

    public int getRequestCode(){
        return mPostcard.getExtras().getInt(RouteExtras.RequestCode, -1);
    }

    public NavigatorBuilder withFlags(@Postcard.FlagInt int flags){
        mPostcard.withFlags(flags);
        mPostcard.withInt(RouteExtras.Flags, flags);
        return this;
    }

    public NavigatorBuilder withString(@NonNull String key, String value){
        mPostcard.withString(key, value);
        return this;
    }

    public NavigatorBuilder withBoolean(@NonNull String key, boolean value){
        mPostcard.withBoolean(key, value);
        return this;
    }

    public NavigatorBuilder withShort(@NonNull String key, short value){
        mPostcard.withShort(key, value);
        return this;
    }

    public NavigatorBuilder withInt(@NonNull String key, int value){
        mPostcard.withInt(key, value);
        return this;
    }

    public NavigatorBuilder withLong(@NonNull String key, long value){
        mPostcard.withLong(key, value);
        return this;
    }

    public NavigatorBuilder withDouble(@NonNull String key, double value){
        mPostcard.withDouble(key, value);
        return this;
    }

    public NavigatorBuilder withByte(@NonNull String key, byte value){
        mPostcard.withByte(key, value);
        return this;
    }

    public NavigatorBuilder withChar(@NonNull String key, char value){
        mPostcard.withChar(key, value);
        return this;
    }

    public NavigatorBuilder withFloat(@NonNull String key, float value){
        mPostcard.withFloat(key, value);
        return this;
    }

    public NavigatorBuilder withCharSequence(@NonNull String key, CharSequence value){
        mPostcard.withCharSequence(key, value);
        return this;
    }

    public NavigatorBuilder withParcelable(@NonNull String key, Parcelable value){
        mPostcard.withParcelable(key, value);
        return this;
    }

    public NavigatorBuilder withParcelableArray(@NonNull String key, Parcelable[] value){
        mPostcard.withParcelableArray(key, value);
        return this;
    }

    public NavigatorBuilder withParcelableArrayList(@NonNull String key, ArrayList<? extends Parcelable> value){
        mPostcard.withParcelableArrayList(key, value);
        return this;
    }

    public NavigatorBuilder withSparseParcelableArray(@NonNull String key, SparseArray<? extends Parcelable> value){
        mPostcard.withSparseParcelableArray(key, value);
        return this;
    }

    public NavigatorBuilder withIntegerArrayList(@NonNull String key, ArrayList<Integer> value){
        mPostcard.withIntegerArrayList(key, value);
        return this;
    }

    public NavigatorBuilder withStringArrayList(@NonNull String key, ArrayList<String> value){
        mPostcard.withStringArrayList(key, value);
        return this;
    }

    public NavigatorBuilder withCharSequenceArrayList(@NonNull String key, ArrayList<CharSequence> value){
        mPostcard.withCharSequenceArrayList(key, value);
        return this;
    }

    public NavigatorBuilder withSerializable(@NonNull String key, Serializable value){
        mPostcard.withSerializable(key, value);
        return this;
    }

    public NavigatorBuilder withByteArray(@NonNull String key, byte[] value){
        mPostcard.withByteArray(key, value);
        return this;
    }

    public NavigatorBuilder withShortArray(@NonNull String key, short[] value){
        mPostcard.withShortArray(key, value);
        return this;
    }

    public NavigatorBuilder withCharArray(@NonNull String key, char[] value){
        mPostcard.withCharArray(key, value);
        return this;
    }

    public NavigatorBuilder withFloatArray(@NonNull String key, float[] value){
        mPostcard.withFloatArray(key, value);
        return this;
    }

    public NavigatorBuilder withCharSequenceArray(@NonNull String key, CharSequence[] value){
        mPostcard.withCharSequenceArray(key, value);
        return this;
    }

    public NavigatorBuilder withBundle(@NonNull String key, Bundle value){
        mPostcard.withBundle(key, value);
        return this;
    }

    public NavigatorBuilder withTransition(int enterAnim, int exitAnim){
        mPostcard.withTransition(enterAnim, exitAnim);
        return this;
    }

    public NavigatorBuilder withObject(@NonNull String key, Object value){
        mPostcard.withObject(key, value);
        return this;
    }

    public NavigatorBuilder with(Bundle bundle){
        mPostcard.with(bundle);
        return this;
    }

    public String toString(){
        return mPostcard.toString();
    }

}
