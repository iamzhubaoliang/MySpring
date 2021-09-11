package com.Mytest.service.impl;

import com.Mytest.service.MethodAspectTestInterface;
import com.baoliang.spring.Annotation.Component;

/**
 * 年: 2021 月: 09日: 12小时: 03分钟: 08
 * 用户名: liangliang
 **/
@Component
public class MethodAspectImpl implements MethodAspectTestInterface {
    @Override
    public void methdFunction() {
        System.out.println("方法上通知执行");
    }
}
