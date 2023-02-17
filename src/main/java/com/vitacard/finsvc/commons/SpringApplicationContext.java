package com.vitacard.finsvc.commons;

import com.vitacard.finsvc.domain.account.model.AccountFactory;
import com.vitacard.finsvc.domain.account.model.DepositAccountFactory;
import com.vitacard.finsvc.domain.application.infrastructure.IndividualApplicationEntityFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Returns the Spring managed bean instance of the given class type if it exists.
     * Returns null otherwise.
     * @param beanClass
     * @return
     */
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

    public static IndividualApplicationEntityFactory getIndividualApplicationEntityFactory() {
        return context.getBean(IndividualApplicationEntityFactory.class);
    }

    public static DepositAccountFactory getDepositAccountFactory() {
        return context.getBean(DepositAccountFactory.class);
    }

    public static AccountFactory getAccountFactory()  {
        return context.getBean(AccountFactory.class);
    }
}