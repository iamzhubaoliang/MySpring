package com.baoliang.spring.Helper;

import com.baoliang.spring.Annotation.Controller;
import com.baoliang.spring.Annotation.RequestMapping;
import com.baoliang.spring.Data.Handler;
import com.baoliang.spring.Data.Request;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 年: 2021 月: 09日: 12小时: 15分钟: 35
 * 用户名: liangliang
 **/
public class HandlerMapping {
    private static HashMap<Request, Handler>HandlerMap =new HashMap<>();

    public static void getAllHandler()
    {
        for(Object controllerName: Container.controllerMap.keySet())
        {
            Object controllerInstance=Container.controllerMap.get(controllerName);
            for(Method method:controllerInstance.getClass().getDeclaredMethods())
            {
                if(!method.isAnnotationPresent(RequestMapping.class))
                    return;
                RequestMapping requestMapping=method.getAnnotation(RequestMapping.class);
                String path=requestMapping.value();
                String requestMethod=requestMapping.method().name();
                //将适用的方法封装成请求体
                Request request=new Request("/"+path,requestMethod);

                Handler handler=new Handler(controllerInstance,method);

                HandlerMap.put(request,handler);
            }


        }

    }


    public static Handler getHandler(Request request)
    {
        if(HandlerMap.containsKey(request))
            return HandlerMap.get(request);
        else
          return null;
    }
}
