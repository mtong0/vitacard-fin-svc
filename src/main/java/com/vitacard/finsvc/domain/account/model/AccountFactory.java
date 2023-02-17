package com.vitacard.finsvc.domain.account.model;

import com.vitacard.finsvc.domain.account.infrastructure.account.AccountEntity;
import org.springframework.stereotype.Repository;

@Repository
public class AccountFactory {
    public Account create(AccountEntity accountEntity) {
        return Account.builder()
                .id(accountEntity.getId())
                .available(accountEntity.getAvailable())
                .balance(accountEntity.getBalance())
                .currency(accountEntity.getCurrency())
                .customerId(accountEntity.getCustomerId())
                .hold(accountEntity.getHold())
                .status(AccountStatus.getFromCode(accountEntity.getStatusCode()))
                .build();
    }
}
