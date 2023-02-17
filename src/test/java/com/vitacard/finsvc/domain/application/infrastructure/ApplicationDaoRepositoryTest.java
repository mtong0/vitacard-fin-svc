package com.vitacard.finsvc.domain.application.infrastructure;

import com.vitacard.finsvc.domain.application.model.ApplicationStatus;
import com.vitacard.finsvc.domain.application.model.DocumentType;
import generalattributes.Address;
import generalattributes.FullName;
import generalattributes.Phone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationDaoRepositoryTest {
    @Autowired
    ApplicationDaoRepository applicationDao;
    @Test
    void addApplication() {
        IndividualApplicationEntity application = IndividualApplicationEntity.builder()
                .id("12345")
                .documentType(DocumentType.INDIVIDUAL_APPLICATION)
                .fullName(new FullName()
                        .setFirst("John")
                        .setLast("Doe"))
                .ssn("123456789")
                .address(new Address()
                        .setStreet("123 Main St")
                        .setStreet2("Apt 1")
                        .setCity("New York")
                        .setState("NY")
                        .setPostalCode("12345")
                        .setCountry("US"))
                .dateOfBirth(new Timestamp(System.currentTimeMillis()))
                .email("johndoe@example.com")
                .phone(new Phone()
                        .setCountryCode("+1")
                        .setNumber("123-456-7890"))
                .statusCode(ApplicationStatus.APPROVED.getCode())
                .message("Application pending review")
                .archived(false)
                .customerId("123456")
                .build();

        IndividualApplicationEntity addedApplication = applicationDao.addApplication(application);

        assertEquals("12345", addedApplication.getId());
        assertEquals("John", addedApplication.getFullName().getFirst());
        assertEquals("Doe", addedApplication.getFullName().getLast());
        assertEquals("123 Main St", addedApplication.getAddress().getStreet());
        assertEquals("Apt 1", addedApplication.getAddress().getStreet2());
        assertEquals("New York", addedApplication.getAddress().getCity());
        assertEquals("NY", addedApplication.getAddress().getState());
        assertEquals("12345", addedApplication.getAddress().getPostalCode());
        assertEquals("US", addedApplication.getAddress().getCountry());
    }
}