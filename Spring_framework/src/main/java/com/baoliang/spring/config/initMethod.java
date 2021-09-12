package com.baoliang.spring.config;

import com.baoliang.spring.Helper.HandlerMapping;
import com.baoliang.spring.Helper.LoadBeanHelper;

import java.util.ResourceBundle;

/**
 * 年: 2021 月: 09日: 12小时: 16分钟: 30
 * 用户名: liangliang
 **/
public class initMethod {
    private static boolean isLoaded=false;

    public static void init(){
        if(isLoaded)
            return;
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String s=bundle.getString("componentScan");
        LoadBeanHelper.LoadAllClass(bundle.getString("componentScan"));
        LoadBeanHelper.LoadAllBean();
        LoadBeanHelper.ProductBean();
        HandlerMapping.getAllHandler();
        isLoaded=true;
    }
}
