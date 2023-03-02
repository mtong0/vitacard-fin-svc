package com.vitacard.finsvc.commons.unit;

import com.vitacard.finsvc.commons.events.DomainEvent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import unit.UnitResponseData;

import java.util.UUID;

public record UnitTransactionCallbackEvent(
        @NotBlank String id,
        @NotBlank String accountId,
        long available,
        long balance,
        @NotBlank String customerId,
        long amount
) implements DomainEvent {
    public static final String TYPE = "transaction.created";
    @Override
    public UUID getEventId() {
        return null;
    }

    public static UnitTransactionCallbackEvent create(UnitResponseData unitResponseData) {
        UnitTransactionCreatedDto unitTransactionCreatedDto = new UnitTransactionCreatedDto(unitResponseData);
        String id = unitTransactionCreatedDto.getId();
        String accountId = unitTransactionCreatedDto.getAccountId();
        String customerId = unitTransactionCreatedDto.getCustomerId();
        long available = unitTransactionCreatedDto.getAvailable();
        long balance = unitTransactionCreatedDto.getBalance();
        long amount = unitTransactionCreatedDto.getAmount();
        return new UnitTransactionCallbackEvent(id, accountId, available, balance, customerId, amount);
    }
}
