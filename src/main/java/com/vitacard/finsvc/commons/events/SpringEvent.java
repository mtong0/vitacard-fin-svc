package com.vitacard.finsvc.commons.events;

import com.vitacard.finsvc.commons.DomainEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class SpringEvent extends ApplicationEvent {
    private DomainEvent domainEvent;
    public SpringEvent(Object source, DomainEvent domainEvent) {
        super(source);
        this.domainEvent = domainEvent;
    }
}
