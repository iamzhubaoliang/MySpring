package com.baoliang.spring;


import com.baoliang.spring.Helper.LoadBeanHelper;
import com.baoliang.spring.Helper.Container;
import com.baoliang.spring.Annotation.CompoentScan;

public class MySpringApplication {

    static {
        ClassLoader classLoader = MySpringApplication.class.getClassLoader();//拿到应用类加载器
        Container.classLoader=classLoader;
    }
    public MySpringApplication(Class config) {
        CompoentScan componentScanAnnotation=(CompoentScan) config.getAnnotation(CompoentScan.class);
        String path=componentScanAnnotation.value();
        LoadBeanHelper.LoadAllClass(path);
        LoadBeanHelper.LoadAllBean();
        LoadBeanHelper.ProductBean();
    }
    public <T>  T getBean(Class<T> s)
    {
        String[] name=s.getName().split("\\.");
        return (T) LoadBeanHelper.getBean(name[name.length-1]);
    }
    public Object getBean(String beanName)
    {

        return LoadBeanHelper.getBean(beanName);
    }
}
