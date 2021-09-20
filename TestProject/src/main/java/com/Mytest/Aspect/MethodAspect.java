package com.Mytest.Aspect;

import com.baoliang.spring.Annotation.*;

/**
 * 年: 2021 月: 09日: 12小时: 03分钟: 12
 * 用户名: liangliang
 **/
@Component
@Aspect
public class MethodAspect {
    @PointCut("com.Mytest.service.impl.MethodAspectImpl.methdFunction()")
    public void point()
    {}
    @Before
    public void beforeFunction()
    {
        System.out.println("方法前置");
    }
    @After
    public void afterFunction()
    {
        System.out.println("方法后置");
    }
}
