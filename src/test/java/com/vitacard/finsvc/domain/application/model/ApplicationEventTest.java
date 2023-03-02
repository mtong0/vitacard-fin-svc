package com.vitacard.finsvc.domain.application.model;

import com.vitacard.finsvc.domain.application.facet.CreateIndividualApplicationCommand;
import com.vitacard.finsvc.domain.application.infrastructure.UnitCreateApplicationResponse;
import com.vitacard.finsvc.domain.application.model.ApplicationEvent.CreateIndividualApplicationEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import unit.UnitResponse;
import unit.UnitResponseData;

@SpringBootTest
class ApplicationEventTest {
    @Test
    public void createIndividualApplicationNowTest() {
        CreateIndividualApplicationCommand createIndividualApplicationCommand = CreateIndividualApplicationCommand
                .builder()
                .firstName("first")
                .lastName("last")
                .email("test@email.com")
                .city("Pittsburgh")
                        .build();

        UnitCreateApplicationResponse unitCreateApplicationResponse = new UnitCreateApplicationResponse(
                new UnitResponse(
                """
                        {
                            "data": {
                                "type": "individualApplication",
                                "id": "987629",
                                "attributes": {
                                    "fullName": {
                                        "first": "Test Sincere",
                                        "last": "Price"
                                    },
                                    "ssn": "123456789",
                                    "address": {
                                        "street": "20 Ingram St",
                                        "city": "Forest Hills",
                                        "state": "CA",
                                        "postalCode": "11375",
                                        "country": "US"
                                    },
                                    "dateOfBirth": "2001-08-10",
                                    "email": "Sincere.Price@spiffymaildomain.test",
                                    "phone": {
                                        "countryCode": "1",
                                        "number": "5555555555"
                                    },
                                    "status": "Approved",
                                    "message": "Congrats - your application has been approved!",
                                    "evaluationId": "S-OBflW6CrtjKBxO9wTBZP",
                                    "soleProprietorship": false,
                                    "tags": {
                                        "test": "webhook-tag",
                                        "key": "another-tag",
                                        "number": "111"
                                    },
                                    "archived": false,
                                    "updatedAt": "2023-02-11T03:36:10.254Z"
                                },
                                "relationships": {
                                    "org": {
                                        "data": {
                                            "type": "org",
                                            "id": "2497"
                                        }
                                    },
                                    "customer": {
                                        "data": {
                                            "type": "individualCustomer",
                                            "id": "859817"
                                        }
                                    }
                                }
                            },
                            "included": []
                        }
                        """
        ).getOnly());
        CreateIndividualApplicationEvent createIndividualApplicationEvent = CreateIndividualApplicationEvent.createIndividualApplication(
            createIndividualApplicationCommand,
            unitCreateApplicationResponse
        );

        Assertions.assertEquals("987629", createIndividualApplicationEvent.getIndividualApplicationEntity().getId());
    }
}