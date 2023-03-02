package com.vitacard.finsvc.controller;

import com.vitacard.finsvc.domain.account.dtos.CreateDepositAccountDto;
import com.vitacard.finsvc.domain.account.facet.AccountFacet;
import com.vitacard.finsvc.domain.account.facet.IAccountFacet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reward.InternalRewardDto;

@RestController
@RequestMapping("/fin/account")
public class AccountController {
    @Autowired
    private IAccountFacet accountFacet;
    @PostMapping
    public ResponseEntity<Void> addDepositAccount(@RequestBody CreateDepositAccountDto createDepositAccountDto) {
        accountFacet.createDepositAccount(createDepositAccountDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/reward")
    public ResponseEntity<Void> reward(@RequestBody InternalRewardDto internalRewardDto) {
        accountFacet.reward(internalRewardDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
