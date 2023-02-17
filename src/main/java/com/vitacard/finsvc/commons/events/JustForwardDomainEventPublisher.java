package com.vitacard.finsvc.commons.events;

import com.vitacard.finsvc.commons.DomainEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class JustForwardDomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(new SpringEvent(this, event));
    }
}