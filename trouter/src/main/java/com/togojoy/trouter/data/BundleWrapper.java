package com.togojoy.trouter.data;

import android.os.Bundle;

import com.togojoy.trouter.constant.Type;
import com.togojoy.trouter.utils.TypeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <br></br>Created by MondyXue
 * <br></br>E-MAIL: mondyxue@gmial.com
 */
public class BundleWrapper implements IBundleWrapper{

    private Bundle mBundle;

    public BundleWrapper(){
        this(new Bundle());
    }
    public BundleWrapper(Bundle bundle){
        this.mBundle = bundle;
    }

    @Override public IBundleWrapper put(@Type int type, String key, String value){
        switch(type){
        case Type.Boolean:
            mBundle.putBoolean(key, Boolean.parseBoolean(value));
            break;
        case Type.Double:
            mBundle.putDouble(key, Double.parseDouble(value));
            break;
        case Type.Float:
            mBundle.putFloat(key, Float.parseFloat(value));
            break;
        case Type.Int:
            mBundle.putInt(key, Integer.parseInt(value));
            break;
        case Type.Long:
            mBundle.putLong(key, Long.parseLong(value));
            break;
        case Type.String:
            mBundle.putString(key, value);
            break;
        case Type.CharSequence:
        case Type.Serializable:
        case Type.StringArrayList:
        case Type.StringArray:
        default:
            throw new IllegalArgumentException("unsupport extra [" + value + "]");
        }
        return this;
    }

    @Override public IBundleWrapper put(String key, Object value){
        if(value == null){
            mBundle.remove(key);
        }else if(value instanceof String){
            mBundle.putString(key, (String) value);
        }else if(value instanceof CharSequence){
            mBundle.putCharSequence(key, (CharSequence) value);
        }else if(value instanceof Boolean){
            mBundle.putBoolean(key, (Boolean) value);
        }else if(value instanceof Long){
            mBundle.putLong(key, (Long) value);
        }else if(value instanceof Integer){
            mBundle.putInt(key, (Integer) value);
        }else if(value instanceof Float){
            mBundle.putFloat(key, (Float) value);
        }else if(value instanceof Double){
            mBundle.putDouble(key, (Double) value);
        }else if(value instanceof String[]){
            mBundle.putStringArray(key, (String[]) value);
        }else if(value instanceof ArrayList){
            try{
                mBundle.putStringArrayList(key, (ArrayList<String>) value);
            }catch(Exception ignored){
                mBundle.putSerializable(key, (Serializable) value);
            }
        }else if(value instanceof Serializable){
            mBundle.putSerializable(key, (Serializable) value);
        }else{
            throw new IllegalArgumentException("unsupport extra:" + value);
        }
        return this;
    }

    @Override public IBundleWrapper put(String key, ArrayList<String> value){
        mBundle.putStringArrayList(key, value);
        return this;
    }
    @Override public IBundleWrapper put(String key, String[] value){
        mBundle.putStringArray(key, value);
        return this;
    }
    @Override public <T extends Serializable> IBundleWrapper put(String key, List<T> value){
        mBundle.putSerializable(key, new ListInfo<T>(value));
        return this;
    }
    @Override public IBundleWrapper put(Bundle bundle){
        bundle.putAll(bundle);
        return this;
    }
    @Override public String getString(String key){
        return getString(key, "");
    }
    @Override public String getString(String key, String def){
        return mBundle.getString(key, def);
    }
    @Override public Long getLong(String key){
        return getLong(key, -1L);
    }
    @Override public Long getLong(String key, Long def){
        return TypeUtils.parseLong(mBundle.get(key), def);
    }
    @Override public Integer getInt(String key){
        return getInt(key, -1);
    }
    @Override public Integer getInt(String key, Integer def){
        return TypeUtils.parseInt(mBundle.get(key), def);
    }
    @Override public Double getDouble(String key){
        return getDouble(key, -1D);
    }
    @Override public Double getDouble(String key, Double def){
        return TypeUtils.parseDouble(mBundle.get(key), def);
    }
    @Override public Boolean getBoolean(String key){
        return getBoolean(key, false);
    }
    @Override public Boolean getBoolean(String key, Boolean def){
        return TypeUtils.parseBoolean(mBundle.get(key), def);
    }
    @Override public Float getFloat(String key){
        return getFloat(key, -1F);
    }
    @Override public Float getFloat(String key, Float def){
        return TypeUtils.parseFloat(mBundle.get(key), def);
    }
    @Override public <T extends Serializable> T getSerializable(String key){
        return (T) mBundle.getSerializable(key);
    }
    @Override public <T extends Serializable> List<T> getList(String key){
        Serializable serializable = mBundle.getSerializable(key);
        if(serializable != null){
            if(serializable instanceof ListInfo){
                return ((ListInfo<T>) serializable).getData();
            }else if(serializable instanceof ArrayList){
                return ((ArrayList) serializable);
            }
        }
        return null;
    }
    @Override public ArrayList<String> getStringArrayList(String key){
        return mBundle.getStringArrayList(key);
    }
    @Override
    public String[] getStringArray(String key){
        return mBundle.getStringArray(key);
    }
    @Override public Bundle getBundle(){
        return mBundle;
    }
    @Override public boolean isEmpty(){
        return mBundle.isEmpty();
    }
    @Override
    public void clear(){
        mBundle.clear();
    }

    private class ListInfo<T extends Serializable> implements Serializable{

        private List<T> data;
        ListInfo(List<T> data){
            this.data = data;
        }
        List<T> getData(){
            return data;
        }
    }

}