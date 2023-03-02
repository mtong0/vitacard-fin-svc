package com.vitacard.finsvc.domain.transaction;

import com.vitacard.finsvc.domain.transaction.model.CardTransaction;

public class TransactionData {
    public static final String CARD_TRANSACTION_ID = "410";
    public static final CardTransaction CARD_TRANSACTION = CardTransaction.builder()
            .id("410")
            .type("cardTransaction")
            .build();
    public static final String CARD_TRANSACTION_STRING = """
                {
                    "data": {
                      "type": "cardTransaction",
                      "id": "410",
                      "attributes": {
                        "createdAt": "2020-09-20T12:41:43.360Z",
                        "direction": "Debit",
                        "amount": 10,
                        "balance": 89480,
                        "summary": "Card transaction details",
                        "cardLast4Digits": "2282",
                        "merchant": {
                          "name": "Europcar Mobility Group",
                          "type": 3381,
                          "category": "EUROP CAR",
                          "location": "Cupertino, CA",
                          "id": "029859000085093"
                        },
                        "recurring": false,
                        "interchange": "2.43",
                        "paymentMethod": "Contactless",
                        "digitalWallet": "Apple",
                        "cardVerificationData": {
                          "verificationMethod": "CVV2"
                        },
                        "cardNetwork": "Visa"
                      },
                      "relationships": {
                        "account": {
                          "data": {
                            "type": "account",
                            "id": "10001"
                          }
                        },
                        "customer": {
                          "data": {
                            "type": "customer",
                            "id": "1001"
                          }
                        },
                        "card": {
                          "data": {
                            "type": "card",
                            "id": "11"
                          }
                        }
                      }
                    }
                }
                """;
}
