package com.vitacard.finsvc.domain.transaction.facet;

import com.vitacard.finsvc.domain.transaction.dtos.UnitCardTransaction;
import com.vitacard.finsvc.domain.transaction.dtos.UnitTransaction;
import unit.UnitResponseData;

public interface ISaveTransaction {
    UnitTransaction handle(String accountId, String transactionId);
    UnitCardTransaction handle(UnitResponseData unitResponseData);
}
