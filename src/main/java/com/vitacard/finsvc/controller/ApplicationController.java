package com.vitacard.finsvc.controller;

import com.vitacard.finsvc.domain.application.facet.CreateApplication;
import com.vitacard.finsvc.domain.application.facet.CreateIndividualApplicationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fin/application")
public class ApplicationController {
    @Autowired
    private CreateApplication createApplication;

    @PostMapping("/individual")
    public ResponseEntity<Void> addIndividualApplication(@RequestBody CreateIndividualApplicationCommand createIndividualApplicationCommand) {
        createApplication.createApplication(createIndividualApplicationCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
