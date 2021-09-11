package com.Mytest.Processor;

import com.baoliang.spring.Annotation.Component;
import com.baoliang.spring.Interface.BeanPostProcessor;

/**
 * 年: 2021 月: 09日: 12小时: 03分钟: 14
 * 用户名: liangliang
 **/
@Component
public class ProcessorTets implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println(beanName+"的postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println(beanName+"的postProcessAfterInitialization");
        return bean;
    }
}
