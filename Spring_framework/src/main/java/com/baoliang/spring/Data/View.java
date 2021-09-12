package com.baoliang.spring.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 年: 2021 月: 09日: 12小时: 18分钟: 46
 * 用户名: liangliang
 **/
public class View {

    private String path=null;

    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
