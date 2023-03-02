package com.vitacard.finsvc.commons.unit;
import unit.UnitResponseData;

public record UnitTransactionCreatedDto(UnitResponseData unitResponseData)  {
    public String getId() {
        return unitResponseData.id();
    }
    public String getDirection() {
        return unitResponseData.attributes().get("direction").getAsString();
    }

    public long getAvailable() {
        return unitResponseData.attributes().get("available").getAsLong();
    }

    public long getBalance() {
        return unitResponseData.attributes().get("balance").getAsLong();
    }
    public long getAmount() {return unitResponseData.attributes().get("amount").getAsLong();}

    public String getAccountId() {
        return unitResponseData.relationships().get("account")
                .get(0)
                .id();
    }
    public String getCustomerId() {
        return unitResponseData.relationships().get("customer")
                .get(0)
                .id();
    }
}
