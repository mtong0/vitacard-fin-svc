package com.vitacard.finsvc.domain.account.infrastructure;

import com.vitacard.finsvc.domain.account.model.Account;
import com.vitacard.finsvc.domain.account.model.AccountEvent.AccountTransactionEvent;
import com.vitacard.finsvc.domain.account.model.AccountFactory;
import com.vitacard.finsvc.domain.account.model.DepositAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountFactory accountFactory;

    @Test
    @Transactional
    void addDepositAccountEntity() {
        DepositAccountEntity depositAccountEntity = DepositAccountEntity.depositAccountEntityBuilder()
                .id("123456")
                .statusCode(0)
                .currency("USD")
                .balance(1000)
                .hold(200)
                .available(800)
                .customerId("987654")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .depositProductCode(0)
                .routingNumber("111000025")
                .accountNumber("123456789")
                .build();

        DepositAccount depositAccount = (DepositAccount) accountRepository.addAccount(accountFactory.create(depositAccountEntity));
        assertEquals(depositAccount.getAccountNumber(), depositAccountEntity.getAccountNumber());
    }

    @Test
    @Transactional
    void testPublish() {
        accountTransactionEvent();
    }

    void accountTransactionEvent() {
        DepositAccountEntity depositAccountEntity = DepositAccountEntity.depositAccountEntityBuilder()
                .id("123456")
                .statusCode(0)
                .currency("USD")
                .balance(1000)
                .hold(200)
                .available(800)
                .customerId("987654")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .depositProductCode(0)
                .routingNumber("111000025")
                .accountNumber("123456789")
                .build();

        DepositAccount depositAccount = (DepositAccount) accountRepository.addAccount(accountFactory.create(depositAccountEntity));
        AccountTransactionEvent accountTransactionEvent = AccountTransactionEvent.createTransactionEvent("123456", 10000, 10000);
        accountRepository.publish(accountTransactionEvent);
        Account account = accountRepository.getAccountById(depositAccount.getId());
        assertEquals(account.getAvailable(), 10000);
        assertEquals(account.getBalance(), 10000);
    }
}