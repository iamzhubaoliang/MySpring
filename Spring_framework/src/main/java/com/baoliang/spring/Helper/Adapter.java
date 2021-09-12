package com.baoliang.spring.Helper;


import com.baoliang.spring.Data.Handler;
import com.baoliang.spring.Data.RequestParam;
import com.baoliang.spring.ProxyUtils.MvcProxy;

/**
 * 年: 2021 月: 09日: 12小时: 15分钟: 58
 * 用户名: liangliang
 **/
public class Adapter {

        public static Object AdapterForRequest(RequestParam requestParam, Handler handler)
        {
            Object result=null;
            if(requestParam.getParamMap().size()>0)
            {
                return result=MvcProxy.InvokeMethod(handler.getController(),handler.getControllerMethod(),requestParam.getParamMap());
            }else
            {
                return result=MvcProxy.InvokeMethod(handler.getController(),handler.getControllerMethod(),null);
            }
        }
}
