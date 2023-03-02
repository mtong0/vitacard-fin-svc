package com.vitacard.finsvc.domain.transaction.infrasturcture;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.vitacard.finsvc.domain.transaction.model.CardTransaction;
import com.vitacard.finsvc.domain.transaction.model.TransactionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionFactory transactionFactory;

    @Test
    void addTransaction() {
        CardTransactionEntity transactionEntity = CardTransactionEntity.builder()
                .id("123456")
                .cardLast4Digits("7890")
                .merchantName("Acme Inc.")
                .merchantType("1234")
                .merchantCategory("Clothing")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .direction("Debit")
                .amount(5000)
                .balance(100000)
                .available(95000)
                .summary("Purchase of a shirt")
                .type("cardTransaction")
                .build();
        CardTransaction cardTransaction = transactionFactory.create(transactionEntity);

        CardTransaction addedTransaction = (CardTransaction) transactionRepository.addTransaction(cardTransaction);
        Assertions.assertEquals(transactionEntity.getId(), addedTransaction.getId());
        Assertions.assertEquals(transactionEntity.getMerchantType(), addedTransaction.getMerchantType());
    }
}