package com.vitacard.finsvc.domain.transaction;

import java.sql.Timestamp;

public class CreateCardTransaction {
    private String id;
    private Timestamp createdAt;
    private String direction;
    private long amount;
    private long balance;
    private String summary;
    private String cardLast4Digits;
    private String merchantName;
    private String merchantType;
    private String merchantCategory;
}
