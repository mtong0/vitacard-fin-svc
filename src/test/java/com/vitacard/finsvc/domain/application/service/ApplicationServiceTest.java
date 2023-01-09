package com.vitacard.finsvc.domain.application.service;

import com.vitacard.finsvc.domain.application.aggregate.ApplicationFactory;
import com.vitacard.finsvc.domain.application.aggregate.IndividualApplication;
import com.vitacard.finsvc.domain.application.dao.ApplicationDaoImpl;
import com.vitacard.finsvc.domain.application.request.IndividualApplicationRequest;
import com.vitacard.finsvc.domain.application.status.ApplicationStatus;
import com.vitacard.finsvc.svc.CustomerSvcClient;
import com.vitacard.finsvc.svc.UnitWebClient;
import generalattributes.Phone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.json.JSONObject;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {
    @InjectMocks
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ApplicationDaoImpl applicationDao;
    @Autowired
    private ApplicationFactory applicationFactory;
    @Mock
    private UnitWebClient unitWebClient;
    @Mock
    private CustomerSvcClient customerSvcClient;


    @Test
    void createApplication() {
        IndividualApplicationRequest individualApplicationRequest = new IndividualApplicationRequest()
                .setFirstName("John")
                .setLastName("Doe")
                .setSsn("123456789")
                .setStreet("123 Main St")
                .setStreet2("Apt 1")
                .setCity("New York")
                .setState("NY")
                .setPostalCode("12345")
                .setCountry("USA")
                .setDateOfBirth(new Timestamp(System.currentTimeMillis()))
                .setEmail("johndoe@example.com")
                .setPhone(new Phone()
                        .setCountryCode("1")
                        .setNumber("1234567890"));

        IndividualApplication approvedApplication = applicationFactory.constructFromInternalIndividualApplicationRequest(individualApplicationRequest);
        approvedApplication.setId("123");
        approvedApplication.setStatusCode(0);

        Mockito.when(unitWebClient.createIndividualApplication(any())).thenReturn(new JSONObject()
                .put("data", new JSONObject()
                        .put("id", approvedApplication.getId())
                        .put("attributes", new JSONObject()
                                .put("status", ApplicationStatus.APPROVED.getStatus())))
                .toString());
        Mockito.when(customerSvcClient.addCustomer(any())).thenReturn("");

        IndividualApplication individualApplication = applicationService.createApplication(individualApplicationRequest);
        IndividualApplication addedIndividualApplication = applicationDao.getApplicationById(individualApplication.getId());
        Assertions.assertEquals(individualApplication.getId(), addedIndividualApplication.getId());
    }
}