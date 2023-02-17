package com.vitacard.finsvc.domain.account.facet;

import com.vitacard.finsvc.commons.DomainEvent;
import com.vitacard.finsvc.commons.unitevents.UnitTransactionCreatedEvent;
import com.vitacard.finsvc.domain.account.infrastructure.account.AccountRepository;
import com.vitacard.finsvc.domain.account.model.AccountEvent;
import com.vitacard.finsvc.domain.account.model.AccountEvent.TransactionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Service
public class AccountFacet {
    @Autowired
    private ProcessTransaction processTransaction;
    @Autowired
    private AccountRepository accountRepository;
    public String handle(DomainEvent event) {
        Match(event).of(
                Case($(instanceOf(UnitTransactionCreatedEvent.class)), this::handle)
        );
        return null;

    }

    public String handle(UnitTransactionCreatedEvent event) {
        AccountEvent transactionEvent = TransactionEvent.createTransactionEvent(
                event.getAccountId(),
                event.getAvailable(),
                event.getBalance()
        );

        accountRepository.publish(transactionEvent);
        return null;
    }
}
