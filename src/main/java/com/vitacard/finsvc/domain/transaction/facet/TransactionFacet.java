package com.vitacard.finsvc.domain.transaction.facet;

import com.vitacard.finsvc.client.RewardSvcClient;
import com.vitacard.finsvc.commons.unit.UnitTransactionCallbackEvent;
import com.vitacard.finsvc.domain.transaction.dtos.UnitCardTransaction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import transactionDto.InternalTransactionDto;

@Service
@Validated
public class TransactionFacet implements ITransactionFacet {
    @Autowired
    private RewardSvcClient rewardSvcClient;
    @Autowired
    private SaveTransaction saveTransaction;
    @Override
    public void handle(@Valid UnitTransactionCallbackEvent unitTransactionCallbackEvent) {
        UnitCardTransaction unitCardTransaction = (UnitCardTransaction) saveTransaction.handle(unitTransactionCallbackEvent.accountId(), unitTransactionCallbackEvent.id());

        InternalTransactionDto internalTransactionDto = InternalTransactionDto.builder()
                .accountId(unitTransactionCallbackEvent.accountId())
                .amount(unitTransactionCallbackEvent.available())
                .customerId(unitTransactionCallbackEvent.customerId())
                .id(unitTransactionCallbackEvent.id())
                .merchantType(unitCardTransaction.merchant().type())
                .build();

        rewardSvcClient.postTransaction(internalTransactionDto);
    }
}
