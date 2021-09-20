package com.baoliang.spring.Helper.System;

import java.util.Properties;

public class Utils {
    public static String getSystem() {
        Properties props = System.getProperties(); //获得系统属性集
        String osName = props.getProperty("os.name"); //操作系统名称
        return osName;
    }

}
