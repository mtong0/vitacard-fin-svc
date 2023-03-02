package com.vitacard.finsvc.domain.account.facet;

import com.vitacard.finsvc.commons.events.DomainEvent;
import com.vitacard.finsvc.commons.unit.UnitTransactionCallbackEvent;
import com.vitacard.finsvc.domain.account.dtos.AccountDto;
import com.vitacard.finsvc.domain.account.dtos.CreateDepositAccountDto;
import com.vitacard.finsvc.domain.account.infrastructure.AccountRepository;
import com.vitacard.finsvc.domain.account.model.Account;
import com.vitacard.finsvc.domain.account.model.AccountEvent;
import com.vitacard.finsvc.domain.account.model.AccountEvent.AccountTransactionEvent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reward.InternalRewardDto;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Service
@Validated
public class AccountFacet implements IAccountFacet {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CreateDepositAccount createDepositAccount;
    @Autowired
    private Reward reward;

    @Override
    public String handle(@NotNull DomainEvent event) {
        Match(event).of(
                Case($(instanceOf(UnitTransactionCallbackEvent.class)), this::handle)
        );
        return null;

    }

    @Override
    public AccountDto get(@NotBlank String id) {
        Account account = accountRepository.getAccountById(id);
        return AccountDto.builder()
                .id(account.getId())
                .customerId(account.getCustomerId())
                .build();
    }

    private String  handle(@Valid UnitTransactionCallbackEvent event) {
        AccountEvent accountTransactionEvent = AccountTransactionEvent.createTransactionEvent(
                event.accountId(),
                event.available(),
                event.balance()
        );

        accountRepository.publish(accountTransactionEvent);
        return null;
    }

    @Override
    public void createDepositAccount(@Valid CreateDepositAccountDto createDepositAccountDto) {
        createDepositAccount.now(createDepositAccountDto);
    }

    @Override
    public void reward(@Valid InternalRewardDto internalRewardDto) {
        reward.now(internalRewardDto);
    }
}
