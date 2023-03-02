package com.vitacard.finsvc.domain.transaction.facet;

import com.vitacard.finsvc.commons.unit.UnitTransactionCallbackEvent;
import jakarta.validation.Valid;

public interface ITransactionFacet {
    void handle(@Valid UnitTransactionCallbackEvent unitTransactionCallbackEvent);
}
