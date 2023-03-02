package com.vitacard.finsvc.domain.account.facet;

import com.vitacard.finsvc.client.UnitWebClient;
import com.vitacard.finsvc.commons.unit.UnitCreateDepositAccountRequest;
import com.vitacard.finsvc.domain.account.dtos.CreateDepositAccountDto;
import com.vitacard.finsvc.commons.unit.UnitCreateDepositAccountResponse;
import com.vitacard.finsvc.domain.account.infrastructure.AccountRepository;
import com.vitacard.finsvc.domain.account.model.AccountFactory;
import com.vitacard.finsvc.domain.account.model.DepositAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateDepositAccount {
    @Autowired
    private UnitWebClient unitWebClient;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountFactory accountFactory;

    public void now(CreateDepositAccountDto createDepositAccountDto) {
        UnitCreateDepositAccountRequest unitCreateDepositAccountRequest = new UnitCreateDepositAccountRequest(
                createDepositAccountDto.depositProduct(),
                createDepositAccountDto.relationType(),
                createDepositAccountDto.relationId()
        );
        UnitCreateDepositAccountResponse unitCreateDepositAccountResponse = unitWebClient.createDepositAccount(unitCreateDepositAccountRequest);
        DepositAccount depositAccount = accountFactory.create(createDepositAccountDto, unitCreateDepositAccountResponse);
        accountRepository.addAccount(depositAccount);
    }
}
