package com.vitacard.finsvc.domain.transaction.facet;

import com.vitacard.finsvc.client.UnitWebClient;
import com.vitacard.finsvc.domain.transaction.TransactionData;
import com.vitacard.finsvc.domain.transaction.dtos.UnitCardTransaction;
import com.vitacard.finsvc.domain.transaction.infrasturcture.TransactionRepository;
import com.vitacard.finsvc.domain.transaction.model.CardTransaction;
import com.vitacard.finsvc.domain.transaction.model.TransactionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import unit.UnitResponse;
import unit.UnitResponseData;

@ExtendWith(MockitoExtension.class)
class SaveTransactionTest {
    @InjectMocks
    private SaveTransaction saveTransaction;

    @Mock
    private UnitWebClient unitWebClient;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionFactory transactionFactory;
    @Test
    void handle() {

    }

    void handle(UnitResponseData unitResponseData) {

    }

    @Test
    void handleCardTransactionTest() {
        String CARD_TRANSACTION = TransactionData.CARD_TRANSACTION_STRING;
        CardTransaction cardTransaction = TransactionData.CARD_TRANSACTION;
        UnitCardTransaction unitCardTransaction  = new UnitCardTransaction(new UnitResponse(CARD_TRANSACTION).getOnly());
        Mockito.when(transactionFactory.create(Mockito.eq(unitCardTransaction))).thenReturn(cardTransaction);
        saveTransaction.handle(new UnitResponse(CARD_TRANSACTION).getOnly());
        Mockito.verify(transactionFactory, Mockito.times(1)).create(Mockito.any(UnitCardTransaction.class));
        Mockito.verify(transactionRepository, Mockito.times(1)).addTransaction(Mockito.any(CardTransaction.class));
    }
}