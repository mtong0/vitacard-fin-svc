package com.vitacard.finsvc.domain.transaction.model;
import com.vitacard.finsvc.domain.transaction.dtos.UnitCardTransaction;
import com.vitacard.finsvc.domain.transaction.infrasturcture.CardTransactionEntity;
import org.springframework.stereotype.Component;


@Component
public class TransactionFactory implements ITransactionFactory {
    public CardTransaction create(CardTransactionEntity cardTransactionEntity) {
        CardTransaction cardTransaction = CardTransaction.builder()
                .id(cardTransactionEntity.getId())
                .createdAt(cardTransactionEntity.getCreatedAt())
                .available(cardTransactionEntity.getAvailable())
                .balance(cardTransactionEntity.getBalance())
                .amount(cardTransactionEntity.getAmount())
                .type(cardTransactionEntity.getType())
                .direction(cardTransactionEntity.getDirection())
                .summary(cardTransactionEntity.getSummary())
                .merchantType(cardTransactionEntity.getMerchantType())
                .cardLast4Digits(cardTransactionEntity.getCardLast4Digits())
                .merchantName(cardTransactionEntity.getMerchantName())
                .merchantCategory(cardTransactionEntity.getMerchantCategory())
                .build();
        return cardTransaction;
    }

    public CardTransaction create(UnitCardTransaction unitCardTransaction) {
        return CardTransaction.builder()
                .id(unitCardTransaction.id())
                .amount(unitCardTransaction.amount())
                .available(unitCardTransaction.amount())
                .balance(unitCardTransaction.balance())
                .createdAt(unitCardTransaction.createdAt())
                .direction(unitCardTransaction.direction())
                .type(unitCardTransaction.type())
                .merchantCategory(unitCardTransaction.merchant().category())
                .merchantName(unitCardTransaction.merchant().name())
                .cardLast4Digits(unitCardTransaction.cardLast4Digits())
                .merchantType(unitCardTransaction.merchant().type())
                .build();
    }

}
