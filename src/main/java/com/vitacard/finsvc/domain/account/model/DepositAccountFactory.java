package com.vitacard.finsvc.domain.account.model;

import com.vitacard.finsvc.domain.account.infrastructure.deposit.DepositAccountEntity;
import org.springframework.stereotype.Component;

@Component
public class DepositAccountFactory {
    public DepositAccount create(DepositAccountEntity depositAccountEntity) {
        return DepositAccount.depositAccountBuilder()
                .accountNumber(depositAccountEntity.getAccountNumber())
                .depositProduct(DepositProduct.getFromCode(depositAccountEntity.getDepositProductCode()))
                .id(depositAccountEntity.getId())
                .routingNumber(depositAccountEntity.getRoutingNumber())
                .build();
    }
}
