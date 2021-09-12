package com.Mytest.controller;

import com.baoliang.spring.Annotation.Controller;
import com.baoliang.spring.Annotation.RequestMapping;
import com.baoliang.spring.Data.View;
import com.baoliang.spring.Enum.RequestMethod;


/**
 * 年: 2021 月: 09日: 12小时: 03分钟: 03
 * 用户名: liangliang
 **/
@Controller
public class TestController {


    @RequestMapping(value = "runTest",method = RequestMethod.GET)
    public String testHanler(String name,String pass)
    {
        return "名字是"+name+","+"密码是"+pass;
    }
    @RequestMapping(value = "second",method = RequestMethod.GET)//second?name="xxx"
    public View secondeHandler(String parm)
    {
        View view=new View("/runthrid");
        view.addModel("name","小红");
        return view;
    }
    @RequestMapping(value = "runthrid",method = RequestMethod.GET)
    public String third()
    {
        return "小明和小红";
    }
}
