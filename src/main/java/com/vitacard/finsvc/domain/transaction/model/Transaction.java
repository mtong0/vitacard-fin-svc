package com.vitacard.finsvc.domain.transaction.model;

import java.sql.Timestamp;

public class Transaction {
    private String id;
    private Timestamp createdAt;
    private String direction;
    private long amount;
    private long balance;
    private long available;
    private String summary;
    private String type;
}
