package com.baoliang.spring.Helper;

import java.lang.reflect.Method;

/**
 * 年: 2021 月: 09日: 10小时: 14分钟: 06
 * 用户名: liangliang
 **/
public class MethodNode {
    private Method method;
    private boolean isFunction;
    private String methodName;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public MethodNode(Method method, boolean isFunction) {
        this.method = method;
        this.isFunction = isFunction;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public boolean isFunction() {
        return isFunction;
    }

    public void setFunction(boolean function) {
        isFunction = function;
    }
}
