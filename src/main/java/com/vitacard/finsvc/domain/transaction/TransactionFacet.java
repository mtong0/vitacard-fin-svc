package com.vitacard.finsvc.domain.transaction;

import com.vitacard.finsvc.commons.unitevents.UnitTransactionCreatedEvent;
import org.springframework.stereotype.Service;

@Service
public class TransactionFacet {
    public void handle(UnitTransactionCreatedEvent unitTransactionCreatedEvent) {}

}
