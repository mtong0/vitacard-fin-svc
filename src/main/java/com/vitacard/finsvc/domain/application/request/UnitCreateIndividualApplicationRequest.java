package com.vitacard.finsvc.domain.application.request;

import com.vitacard.finsvc.domain.application.aggregate.IndividualApplication;
import com.vitacard.finsvc.infrastructure.UnitDateFormat;
import generalattributes.Address;
import generalattributes.FullName;
import generalattributes.Phone;
import org.json.JSONObject;

public class UnitCreateIndividualApplicationRequest {
    private String type;

    private FullName fullName;
    private Address address;
    private String dateOfBirth;
    private String email;
    private Phone phone;
    private String ssn;

    public UnitCreateIndividualApplicationRequest(IndividualApplication individualApplication) {
        UnitDateFormat unitDateFormat = new UnitDateFormat();

        ssn = individualApplication.getSsn();
        type = individualApplication.getDocumentType().getType();
        fullName = individualApplication.getFullName();
        address = individualApplication.getAddress();
        dateOfBirth = unitDateFormat.format(individualApplication.getDateOfBirth());
        email = individualApplication.getEmail();
        phone = individualApplication.getPhone();
    }


    public String getType() {
        return type;
    }

    public UnitCreateIndividualApplicationRequest setType(String type) {
        this.type = type;
        return this;
    }

    public FullName getFullName() {
        return fullName;
    }

    public UnitCreateIndividualApplicationRequest setFullName(FullName fullName) {
        this.fullName = fullName;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public UnitCreateIndividualApplicationRequest setAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public UnitCreateIndividualApplicationRequest setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UnitCreateIndividualApplicationRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public Phone getPhone() {
        return phone;
    }

    public UnitCreateIndividualApplicationRequest setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    public JSONObject getAttributes() {
        return new JSONObject()
                .put("ssn", ssn)
                .put("fullName", new JSONObject(fullName))
                .put("address", new JSONObject(address))
                .put("dateOfBirth", dateOfBirth)
                .put("email", email)
                .put("phone", new JSONObject(phone));
    }

    public String value() {
        return new JSONObject()
                .put("data", new JSONObject()
                        .put("type", type)
                        .put("attributes", getAttributes()))
                .toString();
    }
}
