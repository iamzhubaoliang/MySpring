package com.baoliang.spring.ProxyUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * 年: 2021 月: 09日: 12小时: 18分钟: 50
 * 用户名: liangliang
 **/
public class MvcProxy {
    public static Object InvokeMethod(Object target, Method method, Map<String,Object> paramMap)
    {
            Object res = null;

        try {
            method.setAccessible(true);
            if(paramMap!=null) {
                Parameter[] parameters = method.getParameters();
                Object[] par = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    par[i] = paramMap.get(parameters[i].getName());
                    String a=parameters[i].getName();
                }
                res = method.invoke(target, par);
            }else
                res = method.invoke(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return res;
    }
}
