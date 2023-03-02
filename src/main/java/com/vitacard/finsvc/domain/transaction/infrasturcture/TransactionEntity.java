package com.vitacard.finsvc.domain.transaction.infrasturcture;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class TransactionEntity {
    private String id;
    private Timestamp createdAt;
    private String direction;
    private long amount;
    private long balance;
    private long available;
    private String summary;
    private String type;
}
