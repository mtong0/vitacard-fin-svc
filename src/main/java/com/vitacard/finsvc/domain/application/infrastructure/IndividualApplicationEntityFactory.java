package com.vitacard.finsvc.domain.application.infrastructure;

import com.vitacard.finsvc.domain.application.facet.CreateIndividualApplicationCommand;
import com.vitacard.finsvc.domain.application.model.DocumentType;
import generalattributes.Address;
import generalattributes.FullName;
import org.springframework.stereotype.Component;

@Component
public class IndividualApplicationEntityFactory {
    public IndividualApplicationEntity create(
            String id,
            int statusCode,
            String customerId,
            CreateIndividualApplicationCommand createIndividualApplicationCommand
    ) {
        FullName fullName = new FullName()
                .setFirst(createIndividualApplicationCommand.getFirstName())
                .setLast(createIndividualApplicationCommand.getLastName());
        Address address = new Address()
                .setStreet(createIndividualApplicationCommand.getStreet())
                .setStreet2(createIndividualApplicationCommand.getStreet2())
                .setCity(createIndividualApplicationCommand.getCity())
                .setState(createIndividualApplicationCommand.getState())
                .setPostalCode(createIndividualApplicationCommand.getPostalCode())
                .setCountry(createIndividualApplicationCommand.getCountry());

        return IndividualApplicationEntity.builder()
                .documentType(DocumentType.INDIVIDUAL_APPLICATION)
                .fullName(fullName)
                .address(address)
                .dateOfBirth(createIndividualApplicationCommand.getDateOfBirth())
                .email(createIndividualApplicationCommand.getEmail())
                .phone(createIndividualApplicationCommand.getPhone())
                .ssn(createIndividualApplicationCommand.getSsn())
                .id(id)
                .statusCode(statusCode)
                .customerId(customerId)
                .build();
    }
}
