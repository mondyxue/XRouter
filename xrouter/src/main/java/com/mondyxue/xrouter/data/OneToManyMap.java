package com.mondyxue.xrouter.data;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public class OneToManyMap<K, V>{

    private Map<K,List<V>> mContainer;

    public OneToManyMap(){
        mContainer = new HashMap<>();
    }

    public V put(K key, V value){
        List<V> values = mContainer.get(key);
        if(values == null){
            values = new ArrayList<>();
            mContainer.put(key, values);
        }
        if(values.isEmpty() || !values.contains(value)){
            values.add(value);
        }
        return value;
    }

    @Nullable public V get(K key){
        List<V> values = mContainer.get(key);
        if(values != null && !values.isEmpty()){
            return values.get(values.size() - 1);
        }else{
            return null;
        }
    }
    @Nullable public V remove(K key){
        List<V> values = mContainer.get(key);
        if(values != null && !values.isEmpty()){
            V remove = values.remove(values.size() - 1);
            if(values.isEmpty()){
                mContainer.remove(key);
            }
            return remove;
        }else{
            return null;
        }
    }

    @Nullable public List<V> getValues(K key){
        return mContainer.get(key);
    }
    @Nullable public List<V> removeValues(K key){
        return mContainer.remove(key);
    }

    public boolean containsKey(K key){
        return get(key) != null;
    }
    public boolean containsValue(V value){
        Collection<List<V>> values = mContainer.values();
        if(!values.isEmpty()){
            for(List<V> vs : values){
                if(!vs.isEmpty() && vs.contains(value)){
                    return true;
                }
            }
        }
        return false;
    }

    public int size(){
        int size = 0;
        Collection<List<V>> values = mContainer.values();
        if(!values.isEmpty()){
            for(List<V> value : values){
                size += value.size();
            }
        }
        return size;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

}
