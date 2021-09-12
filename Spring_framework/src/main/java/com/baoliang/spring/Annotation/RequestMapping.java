package com.baoliang.spring.Annotation;

import com.baoliang.spring.Enum.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 年: 2021 月: 09日: 12小时: 15分钟: 47
 * 用户名: liangliang
 **/

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value() default "";

    RequestMethod method() default RequestMethod.GET;
}
