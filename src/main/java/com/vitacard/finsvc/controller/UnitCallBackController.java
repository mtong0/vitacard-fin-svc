package com.vitacard.finsvc.controller;

import com.vitacard.finsvc.commons.events.DomainEvent;
import com.vitacard.finsvc.commons.events.JustForwardDomainEventPublisher;
import com.vitacard.finsvc.commons.unit.UnitTransactionCallbackEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unit.UnitResponse;
import unit.UnitResponseData;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@RestController
@RequestMapping("/fin/callback")
public class UnitCallBackController {
    @Autowired
    private JustForwardDomainEventPublisher justForwardDomainEventPublisher;
    @PostMapping("/event")
    public ResponseEntity<Void> event(@RequestBody String body)  {
        UnitResponse unitResponse = new UnitResponse(body);
        DomainEvent domainEvent = getEvent(unitResponse.getOnly());
        justForwardDomainEventPublisher.publish(domainEvent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private DomainEvent getEvent(UnitResponseData unitResponseData) {
        String type = unitResponseData.type();
        return Match(type).of(
                Case($(UnitTransactionCallbackEvent.TYPE), ()-> UnitTransactionCallbackEvent.create(unitResponseData))
        );
    }
}
