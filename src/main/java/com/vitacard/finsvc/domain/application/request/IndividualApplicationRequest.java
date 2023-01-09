package com.vitacard.finsvc.domain.application.request;


import generalattributes.Phone;

import java.sql.Timestamp;

public class IndividualApplicationRequest {
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

    public String getFirstName() {
        return firstName;
    }

    public IndividualApplicationRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public IndividualApplicationRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getSsn() {
        return ssn;
    }

    public IndividualApplicationRequest setSsn(String ssn) {
        this.ssn = ssn;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public IndividualApplicationRequest setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getStreet2() {
        return street2;
    }

    public IndividualApplicationRequest setStreet2(String street2) {
        this.street2 = street2;
        return this;
    }

    public String getCity() {
        return city;
    }

    public IndividualApplicationRequest setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public IndividualApplicationRequest setState(String state) {
        this.state = state;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public IndividualApplicationRequest setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public IndividualApplicationRequest setCountry(String country) {
        this.country = country;
        return this;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public IndividualApplicationRequest setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public IndividualApplicationRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public Phone getPhone() {
        return this.phone;
    }

    public IndividualApplicationRequest setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }
}
