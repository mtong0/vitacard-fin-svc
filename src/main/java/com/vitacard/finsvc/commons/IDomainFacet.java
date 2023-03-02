package com.vitacard.finsvc.commons;

import com.vitacard.finsvc.commons.events.DomainEvent;
import jakarta.validation.constraints.NotNull;

public interface IDomainFacet {
    String handle(@NotNull DomainEvent event);
}
