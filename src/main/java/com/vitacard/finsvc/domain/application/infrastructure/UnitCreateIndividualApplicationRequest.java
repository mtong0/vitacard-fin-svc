package com.vitacard.finsvc.domain.application.infrastructure;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vitacard.finsvc.commons.unit.UnitDateFormat;
import com.vitacard.finsvc.domain.application.facet.CreateIndividualApplicationCommand;
import generalattributes.FullName;
import generalattributes.Phone;
import lombok.Value;
import unit.UnitAddress;

@Value
public class UnitCreateIndividualApplicationRequest {
    String type = "individualApplication";
    FullName fullName;
    UnitAddress address;
    String dateOfBirth;
    String email;
    Phone phone;
    String ssn;

    public UnitCreateIndividualApplicationRequest(CreateIndividualApplicationCommand createIndividualApplicationCommand) {
        ssn = createIndividualApplicationCommand.getSsn();
        fullName = new FullName()
                .setFirst(createIndividualApplicationCommand.getFirstName())
                .setLast(createIndividualApplicationCommand.getLastName());
        address = UnitAddress.builder()
                .street(createIndividualApplicationCommand.getStreet())
                .city(createIndividualApplicationCommand.getCity())
                .state(createIndividualApplicationCommand.getState())
                .country(createIndividualApplicationCommand.getCountry())
                .postalCode(createIndividualApplicationCommand.getPostalCode())
                .build();
        dateOfBirth = UnitDateFormat.formatToDay(createIndividualApplicationCommand.getDateOfBirth());
        email = createIndividualApplicationCommand.getEmail();
        phone = createIndividualApplicationCommand.getPhone();
    }

    public JsonObject getAttributes() {
        Gson gson = new Gson();
        JsonObject attributes = new JsonObject();
        attributes.addProperty("ssn", ssn);
        attributes.add("fullName", gson.toJsonTree(fullName));
        attributes.add("address", gson.toJsonTree(address));
        attributes.addProperty("dateOfBirth", dateOfBirth);
        attributes.addProperty("email", email);
        attributes.add("phone", gson.toJsonTree(phone));


        return attributes;
    }

    public String value() {
        JsonObject value = new JsonObject();
        JsonObject data = new JsonObject();
        data.addProperty("type", type);
        data.add("attributes", getAttributes());
        value.add("data", data);
        return value.toString();
    }
}
