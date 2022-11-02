package com.example.weatherforecast.annotations;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

public class PrintResultAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {


        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            PrintResult annotation = method.getAnnotation(PrintResult.class);
            if (annotation != null) {
                System.out.println(method.toString());
                System.out.println("annotation working");
            } else {
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
