package com.mondyxue.xrouter.demo.base.service.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.fastjson.JSON;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.api.navigator.BaseNavigator;
import com.mondyxue.xrouter.demo.api.service.DataParser;

import java.util.List;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Route(path = BaseNavigator._DataParser, extras = RouteType.GreenService)
public class DataParserImpl implements DataParser, SerializationService{

    @Override public <T> T json2Object(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    @Override public String object2Json(Object obj){
        return JSON.toJSONString(obj);
    }

    @Override public <T> List<T> parseList(String json, Class<T> clazz){
        return JSON.parseArray(json, clazz);
    }

    @Override public void init(Context context){

    }

}
