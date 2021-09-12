package com.baoliang.spring.Data;

import java.util.Objects;

/**
 * 年: 2021 月: 09日: 12小时: 15分钟: 38
 * 用户名: liangliang
 **/
public class Request {
    private String RequestPath;
    private String RequestMethod;

    public Request(String requestPath, String requestMethod) {
        RequestPath = requestPath;
        RequestMethod = requestMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;

        return RequestPath.equals(request.RequestPath) && RequestMethod.equals(request.RequestMethod);
    }

    @Override
    public int hashCode() {
        return RequestPath.hashCode()&RequestMethod.hashCode()&21;
    }



    public String getRequestPath() {
        return RequestPath;
    }

    public void setRequestPath(String requestPath) {
        RequestPath = requestPath;
    }

    public String getRequestMethod() {
        return RequestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        RequestMethod = requestMethod;
    }
}
