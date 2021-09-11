package com.baoliang.spring.Enum;

/**
 * 年: 2021 月: 09日: 09小时: 23分钟: 00
 * 用户名: liangliang
 **/
public enum ScopeEnum {
    SingleTon("single"),ProtoType("prototype");
    private String name;

    private ScopeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
