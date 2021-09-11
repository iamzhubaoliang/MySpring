package com.baoliang.spring.Helper;

import com.baoliang.spring.ProxyUtils.BeanProxyUtil;

import java.util.ArrayList;

public class DynamicBeanFactory {
    private BeanDefintion beanDefintion;
    private Object instance;
    private Boolean isDelegated=false;
    private ArrayList<MethodNode> BeforemethodCache;
    private ArrayList<MethodNode> AftermethodCache;


    public Boolean getDelegated() {
        return isDelegated;
    }

    public void setDelegated(Boolean delegated) {
        isDelegated = delegated;
    }

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


    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public BeanDefintion getBeanDefintion() {
        return beanDefintion;
    }

    public void setBeanDefintion(BeanDefintion beanDefintion) {
        this.beanDefintion = beanDefintion;
    }

    //此方法用于返回是不是动态代理对象
    public Object getTarget() {
        if(isDelegated)
        {
            BeanProxyUtil beanProxyUtil=new BeanProxyUtil();
            beanProxyUtil.setAftermethodCache(getAftermethodCache());
            beanProxyUtil.setBeforemethodCache(getBeforemethodCache());
            return beanProxyUtil.creatCarProxy(instance);
        }else
            return instance;
    }




}
