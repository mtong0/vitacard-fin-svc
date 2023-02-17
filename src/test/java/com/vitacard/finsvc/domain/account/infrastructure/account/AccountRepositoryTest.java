package com.vitacard.finsvc.domain.account.infrastructure.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void addAccountEntity() {
        AccountEntity accountEntity = AccountEntity.builder()
                .id("123456")
                .statusCode(0)
                .currency("USD")
                .balance(1000)
                .hold(200)
                .available(800)
                .customerId("987654")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();

        AccountEntity addedAccountEntity = accountRepository.addAccountEntity(accountEntity);
        assertEquals(addedAccountEntity, accountEntity);
    }
}