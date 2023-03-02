package com.vitacard.finsvc.domain.account.facet;

import com.vitacard.finsvc.commons.unit.UnitTransactionCallbackEvent;
import com.vitacard.finsvc.domain.account.infrastructure.AccountRepository;
import com.vitacard.finsvc.domain.account.model.AccountEvent.AccountTransactionEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import unit.UnitResponse;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AccountFacetTest {
    @InjectMocks
    private AccountFacet accountFacet;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CreateDepositAccount createDepositAccount;
    @Mock
    private Reward reward;

    @Test
    void handle() {
    }

    @Test
    void getTest() {
    }
    @Test
    void handleTransactionCallbackEvent() {
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
        UnitTransactionCallbackEvent unitTransactionCallbackEvent = UnitTransactionCallbackEvent.create(
                new UnitResponse(transactionCreated).getOnly()
        );

        AccountTransactionEvent accountTransactionEvent
                = AccountTransactionEvent.createTransactionEvent("1212053", 3000, 689305);
        Mockito.when(accountRepository.publish(eq(accountTransactionEvent))).thenReturn(null);
        accountFacet.handle(unitTransactionCallbackEvent);
        Mockito.verify(accountRepository, times(1)).publish(eq(accountTransactionEvent));
    }
}