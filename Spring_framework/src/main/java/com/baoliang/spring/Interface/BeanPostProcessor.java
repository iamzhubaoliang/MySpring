package com.baoliang.spring.Interface;

/**
 * 年: 2021 月: 09日: 09小时: 22分钟: 18
 * 用户名: liangliang
 **/
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName);

    Object postProcessAfterInitialization(Object bean, String beanName);
}
