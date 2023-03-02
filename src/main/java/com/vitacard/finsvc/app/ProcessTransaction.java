package com.vitacard.finsvc.app;

import com.vitacard.finsvc.commons.unit.UnitTransactionCallbackEvent;
import com.vitacard.finsvc.domain.account.facet.IAccountFacet;
import com.vitacard.finsvc.domain.transaction.facet.ITransactionFacet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessTransaction {
    @Autowired
    private IAccountFacet accountFacet;
    @Autowired
    private ITransactionFacet transactionFacet;

    public void handle(UnitTransactionCallbackEvent unitTransactionCallbackEvent) {
        accountFacet.handle(unitTransactionCallbackEvent);
        transactionFacet.handle(unitTransactionCallbackEvent);
    }
}
