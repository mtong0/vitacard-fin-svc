package com.vitacard.finsvc.domain.account.model;

import com.vitacard.finsvc.commons.DomainEvent;
import com.vitacard.finsvc.domain.account.dtos.CreateDepositAccountCommand;
import com.vitacard.finsvc.domain.account.dtos.UnitCreateDepositAccountResponse;
import com.vitacard.finsvc.domain.account.infrastructure.account.AccountEntity;
import com.vitacard.finsvc.domain.account.infrastructure.deposit.DepositAccountEntity;
import lombok.Value;

import java.util.Arrays;
import java.util.UUID;

public interface AccountEvent extends DomainEvent {
    @Value
    class CreateDepositAccountEvent implements AccountEvent {
        DepositAccountEntity depositAccountEntity;
        AccountEntity accountEntity;

        @Override
        public UUID getEventId() {
            return null;
        }
        private CreateDepositAccountEvent(DepositAccountEntity depositAccountEntity, AccountEntity accountEntity) {
            this.depositAccountEntity = depositAccountEntity;
            this.accountEntity = accountEntity;
        }
        public static CreateDepositAccountEvent createDepositAccount(
                CreateDepositAccountCommand createDepositAccountCommand,
                UnitCreateDepositAccountResponse unitCreateDepositAccountResponse) {

            DepositAccountEntity newDepositAccountEntity = DepositAccountEntity.builder()
                    .id(unitCreateDepositAccountResponse.getId())
                    .accountNumber(unitCreateDepositAccountResponse.getAccountNumber())
                    .routingNumber(unitCreateDepositAccountResponse.getRoutingNumber())
                    .depositProductCode(DepositProduct.getFromName(unitCreateDepositAccountResponse.getDepositProduct()).getCode())
                    .build();

            AccountEntity newAccountEntity = AccountEntity.builder()
                    .id(unitCreateDepositAccountResponse.getId())
                    .createdAt(unitCreateDepositAccountResponse.getCreatedAt())
                    .available(unitCreateDepositAccountResponse.getAvailable())
                    .currency(unitCreateDepositAccountResponse.getCurrency())
                    .balance(unitCreateDepositAccountResponse.getBalance())
                    .customerId(createDepositAccountCommand.getRelationId())
                    .hold(unitCreateDepositAccountResponse.getHold())
                    .statusCode(AccountStatus.getFromStatus(unitCreateDepositAccountResponse.getStatus()).getCode())
                    .build();
            return new CreateDepositAccountEvent(newDepositAccountEntity, newAccountEntity);
        }
    }

    @Value
    class TransactionEvent implements AccountEvent {
        String accountId;
        long updatedBalance;
        long updatedAvailable;
        @Override
        public UUID getEventId() {
            return null;
        }

        public static TransactionEvent createTransactionEvent(String accountId, long updatedAvailable, long updatedBalance) {
            return new TransactionEvent(accountId, updatedBalance, updatedAvailable);
        }
    }
}
