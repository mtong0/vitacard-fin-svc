package com.vitacard.finsvc.commons.events;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {

    UUID getEventId();

//    UUID getAggregateId();
//
//    Instant getWhen();
}
