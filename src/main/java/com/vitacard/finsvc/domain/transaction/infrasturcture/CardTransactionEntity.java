package com.vitacard.finsvc.domain.transaction.infrasturcture;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@Builder
public class CardTransactionEntity extends TransactionEntity {
    private String id;
    private Timestamp createdAt;
    private String direction;
    private long amount;
    private long balance;
    private long available;
    private String summary;
    private String type;
    private String cardLast4Digits;
    private String merchantName;
    private String merchantType;
    private String merchantCategory;
}
