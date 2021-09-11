package com.Mytest.test;

import com.Mytest.service.ClassAspectTestInterface;
import com.Mytest.service.MethodAspectTestInterface;
import com.Mytest.service.impl.ClassInterfaceImpl;
import com.Mytest.service.impl.MethodAspectImpl;
import com.baoliang.spring.MySpringApplication;

/**
 * 年: 2021 月: 09日: 10小时: 10分钟: 39
 * 用户名: liangliang
 **/
public class test {
    public static void main(String[] args) {
        MySpringApplication app = new MySpringApplication(config.class);
        ClassAspectTestInterface classInterface = app.getBean(ClassInterfaceImpl.class);
        classInterface.FunctionRun();
        ((ClassInterfaceImpl) classInterface).Propertyfuntion();
        System.out.println("Bean的名称为"+((ClassInterfaceImpl) classInterface).BeanName);
        MethodAspectTestInterface methodinterface = app.getBean(MethodAspectImpl.class);
        methodinterface.methdFunction();

        MethodAspectTestInterface aa= (MethodAspectTestInterface) app.getBean("Namefor");
        aa.methdFunction();
    }


}
