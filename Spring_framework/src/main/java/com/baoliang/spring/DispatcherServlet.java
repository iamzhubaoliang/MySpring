package com.baoliang.spring;

import com.baoliang.spring.Data.Handler;
import com.baoliang.spring.Data.Request;
import com.baoliang.spring.Data.RequestParam;
import com.baoliang.spring.Data.View;
import com.baoliang.spring.Helper.Adapter;
import com.baoliang.spring.Helper.Container;
import com.baoliang.spring.Helper.HandlerMapping;
import com.baoliang.spring.Helper.ViewResolver;
import com.baoliang.spring.config.initMethod;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 年: 2021 月: 09日: 12小时: 14分钟: 59
 * 用户名: liangliang
 **/
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        Container.classLoader=this.getClass().getClassLoader();
        initMethod.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMehod = req.getMethod();
        String requestPath  = req.getPathInfo();//获取到的路径类似 /aa=xxx
        Request request=new Request(requestPath,requestMehod);

        //交给处理器映射器处理
        Handler handler= HandlerMapping.getHandler(request);
        if(handler==null)
            return;

        //请求处理器适配器适配器适配Parm
        RequestParam parm=new RequestParam();
        parm.creatParam(req);
        Object result=Adapter.AdapterForRequest(parm,handler);

        if(result instanceof View)
        {
            ViewResolver.handleViewResult((View) result,req,resp);
        }else if(result instanceof String)
        {
            ViewResolver.handleDataResult((String) result,resp);
        }



    }
}
