package com.vitacard.finsvc.domain.transaction.model;

import com.vitacard.finsvc.domain.transaction.TransactionData;
import com.vitacard.finsvc.domain.transaction.dtos.UnitCardTransaction;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import unit.UnitResponse;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionFactoryTest {

    @Autowired
    private TransactionFactory transactionFactory;
    @Autowired
    private Validator validator;

    @Test
    void createUnitCardTransaction() {
        UnitCardTransaction unitCardTransaction = new UnitCardTransaction(new UnitResponse(TransactionData.CARD_TRANSACTION_STRING).getOnly());
        CardTransaction createdCardTransaction = transactionFactory.create(unitCardTransaction);
        assertEquals( TransactionData.CARD_TRANSACTION_ID, createdCardTransaction.getId());
        Set<ConstraintViolation<CardTransaction>> constraintViolationSet = validator.validate(createdCardTransaction);
        assertEquals(0, constraintViolationSet.size());
    }
}