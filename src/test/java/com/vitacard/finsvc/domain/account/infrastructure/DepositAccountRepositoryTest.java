package com.vitacard.finsvc.domain.account.infrastructure;

import com.vitacard.finsvc.domain.account.infrastructure.deposit.DepositAccountEntity;
import com.vitacard.finsvc.domain.account.infrastructure.deposit.DepositAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepositAccountRepositoryTest {
    @Autowired
    private DepositAccountRepository depositAccountRepository;

    @Test
    void addDepositAccountEntity() {
        DepositAccountEntity depositAccountEntity = DepositAccountEntity.builder()
                .id("1234")
                .depositProductCode(0)
                .routingNumber("111000025")
                .accountNumber("123456789")
                .build();

        DepositAccountEntity addedDepositAccountEntity = depositAccountRepository.addDepositAccountEntity(depositAccountEntity);
        assertEquals(depositAccountEntity, addedDepositAccountEntity);
    }
}