package com.vitacard.finsvc.domain.account.model;

import com.vitacard.finsvc.domain.account.dtos.CreateDepositAccountDto;
import com.vitacard.finsvc.commons.unit.UnitCreateDepositAccountResponse;
import com.vitacard.finsvc.domain.account.infrastructure.AccountEntity;
import com.vitacard.finsvc.domain.account.infrastructure.DepositAccountEntity;

public interface IAccountFactory {
    Account create(AccountEntity accountEntity);
    DepositAccount create(DepositAccountEntity depositAccountEntity);
    DepositAccount create(CreateDepositAccountDto createDepositAccountDto, UnitCreateDepositAccountResponse unitCreateDepositAccountResponse);
}
