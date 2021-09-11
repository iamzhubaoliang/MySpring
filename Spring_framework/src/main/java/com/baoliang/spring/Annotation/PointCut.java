package com.baoliang.spring.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 年: 2021 月: 09日: 10小时: 11分钟: 28
 * 用户名: liangliang
 **/
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PointCut {
    String value();
}
