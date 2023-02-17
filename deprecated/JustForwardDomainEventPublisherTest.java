//package com.vitacard.finsvc.commons.events;
//
//import com.vitacard.finsvc.domain.application.infrastructure.ApplicationEventListener;
//import com.vitacard.finsvc.domain.application.model.ApplicationEvent;
//import com.vitacard.finsvc.domain.application.model.ApplicationEvent.CreateCustomerEvent;
//import com.vitacard.finsvc.domain.application.model.DocumentType;
//import com.vitacard.finsvc.domain.application.model.IndividualApplication;
//import generalattributes.Address;
//import generalattributes.FullName;
//import generalattributes.Phone;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.sql.Timestamp;
//import java.util.concurrent.TimeUnit;
//
//import static org.mockito.ArgumentMatchers.any;
//
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//class JustForwardDomainEventPublisherTest {
//    @Autowired
//    private JustForwardDomainEventPublisher justForwardDomainEventPublisher;
//    @MockBean
//    private ApplicationEventListener applicationEventListener;
//
//    @Test
//    void publish() {
//        IndividualApplication individualApplication = IndividualApplication.builder()
//                .id("123456")
//                .documentType(DocumentType.INDIVIDUAL_APPLICATION)
//                .ssn("123456789")
//                .fullName(new FullName().setLast("Doe").setLast("John"))
//                .address(new Address().setStreet("street1").setStreet2("street2").setCity("Pittsburgh").setState("PA").setCountry("US")
//                        .setPostalCode("12345"))
//                .dateOfBirth(new Timestamp(System.currentTimeMillis()))
//                .email("johndoe@example.com")
//                .phone(new Phone().setNumber("5555555555").setCountryCode("1"))
//                .statusCode(0)
//                .message("Application received successfully")
//                .archived(false)
//                .customerId("12345")
//                .build();
//
//        CreateCustomerEvent createCustomerEvent = CreateCustomerEvent.createCustomer(individualApplication);
//        Mockito.verify(justForwardDomainEventPublisher).publish(createCustomerEvent);
//    }
//}