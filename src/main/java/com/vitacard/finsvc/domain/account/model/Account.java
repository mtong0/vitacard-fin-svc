package com.vitacard.finsvc.domain.account.model;

import com.vitacard.finsvc.domain.account.model.attributes.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
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
    private Timestamp createdAt;
    private String type;
}
