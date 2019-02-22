package com.cdd.gsl.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangchao on 17-8-8.
 */
public class JsonUtil {

    public static <T> String Java2Json(T t){
        return JSON.toJSONString(t);
    }

    public static <T> String JavaList2Json(List<T> tList){
        return JSON.toJSONString(tList);
    }

    public static <T> T Json2Java(String json,Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> Json2JavaList(String json,Class<T> clazz){
        return JSON.parseArray(json,clazz);
    }


    public static <T> String JavaMap2Json(Map map){
        return JSON.toJSONString(map);
    }


    public static <T,R> Map<T,R> Json2JavaMap(String json ,Class<T> keyClass,Class<R> valueClass) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<T, R> resMap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> it = jsonObject.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<T, R> param = (Map.Entry<T, R>) it.next();
            resMap.put(JSON.parseObject((String) param.getKey(),keyClass),Json2Java(JSONObject.toJSONString(param.getValue()),valueClass));
        }
        return resMap;

    }




}

