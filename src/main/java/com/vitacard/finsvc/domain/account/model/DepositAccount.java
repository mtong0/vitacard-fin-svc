package com.vitacard.finsvc.domain.account.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
public class DepositAccount extends Account {
    private DepositProduct depositProduct;
    private String routingNumber;
    private String accountNumber;

    @Builder(builderMethodName = "depositAccountBuilder")
    public DepositAccount(
            String id,
            AccountStatus status,
            String currency,
            long balance,
            long hold,
            long available,
            String customerId,
            DepositProduct depositProduct,
            String routingNumber,
            String accountNumber
    ) {
        super(id, status, currency, balance, hold, available, customerId);
        this.depositProduct = depositProduct;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
    }
}
