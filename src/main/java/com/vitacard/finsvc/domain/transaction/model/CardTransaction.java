package com.vitacard.finsvc.domain.transaction.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class CardTransaction extends Transaction {
    @NotNull private String id;
    @NotNull private Timestamp createdAt;
    @NotBlank private String direction;
    private long amount;
    private long balance;
    private long available;
    private String summary;
    @NotBlank private String type;
    @NotBlank private String cardLast4Digits;
    private String merchantName;
    private String merchantType;
    private String merchantCategory;
}
