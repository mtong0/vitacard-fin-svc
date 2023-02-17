package com.vitacard.finsvc.domain.account.facet;

import com.vitacard.finsvc.commons.events.JustForwardDomainEventPublisher;
import com.vitacard.finsvc.commons.UnitCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountFacetTest {
    @Autowired
    private JustForwardDomainEventPublisher justForwardDomainEventPublisher;
    @Autowired
    private AccountFacet accountFacet = new AccountFacet();
    @MockBean
    private ProcessTransaction processTransaction;
    @Test
    void handle() {
        String transactionCreated = """
                {
                  "data": [
                    {
                      "id": "34",
                      "type": "transaction.created",
                      "attributes": {
                        "summary": "Funding",
                        "direction": "Debit",
                        "amount": 2500,
                        "available": 3000,
                        "balance": 689305,
                        "createdAt": "2020-07-30T09:17:21.593Z",
                        "tags": {
                          "tag": "value"
                        }
                      },
                      "relationships": {
                        "transaction": {
                          "data": {
                            "type": "receivedAchTransaction",
                            "id": "10001"
                          }
                        },
                        "account": {
                          "data": {
                            "id": "1212053",
                            "type": "account"
                          }
                        },
                        "customer": {
                          "data": {
                            "id": "1",
                            "type": "customer"
                          }
                        },
                        "payment": {
                          "data": {
                            "id": "515",
                            "type": "payment"
                          }
                        }
                      }
                    }
                  ]
                }
                """;
        Mockito.doNothing().when(processTransaction).processTransaction(Mockito.any());
        accountFacet.handle(UnitCommand.getEvent(new UnitCommand(transactionCreated)));
    }
}