package com.vitacard.finsvc.domain.transaction.model;

import com.vitacard.finsvc.commons.events.DomainEvent;
import com.vitacard.finsvc.domain.transaction.dtos.CardTransactionDto;
import lombok.Value;

import java.util.UUID;

public interface TransactionEvent extends DomainEvent {
    @Value
    class SendTransactionEvent implements TransactionEvent {
        @Override
        public UUID getEventId() {
            return null;
        }
        CardTransactionDto cardTransactionDto;
        public static SendTransactionEvent create(String customerId, long amount, String merchantType) {
            CardTransactionDto info = CardTransactionDto.builder()
                    .customerId(customerId)
                    .amount(amount)
                    .merchantType(merchantType)
                    .build();
            return new SendTransactionEvent(info);
        }
    }
}


