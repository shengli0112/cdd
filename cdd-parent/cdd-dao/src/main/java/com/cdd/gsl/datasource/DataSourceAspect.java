package com.cdd.gsl.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;

@Aspect
@Component
public class DataSourceAspect {
    private final static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
    private static final String DEFAULT_DATA_SOURCE_NAME = "master";
    @Pointcut("execution(* com.cdd.gsl.service.impl.*.*(..))")
    public void service(){

    }

    @Around("service()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        String previousSource = DynamicDataSourceHolder.getDataSource();
        try{
            String newSource = extractDataSourceFromPoint(point);
            switchSource(newSource);
            return  point.proceed();
        }finally {
            switchSource(previousSource);
        }
    }

    public void switchSource(String source){
        DynamicDataSourceHolder.putDataSource(source);
    }
    public String extractDataSourceFromPoint(JoinPoint point){
        try {
            Object target = point.getTarget();
            String method = point.getSignature().getName();
            Class<?>[] parameterTypes = ((MethodSignature)point.getSignature()).getMethod().getParameterTypes();
            Method m = target.getClass().getMethod(method,parameterTypes);
            if(m != null && m.isAnnotationPresent(DataSource.class)){
                DataSource d = m.getAnnotation(DataSource.class);
                return d != null ? d.value():DEFAULT_DATA_SOURCE_NAME;
            }else{
                return DEFAULT_DATA_SOURCE_NAME;
            }


        }catch (Exception e){
            return DEFAULT_DATA_SOURCE_NAME;
        }

    }
}
