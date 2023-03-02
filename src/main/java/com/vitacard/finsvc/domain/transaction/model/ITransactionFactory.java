package com.vitacard.finsvc.domain.transaction.model;

import com.vitacard.finsvc.domain.transaction.dtos.UnitCardTransaction;
import com.vitacard.finsvc.domain.transaction.infrasturcture.CardTransactionEntity;

public interface ITransactionFactory {
    CardTransaction create(CardTransactionEntity cardTransactionEntity);
    CardTransaction create(UnitCardTransaction unitCardTransaction);
}
