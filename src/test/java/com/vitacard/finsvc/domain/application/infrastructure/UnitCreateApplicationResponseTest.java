package com.vitacard.finsvc.domain.application.infrastructure;

import com.google.gson.internal.LinkedTreeMap;
import org.junit.jupiter.api.Test;
import unit.UnitResponse;
import unit.UnitResponseData;

import static org.junit.jupiter.api.Assertions.*;


class UnitCreateApplicationResponseTest {
    @Test
    public void testConstructor() {
        String unitResponseString = """
                {
                  "data": {
                    "type": "individualApplication",
                    "id": "53",
                    "attributes": {
                      "createdAt": "2020-01-14T14:05:04.718Z",
                      "fullName": {
                        "first": "Peter",
                        "last": "Parker"
                      },
                      "ssn": "721074426",
                      "address": {
                        "street": "20 Ingram St",
                        "street2": null,
                        "city": "Forest Hills",
                        "state": "NY",
                        "postalCode": "11375",
                        "country": "US"
                      },
                      "dateOfBirth": "2001-08-10",
                      "email": "peter@oscorp.com",
                      "phone": {
                        "countryCode": "1",
                        "number": "5555555555"
                      },
                      "status": "AwaitingDocuments",
                      "ip": "127.0.0.2",
                      "soleProprietorship": true,
                      "ein": "123456789",
                      "dba": "Piedpiper Inc",
                      "tags": {
                        "userId": "106a75e9-de77-4e25-9561-faffe59d7814"
                      },
                      "archived": false
                    },
                    "relationships": {
                      "org": {
                        "data": {
                          "type": "org",
                          "id": "1"
                        }
                      },
                      "customer": { 
                          "data": {
                              "type": "individualCustomer",
                              "id": "867039"
                          }
                      },
                      "documents": {
                        "data": [
                          {
                            "type": "document",
                            "id": "1"
                          },
                          {
                            "type": "document",
                            "id": "2"
                          }
                        ]
                      }
                    }
                  },
                  "included": [
                    {
                      "type": "document",
                      "id": "1",
                      "attributes": {
                        "documentType": "AddressVerification",
                        "status": "Required",
                        "name": "Peter Parker",
                        "description": "Please provide a document to verify your address. Document may be a utility bill, bank statement, lease agreement or current pay stub.",
                        "address": {
                          "street": "20 Ingram St",
                          "street2": null,
                          "city": "Forest Hills",
                          "state": "NY",
                          "postalCode": "11375",
                          "country": "US"
                        }
                      }
                    },
                    {
                      "type": "document",
                      "id": "2",
                      "attributes": {
                        "documentType": "IdDocument",
                        "status": "Required",
                        "name": "Peter Parker",
                        "description": "Please provide a copy of your unexpired government issued photo ID which would include Drivers License or State ID.",
                        "dateOfBirth": "2001-08-10"
                      }
                    }
                  ]
                }
                """;

        UnitCreateApplicationResponse unitCreateApplicationResponse = new UnitCreateApplicationResponse(new UnitResponse(unitResponseString).getOnly());
        assertNotNull(unitCreateApplicationResponse.getId());
        assertNotNull(unitCreateApplicationResponse.getStatus());
        assertNotNull(unitCreateApplicationResponse.getCustomerId());
    }
}