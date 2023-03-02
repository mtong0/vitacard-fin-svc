package com.vitacard.finsvc.domain.account.facet;

import com.vitacard.finsvc.commons.IDomainFacet;
import com.vitacard.finsvc.domain.account.dtos.AccountDto;
import com.vitacard.finsvc.domain.account.dtos.CreateDepositAccountDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import reward.InternalRewardDto;


public interface IAccountFacet extends IDomainFacet {
    AccountDto get(@NotBlank String id);
    void createDepositAccount(@Valid CreateDepositAccountDto createDepositAccountDto);
    void reward(@Valid InternalRewardDto internalRewardDto);
}
