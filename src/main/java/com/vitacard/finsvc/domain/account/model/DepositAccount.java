package com.vitacard.finsvc.domain.account.model;

import com.vitacard.finsvc.domain.account.model.attributes.AccountStatus;
import com.vitacard.finsvc.domain.account.model.attributes.DepositProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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
            Timestamp createdAt,
            String type,
            DepositProduct depositProduct,
            String routingNumber,
            String accountNumber) {
       super(id, status, currency, balance, hold, available, customerId, createdAt, type);
       this.depositProduct = depositProduct;
       this.routingNumber = routingNumber;
       this.accountNumber = accountNumber;
    }
}
