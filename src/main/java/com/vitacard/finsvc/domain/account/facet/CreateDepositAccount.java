package com.vitacard.finsvc.domain.account.facet;

import com.vitacard.finsvc.client.UnitWebService;
import com.vitacard.finsvc.domain.account.dtos.CreateDepositAccountCommand;
import com.vitacard.finsvc.domain.account.dtos.UnitCreateDepositAccountRequest;
import com.vitacard.finsvc.domain.account.dtos.UnitCreateDepositAccountResponse;
import com.vitacard.finsvc.domain.account.infrastructure.account.AccountRepository;
import com.vitacard.finsvc.domain.account.infrastructure.deposit.DepositAccountRepository;
import com.vitacard.finsvc.domain.account.model.AccountEvent.CreateDepositAccountEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vitacard.finsvc.domain.account.model.AccountEvent.CreateDepositAccountEvent.createDepositAccount;

@Service
public class CreateDepositAccount {
    @Autowired
    private UnitWebService unitWebService;
    @Autowired
    private DepositAccountRepository depositAccountRepository;
    @Autowired
    private AccountRepository accountRepository;

    public void createAccount(CreateDepositAccountCommand createDepositAccountCommand) {
        UnitCreateDepositAccountRequest unitCreateDepositAccountRequest = new UnitCreateDepositAccountRequest(
                createDepositAccountCommand.getDepositProduct(), createDepositAccountCommand.getRelationType(), createDepositAccountCommand.getRelationId()
        );
        UnitCreateDepositAccountResponse unitCreateDepositAccountResponse = new UnitCreateDepositAccountResponse(
                unitWebService.createDepositAccount(unitCreateDepositAccountRequest)
        );

        CreateDepositAccountEvent createDepositAccountEvent = createDepositAccount(createDepositAccountCommand, unitCreateDepositAccountResponse);
        accountRepository.publish(createDepositAccountEvent);
        depositAccountRepository.publish(createDepositAccountEvent);
    }

}
