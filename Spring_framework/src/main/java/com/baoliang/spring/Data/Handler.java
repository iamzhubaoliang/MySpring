package com.baoliang.spring.Data;

import java.lang.reflect.Method;

/**
 * 年: 2021 月: 09日: 12小时: 15分钟: 15
 * 用户名: liangliang
 **/
public class Handler {
    private Object Controller;
    private Method ControllerMethod;

    public Handler(Object controller, Method controllerMethod) {
        Controller = controller;
        ControllerMethod = controllerMethod;
    }

    public Object getController() {
        return Controller;
    }

    public void setController(Object controller) {
        Controller = controller;
    }

    public Method getControllerMethod() {
        return ControllerMethod;
    }

    public void setControllerMethod(Method controllerMethod) {
        ControllerMethod = controllerMethod;
    }
}
