package com.vitacard.finsvc.domain.account.model;

import com.vitacard.finsvc.commons.events.DomainEvent;

import java.util.UUID;

public interface AccountEvent extends DomainEvent {
    record AccountTransactionEvent(
            String accountId,
            long updatedBalance,
            long updatedAvailable
    ) implements AccountEvent {
     @Override
        public UUID getEventId() {
            return null;
        }

        public static AccountTransactionEvent createTransactionEvent(String accountId, long updatedAvailable, long updatedBalance) {
            return new AccountTransactionEvent(accountId, updatedBalance, updatedAvailable);
        }
    }
}
