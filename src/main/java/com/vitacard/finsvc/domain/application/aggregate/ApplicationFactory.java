package com.vitacard.finsvc.domain.application.aggregate;

import com.vitacard.finsvc.domain.application.request.IndividualApplicationRequest;
import com.vitacard.finsvc.domain.application.status.ApplicationStatus;
import com.vitacard.finsvc.infrastructure.DocumentType;
import generalattributes.Address;
import generalattributes.FullName;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ApplicationFactory {
    public IndividualApplication constructFromInternalIndividualApplicationRequest(IndividualApplicationRequest individualApplicationRequest) {
        FullName fullName = new FullName()
                .setFirst(individualApplicationRequest.getFirstName())
                .setLast(individualApplicationRequest.getLastName());
        Address address = new Address()
                .setStreet(individualApplicationRequest.getStreet())
                .setStreet2(individualApplicationRequest.getStreet2())
                .setCity(individualApplicationRequest.getCity())
                .setState(individualApplicationRequest.getState())
                .setPostalCode(individualApplicationRequest.getPostalCode())
                .setCountry(individualApplicationRequest.getCountry());

        return new IndividualApplication()
                .setDocumentType(DocumentType.INDIVIDUAL_APPLICATION)
                .setFullName(fullName)
                .setAddress(address)
                .setDateOfBirth(individualApplicationRequest.getDateOfBirth())
                .setEmail(individualApplicationRequest.getEmail())
                .setPhone(individualApplicationRequest.getPhone())
                .setSsn(individualApplicationRequest.getSsn());
    }

    public IndividualApplication constructFromUnitResponse(IndividualApplication individualApplication, String responseString) {
        JSONObject jsonObject = new JSONObject(responseString);
        String id = jsonObject.getJSONObject("data").getString("id");
        String status = jsonObject.getJSONObject("data").getJSONObject("attributes").getString("status");
        ApplicationStatus applicationStatus = ApplicationStatus.getByStatus(status);
        individualApplication.setId(id);
        individualApplication.setStatusCode(applicationStatus.getCode());
        return individualApplication;
    }
}
