package com.vitacard.finsvc.domain.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Account {
    private String id;
    private AccountStatus status;
    private String currency;
    private long balance;
    private long hold;
    private long available;
    private String customerId;

    Account(String accountId) {
        this.id = accountId;
    }
}
