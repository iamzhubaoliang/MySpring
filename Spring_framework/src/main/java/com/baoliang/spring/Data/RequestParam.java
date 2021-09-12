package com.baoliang.spring.Data;

/**
 * 年: 2021 月: 09日: 12小时: 15分钟: 22
 * 用户名: liangliang
 **/
public class RequestParam {
    private String requestPath;
    private String requestMethod;

    public RequestParam(String requestPath, String requestMethod) {
        this.requestPath = requestPath;
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
}
