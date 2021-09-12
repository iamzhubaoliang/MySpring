package com.baoliang.spring.Data;

import java.lang.reflect.Method;

/**
 * 年: 2021 月: 09日: 12小时: 15分钟: 15
 * 用户名: liangliang
 **/
public class Handler {
    private Class<?> ControllerClass;
    private Method ControllerMethod;

    public Handler(Class<?> controllerClass, Method controllerMethod) {
        ControllerClass = controllerClass;
        ControllerMethod = controllerMethod;
    }

    public Class<?> getControllerClass() {
        return ControllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        ControllerClass = controllerClass;
    }

    public Method getControllerMethod() {
        return ControllerMethod;
    }

    public void setControllerMethod(Method controllerMethod) {
        ControllerMethod = controllerMethod;
    }



}
