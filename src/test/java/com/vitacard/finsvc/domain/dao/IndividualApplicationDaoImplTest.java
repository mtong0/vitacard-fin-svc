package com.vitacard.finsvc.domain.dao;

import com.vitacard.finsvc.domain.application.aggregate.IndividualApplication;
import com.vitacard.finsvc.domain.application.dao.ApplicationDaoImpl;
import com.vitacard.finsvc.domain.application.status.ApplicationStatus;
import com.vitacard.finsvc.infrastructure.DocumentType;
import generalattributes.Address;
import generalattributes.FullName;
import generalattributes.Phone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class IndividualApplicationDaoImplTest {
    @Autowired
    ApplicationDaoImpl applicationDao;

    @Test
    void addApplication() {
        // Create a new IndividualApplication object with sample data
        IndividualApplication application = new IndividualApplication()
                .setId("12345")
                .setDocumentType(DocumentType.INDIVIDUAL_APPLICATION)
                .setFullName(new FullName()
                        .setFirst("John")
                        .setLast("Doe"))
                .setSsn("123456789")
                .setAddress(new Address()
                        .setStreet("123 Main St")
                        .setStreet2("Apt 1")
                        .setCity("New York")
                        .setState("NY")
                        .setPostalCode("12345")
                        .setCountry("USA"))
                .setDateOfBirth(new Timestamp(System.currentTimeMillis()))
                .setEmail("johndoe@example.com")
                .setPhone(new Phone()
                        .setCountryCode("+1")
                        .setNumber("123-456-7890"))
                .setStatusCode(ApplicationStatus.APPROVED.getCode())
                .setMessage("Application pending review")
                .setArchived(false);

        IndividualApplication addedApplication = applicationDao.addApplication(application);

        assertEquals("12345", addedApplication.getId());
        assertEquals("John", addedApplication.getFullName().getFirst());
        assertEquals("Doe", addedApplication.getFullName().getLast());
        assertEquals("123 Main St", addedApplication.getAddress().getStreet());
        assertEquals("Apt 1", addedApplication.getAddress().getStreet2());
        assertEquals("New York", addedApplication.getAddress().getCity());
        assertEquals("NY", addedApplication.getAddress().getState());
        assertEquals("12345", addedApplication.getAddress().getPostalCode());
        assertEquals("USA", addedApplication.getAddress().getCountry());
    }

    @Test
    void getApplicationById() {
    }
}