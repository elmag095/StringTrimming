package org.example.configs;

import org.example.anotations.Trimmed;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

public class TrimmedAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        Method[] methods = aClass.getMethods();

        for (Method method : methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            boolean isParameterTypeString = false;
            for (Class<?> parameterType : parameterTypes) {
                if (parameterType.isAssignableFrom(String.class)) {
                    isParameterTypeString = true;
                }
            }

            if (method.isAnnotationPresent(Trimmed.class) && isParameterTypeString) {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(aClass);
                MethodInterceptor methodInterceptor = (o, method1, objects, methodProxy) -> {
                    for (int i = 0; i < objects.length; i++) {
                        if (objects[i].getClass().isAssignableFrom(String.class)) {
                            objects[i] = objects[i].toString().trim();
                        }
                    }
                    return methodProxy.invokeSuper(o, objects);
                };
                enhancer.setCallback(methodInterceptor);
                return enhancer.create();
            }
        }
        return bean;
    }
}
