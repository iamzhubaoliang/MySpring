package com.baoliang.spring.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * 年: 2021 月: 09日: 12小时: 15分钟: 22
 * 用户名: liangliang
 **/
public class RequestParam {
    private HashMap<String,Object>paramMap=new HashMap<>();

    public void creatParam(HttpServletRequest request)
    {
        Enumeration<String> parameterNames = request.getParameterNames();
        if(!parameterNames.hasMoreElements())
            return;
        while (parameterNames.hasMoreElements())
        {
            String key=parameterNames.nextElement();
            String value=request.getParameter(key);
            paramMap.put(key,value);
        }

    }
    public HashMap<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(HashMap<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
