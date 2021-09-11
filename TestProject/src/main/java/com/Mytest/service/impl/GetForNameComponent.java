package com.Mytest.service.impl;

import com.Mytest.service.MethodAspectTestInterface;
import com.baoliang.spring.Annotation.Component;

/**
 * 年: 2021 月: 09日: 12小时: 03分钟: 34
 * 用户名: liangliang
 **/
@Component("Namefor")
public class GetForNameComponent implements MethodAspectTestInterface {
    @Override
    public void methdFunction() {
        System.out.println("利用名称获取Bean");
    }
}
