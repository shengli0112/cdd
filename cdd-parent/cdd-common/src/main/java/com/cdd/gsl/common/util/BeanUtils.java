package com.cdd.gsl.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;


public class BeanUtils<T> {

    private static BeanUtils instance;

    private BeanUtils(){
    }

    public synchronized  static BeanUtils getInstance(){
        if (instance == null) {
            instance = new BeanUtils();
        }
        return instance;
    }

    /**
     * 获取某个对象某个属性的值
     * @param t 对象
     * @param fieldName 属性值
     * @return
     */
    public  Object getKeyValue(T t, Object fieldName) {
        //如果是map类型的
        if(t instanceof Map){
            try {
                Method getMethod =  t.getClass().getDeclaredMethod("get", Object.class);
                return getMethod.invoke(t,fieldName);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        //如果是对象类型
        Field[] fieldList = t.getClass().getDeclaredFields();

        for(Field field : fieldList){
            try {
                field.setAccessible(true);
                if(fieldName.equals(field.getName())){
                    return field.get(t);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        fieldList = t.getClass().getSuperclass().getDeclaredFields();

        for(Field field : fieldList){
            try {
                field.setAccessible(true);
                if(fieldName.equals(field.getName())){
                    return field.get(t);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return "";
    }
}
