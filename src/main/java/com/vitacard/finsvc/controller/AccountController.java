package com.vitacard.finsvc.controller;

import com.vitacard.finsvc.domain.account.dtos.CreateDepositAccountCommand;
import com.vitacard.finsvc.domain.account.facet.CreateDepositAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fin/accounts")
public class AccountController {
    @Autowired
    private CreateDepositAccount createDepositAccount;
    @PostMapping
    public ResponseEntity<Void> addDepositAccount(@RequestBody CreateDepositAccountCommand createDepositAccountCommand) {
        createDepositAccount.createAccount(createDepositAccountCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
