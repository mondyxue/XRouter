package com.mondyxue.xrouter.demo.api.service;

import com.alibaba.android.arouter.facade.service.SerializationService;

import java.util.List;

/**
 * Demo service for logger
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface DataParser extends SerializationService{

    <T> List<T> parseList(String json, Class<T> clazz);

}
