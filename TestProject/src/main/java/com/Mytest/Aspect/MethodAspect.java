package com.Mytest.Aspect;

import com.baoliang.spring.Annotation.*;

/**
 * 年: 2021 月: 09日: 12小时: 03分钟: 12
 * 用户名: liangliang
 **/
@Component
@Aspect
public class MethodAspect {
    @PointCut("com.Mytest.service.MethodAspect.methdFunction()")
    public void point()
    {}
    @Before
    public void beforeFunction()
    {
        System.out.println("加在类级别的前置通知执行");
    }
    @After
    public void afterFunction()
    {
        System.out.println("加在类级别的后置通知执行");
    }
}
