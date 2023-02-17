package com.vitacard.finsvc.domain.application.infrastructure;

import com.vitacard.finsvc.commons.DomainEvent;
import com.vitacard.finsvc.commons.events.SpringEvent;
import com.vitacard.finsvc.commons.unitevents.UnitTransactionCreatedEvent;
import com.vitacard.finsvc.domain.account.facet.AccountFacet;
import com.vitacard.finsvc.domain.application.facet.ApplicationFacet;
import com.vitacard.finsvc.domain.application.model.ApplicationEvent;
import com.vitacard.finsvc.domain.transaction.TransactionFacet;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Component
public class ApplicationEventListener implements ApplicationListener<org.springframework.context.ApplicationEvent> {
    @Autowired
    private ApplicationFacet applicationFacet;
    @Autowired
    private AccountFacet accountFacet;
    @Autowired
    private TransactionFacet transactionFacet;
    @Override
    public void onApplicationEvent(org.springframework.context.ApplicationEvent event) {
        DomainEvent domainEvent = ((SpringEvent) event).getDomainEvent();
        Match(domainEvent).of(
                Case($(instanceOf(ApplicationEvent.class)), this::handle),
                Case($(instanceOf(UnitTransactionCreatedEvent.class)), this::handle));
    }

    public String handle(ApplicationEvent event) {
        return applicationFacet.handle(event);
    }

    public String handle(UnitTransactionCreatedEvent event) {
        transactionFacet.handle(event);
        accountFacet.handle(event);
        return null;
    }
}
