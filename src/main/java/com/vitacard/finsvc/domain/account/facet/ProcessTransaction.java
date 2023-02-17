package com.vitacard.finsvc.domain.account.facet;

import com.vitacard.finsvc.domain.account.dtos.UnitTransactionCreatedCommand;
import com.vitacard.finsvc.domain.account.infrastructure.account.AccountRepository;
import com.vitacard.finsvc.domain.account.model.AccountEvent.TransactionEvent;
import com.vitacard.finsvc.domain.account.model.AccountFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessTransaction {
    @Autowired
    private AccountRepository accountRepository;

    public void processTransaction(UnitTransactionCreatedCommand unitTransactionCreatedCommand) {
        TransactionEvent transactionEvent = TransactionEvent.createTransactionEvent(
                unitTransactionCreatedCommand.getAccountId(),
                unitTransactionCreatedCommand.getAvailable(),
                unitTransactionCreatedCommand.getBalance()
        );

        accountRepository.publish(transactionEvent);
    }
}
