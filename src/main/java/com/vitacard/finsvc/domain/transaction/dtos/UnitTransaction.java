package com.vitacard.finsvc.domain.transaction.dtos;


public interface UnitTransaction {
    String id();
    String type();
    long amount();
}
