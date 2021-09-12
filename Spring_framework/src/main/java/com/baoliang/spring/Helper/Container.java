package com.baoliang.spring.Helper;

import java.util.concurrent.ConcurrentHashMap;

public class Container {
    public static ConcurrentHashMap<String,Object> singletonObjects=new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String,Object> earlySingletonObjects=new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String,DynamicBeanFactory> singletonFactory=new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String,Object> controllerMap=new ConcurrentHashMap<>();
    public static ClassLoader classLoader=null;

}
