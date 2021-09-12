package com.Mytest.controller;

import com.baoliang.spring.Annotation.Component;
import com.baoliang.spring.Annotation.Controller;
import com.baoliang.spring.Annotation.RequestMapping;
import com.baoliang.spring.Enum.RequestMethod;

/**
 * 年: 2021 月: 09日: 12小时: 03分钟: 03
 * 用户名: liangliang
 **/
@Controller
public class TestController {


    @RequestMapping(value = "runTest",method = RequestMethod.GET)
    public void testHanler()
    {
        System.out.println("Controller 方法执行");
    }
}
