package com.vitacard.finsvc.controller;

import com.vitacard.finsvc.commons.DomainEvent;
import com.vitacard.finsvc.commons.events.JustForwardDomainEventPublisher;
import com.vitacard.finsvc.commons.UnitCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fin/callback")
public class UnitCallBackController {
    @Autowired
    private JustForwardDomainEventPublisher justForwardDomainEventPublisher;
    @PostMapping("/event")
    public ResponseEntity<Void> event(@RequestBody String body)  {
        UnitCommand unitCommand = new UnitCommand(body);
        DomainEvent domainEvent = UnitCommand.getEvent(unitCommand);
        justForwardDomainEventPublisher.publish(domainEvent);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
