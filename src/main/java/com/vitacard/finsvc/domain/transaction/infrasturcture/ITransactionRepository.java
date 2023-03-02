package com.vitacard.finsvc.domain.transaction.infrasturcture;

import com.vitacard.finsvc.domain.transaction.model.CardTransaction;
import com.vitacard.finsvc.domain.transaction.model.Transaction;

public interface ITransactionRepository {
    Transaction addTransaction(CardTransaction cardTransaction);
    Transaction getTransactionById(String id, String type);
}