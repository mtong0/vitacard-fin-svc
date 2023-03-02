package com.vitacard.finsvc.domain.transaction.model.attributes;

import java.util.Arrays;

enum TransactionDirection {
    DEBIT("Debit"), CREDIT("Credit");
    private final String direction;

    TransactionDirection(String direction) {
        this.direction = direction;
    }

    public static TransactionDirection getFromDirection(String direction) {
        return Arrays.stream(TransactionDirection.values()).filter(transactionDirection -> transactionDirection.direction.equals(direction)).findFirst().orElseThrow();
    }
}