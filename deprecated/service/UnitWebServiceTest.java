package com.vitacard.finsvc.service;

import com.vitacard.finsvc.client.UnitWebService;
import com.vitacard.finsvc.domain.account.infrastructure.UnitCreateDepositAccountRequest;
import com.vitacard.finsvc.domain.account.infrastructure.DepositAccountEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UnitWebServiceTest {
    @Autowired
    private UnitWebService unitWebService;

    @Test
    @Disabled
    void createDepositAccount() {
        DepositAccountEntity depositAccountEntity = unitWebService.createDepositAccount(
                new UnitCreateDepositAccountRequest("checking", "customer", "123")
        );
    }
}