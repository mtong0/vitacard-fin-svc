package com.vitacard.finsvc.domain.application.facet;

import generalattributes.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder(toBuilder = true)
@Getter()
@AllArgsConstructor
public class CreateIndividualApplicationCommand {
    private String firstName;
    private String lastName;
    private String ssn;
    private String street;
    private String street2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Timestamp dateOfBirth;
    private String email;
    private Phone phone;
}
