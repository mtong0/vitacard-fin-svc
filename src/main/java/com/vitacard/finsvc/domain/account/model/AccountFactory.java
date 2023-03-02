package com.vitacard.finsvc.domain.account.model;

import com.vitacard.finsvc.domain.account.dtos.CreateDepositAccountDto;
import com.vitacard.finsvc.commons.unit.UnitCreateDepositAccountResponse;
import com.vitacard.finsvc.domain.account.infrastructure.AccountEntity;
import com.vitacard.finsvc.domain.account.infrastructure.DepositAccountEntity;
import com.vitacard.finsvc.domain.account.model.attributes.AccountStatus;
import com.vitacard.finsvc.domain.account.model.attributes.DepositProduct;
import org.springframework.stereotype.Component;

@Component
public class AccountFactory implements IAccountFactory{
    public Account create(AccountEntity accountEntity) {
        return Account.builder()
                .id(accountEntity.getId())
                .available(accountEntity.getAvailable())
                .balance(accountEntity.getBalance())
                .currency(accountEntity.getCurrency())
                .customerId(accountEntity.getCustomerId())
                .hold(accountEntity.getHold())
                .type(accountEntity.getType())
                .status(AccountStatus.getFromCode(accountEntity.getStatusCode()))
                .build();
    }

    public DepositAccount create(DepositAccountEntity depositAccountEntity) {
        return DepositAccount.depositAccountBuilder()
                .id(depositAccountEntity.getId())
                .available(depositAccountEntity.getAvailable())
                .balance(depositAccountEntity.getBalance())
                .currency(depositAccountEntity.getCurrency())
                .customerId(depositAccountEntity.getCustomerId())
                .hold(depositAccountEntity.getHold())
                .status(AccountStatus.getFromCode(depositAccountEntity.getStatusCode()))
                .type(depositAccountEntity.getType())
                .createdAt(depositAccountEntity.getCreatedAt())
                .accountNumber(depositAccountEntity.getAccountNumber())
                .depositProduct(DepositProduct.getFromCode(depositAccountEntity.getDepositProductCode()))
                .routingNumber(depositAccountEntity.getRoutingNumber())
                .build();
    }

    public DepositAccount create(CreateDepositAccountDto createDepositAccountDto,
                                 UnitCreateDepositAccountResponse unitCreateDepositAccountResponse) {
        DepositAccountEntity newDepositAccountEntity = DepositAccountEntity.depositAccountEntityBuilder()
                .id(unitCreateDepositAccountResponse.getId())
                .createdAt(unitCreateDepositAccountResponse.getCreatedAt())
                .available(unitCreateDepositAccountResponse.getAvailable())
                .currency(unitCreateDepositAccountResponse.getCurrency())
                .balance(unitCreateDepositAccountResponse.getBalance())
                .customerId(createDepositAccountDto.relationId())
                .hold(unitCreateDepositAccountResponse.getHold())
                .statusCode(AccountStatus.getFromStatus(unitCreateDepositAccountResponse.getStatus()).getCode())
                .accountNumber(unitCreateDepositAccountResponse.getAccountNumber())
                .routingNumber(unitCreateDepositAccountResponse.getRoutingNumber())
                .depositProductCode(DepositProduct.getFromName(unitCreateDepositAccountResponse.getDepositProduct()).getCode())
                .build();

        return create(newDepositAccountEntity);
    }
}
