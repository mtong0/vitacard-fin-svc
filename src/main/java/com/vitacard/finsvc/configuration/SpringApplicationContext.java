package com.vitacard.finsvc.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        // store ApplicationContext reference to access required beans later on
        setContext(context);
    }

    /**
     * Private method context setting (better practice for setting a static field in a bean
     * instance - see comments of this article for more info).
     */
    private static synchronized void setContext(ApplicationContext context) {
        SpringApplicationContext.context = context;
    }
}