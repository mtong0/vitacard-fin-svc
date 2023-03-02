package com.vitacard.finsvc.client;

import com.vitacard.finsvc.commons.unit.UnitCreateDepositAccountResponse;
import com.vitacard.finsvc.commons.unit.UnitCreateRewardRequest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest
@PropertySource("classpath:unit-apis.properties")
class UnitWebClientTest {
    @Autowired
    private UnitWebClient unitWebClient;
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
    void createIndividualApplication() {
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

        UnitCreateDepositAccountResponse unitCreateDepositAccountResponse = unitWebClient.createDepositAccount("checking", "customer", "45555");
        assertEquals("42", unitCreateDepositAccountResponse.getId());
        assertEquals(10000, unitCreateDepositAccountResponse.getBalance());
    }

    @Test
    void getTransactionById() {
        String mockServerResponse = """
                {
                    "data": {
                        "type": "rewardTransaction",
                        "id": "4019185",
                        "attributes": {
                            "createdAt": "2023-02-25T22:34:08.508Z",
                            "receiverCounterparty": {
                                "name": "Test: Runolfsson - Spencer",
                                "routingNumber": "812345678",
                                "accountNumber": "1001060699",
                                "accountType": "Checking"
                            },
                            "amount": 1000,
                            "direction": "Credit",
                            "balance": 199585,
                            "summary": "Sample Reward",
                            "tags": {
                                "customer_type": "vip"
                            }
                        },
                        "relationships": {
                            "account": {
                                "data": {
                                    "type": "account",
                                    "id": "1081226"
                                }
                            },
                            "customer": {
                                "data": {
                                    "type": "customer",
                                    "id": "780057"
                                }
                            },
                            "customers": {
                                "data": [
                                    {
                                        "type": "customer",
                                        "id": "780057"
                                    }
                                ]
                            },
                            "org": {
                                "data": {
                                    "type": "org",
                                    "id": "2489"
                                }
                            },
                            "reward": {
                                "data": {
                                    "type": "reward",
                                    "id": "6017"
                                }
                            },
                            "receiverAccount": {
                                "data": {
                                    "type": "account",
                                    "id": "1081225"
                                }
                            }
                        }
                    }
                }
                """;
        String uri = "/accounts/1081226/transactions/4019185";
        mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath(uri),
                        Times.exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(new JsonBody(mockServerResponse))
                );

        String actualResponse = unitWebClient.getTransactionById("1081226", "4019185");
        assertEquals(mockServerResponse.replaceAll("\\s", ""), actualResponse.replaceAll("\\s",""));
    }

    @Test
    void createReward() {
        String mockServerRequest = """
                {
                    "data": {
                      "type": "reward",
                      "attributes": {
                        "amount": 3000,
                        "description": "Reward for transaction #5678"
                      },
                      "relationships": {
                        "receivingAccount": {
                          "data": {
                            "type": "depositAccount",
                            "id": "10000"
                          }
                        },
                        "fundingAccount": {
                            "data": {
                                "type": "depositAccount",
                                "id":"10001"
                            }
                        }
                      }
                    }
                  }
                """;
        String mockServerResponse = """
                {
                   "data": {
                     "type": "reward",
                     "id": "11",
                     "attributes": {
                       "createdAt": "2022-03-31T09:25:56.388Z",
                       "amount": 1000,
                       "description": "Reward for VIP customer",
                       "status": "Sent",
                       "tags": {
                         "customerType": "vip"
                       }
                     },
                     "relationships": {
                       "receivingAccount": {
                         "data": {
                           "type": "account",
                           "id": "10006"
                         }
                       },
                       "fundingAccount": {
                         "data": {
                           "type": "account",
                           "id": "10002"
                         }
                       },
                       "rewardedTransaction": {
                         "data": {
                           "type": "transaction",
                           "id": "42"
                         }
                       },
                       "customer": {
                         "data": {
                           "type": "customer",
                           "id": "10007"
                         }
                       },
                       "transaction": {
                         "data": {
                           "type": "transaction",
                           "id": "99"
                         }
                       }
                     }
                   }
                 }
                """;
        String uri = environment.getProperty("api.unit.account.createReward.uri");
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
        UnitCreateRewardRequest unitCreateRewardRequest = UnitCreateRewardRequest.builder()
                .amount(3000)
                .receivingAccountId("10000")
                .receivingAccountType("depositAccount")
                .fundingAccountId("10001")
                .fundingAccountType("depositAccount")
                .description("Reward for transaction #5678")
                .build();
        String actualResponse = unitWebClient.createReward(unitCreateRewardRequest);
        assertEquals(mockServerResponse.replaceAll("\\s", ""), actualResponse.replaceAll("\\s",""));
    }
}