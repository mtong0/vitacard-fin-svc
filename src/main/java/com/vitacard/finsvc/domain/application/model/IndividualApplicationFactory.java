package com.vitacard.finsvc.domain.application.model;

import com.vitacard.finsvc.domain.application.infrastructure.IndividualApplicationEntity;
import org.springframework.stereotype.Component;

@Component
public class IndividualApplicationFactory {
    public IndividualApplication create(IndividualApplicationEntity entity) {
        IndividualApplication individualApplication = IndividualApplication.builder()
                .id(entity.getId())
                .documentType(DocumentType.INDIVIDUAL_APPLICATION)
                .ssn(entity.getSsn())
                .fullName(entity.getFullName())
                .address(entity.getAddress())
                .dateOfBirth(entity.getDateOfBirth())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .statusCode(entity.getStatusCode())
                .message(entity.getMessage())
                .archived(entity.isArchived())
                .customerId(entity.getCustomerId())
                .build();
        return individualApplication;
    }
}
