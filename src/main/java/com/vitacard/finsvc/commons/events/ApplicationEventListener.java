package com.vitacard.finsvc.commons.events;

import com.vitacard.finsvc.app.ProcessTransaction;

import com.vitacard.finsvc.commons.unit.UnitTransactionCallbackEvent;
import com.vitacard.finsvc.domain.application.facet.ApplicationFacet;
import com.vitacard.finsvc.domain.application.model.ApplicationEvent;
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
    private ProcessTransaction processTransaction;

    @Override
    public void onApplicationEvent(org.springframework.context.ApplicationEvent event) {
        DomainEvent domainEvent = ((SpringEvent) event).getDomainEvent();
        Match(domainEvent).of(
                Case($(instanceOf(ApplicationEvent.class)), this::handle),
                Case($(instanceOf(UnitTransactionCallbackEvent.class)), this::handle));
    }

    public String handle(ApplicationEvent event) {
        return applicationFacet.handle(event);
    }

    public String handle(UnitTransactionCallbackEvent event) {
        processTransaction.handle(event);
        return null;
    }
}
