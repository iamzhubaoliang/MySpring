package com.baoliang.spring.Helper;

import com.baoliang.spring.Interface.BeanPostProcessor;

import java.util.ArrayList;

/**
 * 年: 2021 月: 09日: 09小时: 22分钟: 30
 * 用户名: liangliang
 **/
public class BeanDefintion {
    private Class clazz;

    private String BeanName;

    public String getBeanName() {
        return BeanName;
    }

    public void setBeanName(String beanName) {
        BeanName = beanName;
    }



    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    //根据扩容原理，如果不添加元素，永远是空数组不占用空间
    private ArrayList<BeanPostProcessor> beanPostProcessors=new ArrayList<>();




    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }





    public ArrayList<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public void setBeanPostProcessors(ArrayList<BeanPostProcessor> beanPostProcessors) {
        this.beanPostProcessors = beanPostProcessors;
    }
}
