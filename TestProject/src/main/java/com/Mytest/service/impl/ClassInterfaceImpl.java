package com.Mytest.service.impl;

import com.Mytest.service.ClassAspectTestInterface;
import com.baoliang.spring.Annotation.Autowired;
import com.baoliang.spring.Annotation.Component;
import com.baoliang.spring.Interface.BeanNameAware;

/**
 * 年: 2021 月: 09日: 12小时: 03分钟: 05
 * 用户名: liangliang
 **/
@Component
public class ClassInterfaceImpl implements ClassAspectTestInterface {
    //项目利用的是JDK动态代理，所以必须要有接口，否则会出现cast Exception
    @Autowired
    PropertyInsert propertyInsert;




    @Override
    public void FunctionRun() {
        System.out.println("添加在类上面的通知执行");
    }
    public void Propertyfuntion()
    {
        propertyInsert.funtion();
    }


}
