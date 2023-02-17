package com.vitacard.finsvc.client;

import customer.InternalCreateCustomerRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.JSONStringConverter;
import java.sql.Timestamp;

@SpringBootTest
class CustomerSvcClientTest {
    @Autowired
    private CustomerSvcClient customerSvcClient;

    @Test
    void addCustomer() {
        InternalCreateCustomerRequestDto customer = new InternalCreateCustomerRequestDto()
                .setSsn("123456789")
                .setFirstName("John")
                .setLastName("Doe")
                .setStreet("123 Main Street")
                .setStreet2("Apt 456")
                .setCity("New York")
                .setState("NY")
                .setPostalCode("10001")
                .setCountry("United States")
                .setDateOfBirth(Timestamp.valueOf("1980-01-01 00:00:00"))
                .setEmail("johndoe@example.com")
                .setCountryCode("1")
                .setPhone("212-555-1212");
        String requestString = JSONStringConverter.toJSONString(customer);
        String customerId = customerSvcClient.addCustomer(requestString);
        Assertions.assertNotEquals(customerId, null);
    }
}