package com.baoliang.spring.ProxyUtils;


import com.baoliang.spring.Helper.MethodNode;
import com.baoliang.spring.Helper.Container;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class BeanProxyUtil implements InvocationHandler {

    private Object tartget;
    private ArrayList<MethodNode> BeforemethodCache;
    private ArrayList<MethodNode> AftermethodCache;

    public ArrayList<MethodNode> getBeforemethodCache() {
        return BeforemethodCache;
    }

    public void setBeforemethodCache(ArrayList<MethodNode> beforemethodCache) {
        BeforemethodCache = beforemethodCache;
    }

    public ArrayList<MethodNode> getAftermethodCache() {
        return AftermethodCache;
    }

    public void setAftermethodCache(ArrayList<MethodNode> aftermethodCache) {
        AftermethodCache = aftermethodCache;
    }




    public Object creatCarProxy(Object target) {
        this.tartget=target;
        try {
            Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
            return proxy;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        Object result=null;
        //整个类级别的切面方法执行

        if(BeforemethodCache!=null)
        for(MethodNode beforMethod:BeforemethodCache)
        {
            if((!beforMethod.isFunction()))
            {
                String[] name=beforMethod.getMethod().getDeclaringClass().toString().split("\\.");
                beforMethod.getMethod().invoke(Container.singletonObjects.get(name[name.length-1]));
            }
        }


        //特定方法执行
        if(BeforemethodCache!=null)
        for(MethodNode beforMethod:BeforemethodCache)
        {
            if(beforMethod.isFunction()&&beforMethod.getMethodName().equals(method.getName()))
            {
                String[] name=beforMethod.getMethod().getDeclaringClass().toString().split("\\.");
                beforMethod.getMethod().invoke(Container.singletonObjects.get(name[name.length-1]));
            }
        }
        result=method.invoke(tartget,args);
        if(AftermethodCache!=null)
        for(MethodNode afterMethod:AftermethodCache)
        {
            if(afterMethod.isFunction()&&afterMethod.getMethodName().equals(method.getName()))
            {
                String[] name=afterMethod.getMethod().getDeclaringClass().toString().split("\\.");
                afterMethod.getMethod().invoke(Container.singletonObjects.get(name[name.length-1]));
            }
        }
        //类级别
        if(AftermethodCache!=null)
        for(MethodNode afterMethod:AftermethodCache)
        {
            if((!afterMethod.isFunction()))
            {
                String[] name=afterMethod.getMethod().getDeclaringClass().toString().split("\\.");
                afterMethod.getMethod().invoke(Container.singletonObjects.get(name[name.length-1]));
            }
        }

      return  result;
    }

}
