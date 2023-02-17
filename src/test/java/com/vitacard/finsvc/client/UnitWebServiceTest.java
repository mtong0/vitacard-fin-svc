package com.vitacard.finsvc.client;

import com.vitacard.finsvc.domain.account.dtos.UnitCreateDepositAccountRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.JsonBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest
@PropertySource("unit-apis.properties")
class UnitWebServiceTest {
    @Autowired
    private UnitWebService unitWebService;
    @Autowired
    private Environment environment;
    private static ClientAndServer mockServer;
    @BeforeAll
    public static void startMockServer() {
        mockServer = startClientAndServer(1080);
    }
    @AfterAll
    public static void stopMockServer() {
        mockServer.stop();
    }
    @Test
    void createDepositAccount() {
        String mockServerRequest = """
                {
                    "data": {
                        "type": "depositAccount",
                        "attributes": {
                            "depositProduct": "checking"
                        },
                        "relationships": {
                           "customer": {
                                "data": {
                                "type": "customer",
                                "id": "45555"
                                }
                            }
                        }
                    }
                }
                """;
        String mockServerResponse = """
                { 
                  "data": {
                    "type": "depositAccount",
                    "id": "42",
                    "attributes": {
                      "createdAt": "2000-05-11T10:19:30.409Z",
                      "name": "Peter Parker",
                      "status": "Open",
                      "depositProduct": "checking",
                      "routingNumber": "812345678",
                      "accountNumber": "1000000002",
                      "currency": "USD",
                      "balance": 10000,
                      "hold": 0,
                      "available": 10000,
                      "tags": {
                        "purpose": "checking"
                      }
                    },
                    "relationships": {
                      "customer": {
                        "data": {
                          "type": "customer",
                          "id": "45555"
                        }
                      }
                    }
                  }
                }
                """;
        String uri = environment.getProperty("api.unit.account.createDepositAccount.uri");
        mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath(uri)
                                .withBody(new JsonBody(mockServerRequest)),
                        Times.exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(201)
                                .withBody(new JsonBody(mockServerResponse))
                );

        UnitCreateDepositAccountRequest unitCreateDepositAccountRequest = new UnitCreateDepositAccountRequest("checking", "customer", "45555");
        String actualResponse = unitWebService.createDepositAccount(unitCreateDepositAccountRequest);
        assertEquals(mockServerResponse.replaceAll("\\s", ""), actualResponse.replaceAll("\\s",""));
    }
}