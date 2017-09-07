package com.mondyxue.xrouter.utils;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@SuppressWarnings("unchecked")
public final class BundleUtils{

    private BundleUtils(){}

    public static boolean checkToPut(Bundle bundle, String key, Object value){
        if(value == null){
            bundle.remove(key);
        }else if(value instanceof String){
            bundle.putString(key, (String) value);
        }else if(value instanceof Boolean){
            bundle.putBoolean(key, (Boolean) value);
        }else if(value instanceof Long){
            bundle.putLong(key, (Long) value);
        }else if(value instanceof Integer){
            bundle.putInt(key, (Integer) value);
        }else if(value instanceof Float){
            bundle.putFloat(key, (Float) value);
        }else if(value instanceof Double){
            bundle.putDouble(key, (Double) value);
        }else if(value instanceof ArrayList){
            if(!((ArrayList) value).isEmpty()){
                Object o = ((ArrayList) value).get(0);
                if(o instanceof String){
                    bundle.putStringArrayList(key, (ArrayList<String>) value);
                }else if(o instanceof Parcelable){
                    bundle.putParcelableArrayList(key, (ArrayList<Parcelable>) value);
                }else if(o instanceof Integer){
                    bundle.putIntegerArrayList(key, (ArrayList<Integer>) value);
                }else if(o instanceof CharSequence){
                    bundle.putCharSequenceArrayList(key, (ArrayList<CharSequence>) value);
                }else{
                    bundle.putSerializable(key, (Serializable) value);
                }
            }
        }else if(value instanceof Bundle){
            bundle.putBundle(key, (Bundle) value);
        }else if(value instanceof Parcelable){
            bundle.putParcelable(key, (Parcelable) value);
        }else if(value instanceof Byte){
            bundle.putByte(key, (Byte) value);
        }else if(value instanceof Character){
            bundle.putChar(key, (Character) value);
        }else if(value instanceof CharSequence){
            bundle.putCharSequence(key, (CharSequence) value);
        }else if(value instanceof Short){
            bundle.putShort(key, (Short) value);
        }else if(value instanceof int[]){
            bundle.putIntArray(key, (int[]) value);
        }else if(value instanceof long[]){
            bundle.putLongArray(key, (long[]) value);
        }else if(value instanceof float[]){
            bundle.putFloatArray(key, (float[]) value);
        }else if(value instanceof byte[]){
            bundle.putByteArray(key, (byte[]) value);
        }else if(value instanceof char[]){
            bundle.putCharArray(key, (char[]) value);
        }else if(value instanceof short[]){
            bundle.putShortArray(key, (short[]) value);
        }else if(value instanceof CharSequence[]){
            bundle.putCharSequenceArray(key, (CharSequence[]) value);
        }else if(value instanceof Parcelable[]){
            bundle.putParcelableArray(key, (Parcelable[]) value);
        }else if(value instanceof Serializable){
            bundle.putSerializable(key, (Serializable) value);
        }else{
            return false;
        }
        return true;
    }

    public static Map<String,String> toQueryParameters(Bundle extras, @NonNull StringConverter converter){
        Map<String,String> params = new HashMap<>();
        for(String key : extras.keySet()){
            Object value = extras.get(key);
            if((value instanceof Integer)
               || (value instanceof Long)
               || (value instanceof String)
               || (value instanceof Boolean)
               || (value instanceof Float)
               || (value instanceof Double)
               || (value instanceof Short)
               || (value instanceof Byte)){
                params.put(key, value.toString());
            }else if((value instanceof int[])
                     || (value instanceof long[])
                     || (value instanceof float[])
                     || (value instanceof byte[])
                     || (value instanceof char[])
                     || (value instanceof short[])
                     || (value instanceof CharSequence[])
                     || (value instanceof Parcelable[])){
                List<Object> arrayList = Collections.singletonList(value);
                if(!arrayList.isEmpty()){
                    params.put(key, converter.convert(arrayList));
                }
            }else if(value instanceof Bundle){
                Map<String,String> bundleParams = toQueryParameters(((Bundle) value), converter);
                params.put(key, converter.convert(bundleParams));
            }else{
                params.put(key, converter.convert(value));
            }
        }
        return params;
    }

    public interface StringConverter{

        String convert(Object object);
    }

}
