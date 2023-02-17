package com.vitacard.finsvc.commons.unitevents;

import com.vitacard.finsvc.commons.DomainEvent;
import lombok.Value;
import unit.UnitResponseData;

import java.util.UUID;

@Value
public class UnitTransactionCreatedEvent implements DomainEvent {
    @Override
    public UUID getEventId() {
        return null;
    }
    String id;
    String accountId;
    long available;
    long balance;
    public static UnitTransactionCreatedEvent create(UnitResponseData unitResponseData) {
        String id = unitResponseData.getId();
        String accountId = unitResponseData.getRelationships().get("account").get(0).getId();
        long available = unitResponseData.getAttributes().get("available").getAsLong();
        long balance = unitResponseData.getAttributes().get("balance").getAsLong();
        return new UnitTransactionCreatedEvent(id, accountId, available, balance);
    }
}
