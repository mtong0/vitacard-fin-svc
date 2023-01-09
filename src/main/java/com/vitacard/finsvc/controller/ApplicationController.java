package com.vitacard.finsvc.controller;

import com.vitacard.finsvc.domain.application.request.IndividualApplicationRequest;
import com.vitacard.finsvc.domain.application.service.ApplicationService;
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
    private ApplicationService applicationService;

    @PostMapping("/individual/add")
    public ResponseEntity<Void> addIndividualApplication(@RequestBody IndividualApplicationRequest individualApplicationRequest) {
        applicationService.createApplication(individualApplicationRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
