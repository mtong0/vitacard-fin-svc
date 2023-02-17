package com.vitacard.finsvc.domain.application.model;

import generalattributes.Address;
import generalattributes.FullName;
import generalattributes.Phone;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class IndividualApplication {
    private static final String applicationType = "IndividualApplication";
    private String id;
    private DocumentType documentType;
    private String ssn;
    private FullName fullName;
    private Address address;
    private Timestamp dateOfBirth;
    private String email;
    private Phone phone;
    private int statusCode;
    private String message;
    private boolean archived;
    private String customerId;
}
