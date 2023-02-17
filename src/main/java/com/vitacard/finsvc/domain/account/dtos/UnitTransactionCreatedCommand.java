package com.vitacard.finsvc.domain.account.dtos;

import com.google.gson.JsonElement;
import com.vitacard.finsvc.domain.application.infrastructure.UnitCreateApplicationResponse;
import unit.Relationship;
import unit.RelationshipData;
import unit.UnitResponse;

import java.util.Map;

public class UnitTransactionCreatedCommand extends UnitResponse {
    public UnitTransactionCreatedCommand(String responseBody) {
        super(responseBody);
    }

    public UnitTransactionCreatedCommand(JsonElement element) {
        super(element);
    }

    public String getDirection() {
        return getAttributes().get("direction").getAsString();
    }

    public long getAvailable() {
        return getAttributes().get("available").getAsLong();
    }

    public long getBalance() {
        return getAttributes().get("balance").getAsLong();
    }

    public String getAccountId() {
        return getRelationships().get("account")
                .get(0)
                .getId();
    }
}
