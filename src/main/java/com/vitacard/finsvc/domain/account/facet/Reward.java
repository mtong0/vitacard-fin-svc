package com.vitacard.finsvc.domain.account.facet;

import com.vitacard.finsvc.client.UnitWebClient;
import com.vitacard.finsvc.commons.unit.UnitCreateRewardRequest;
import com.vitacard.finsvc.domain.account.infrastructure.AccountRepository;
import com.vitacard.finsvc.domain.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reward.InternalRewardDto;

@Service
public class Reward {
    private final String fundingAccountId = "1252842";
    private final String fundingAccountType = "depositAccount";
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UnitWebClient unitWebClient;

    public void now(InternalRewardDto internalRewardDto) {
        String accountId = internalRewardDto.accountId();
        Account account = accountRepository.getAccountById(accountId);
        UnitCreateRewardRequest unitCreateRewardRequest = UnitCreateRewardRequest.builder()
                .amount(internalRewardDto.amount())
                .receivingAccountId(internalRewardDto.accountId())
                .receivingAccountType(account.getType())
                .fundingAccountType(fundingAccountType)
                .fundingAccountId(fundingAccountId)
                .description("")
                .build();

        unitWebClient.createReward(unitCreateRewardRequest);
    }
}
