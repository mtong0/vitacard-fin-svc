package com.vitacard.finsvc.commons.unit;

import com.vitacard.finsvc.commons.unit.UnitDateFormat;
import jakarta.validation.constraints.NotNull;
import unit.UnitResponseData;

import java.sql.Timestamp;

public record UnitCreateDepositAccountResponse(
        @NotNull UnitResponseData unitResponseData
) {
    public String getId() {
        return unitResponseData.id();
    }
    public Timestamp getCreatedAt() {
        return UnitDateFormat.parse(unitResponseData.attributes().get("createdAt").getAsString());
    }
    public String getStatus() {
        return unitResponseData.attributes().get("status").getAsString();
    }
    public String getDepositProduct() {
        return unitResponseData.attributes().get("depositProduct").getAsString();
    }
    public String getRoutingNumber() {
        return unitResponseData.attributes().get("routingNumber").getAsString();
    }
    public String getAccountNumber() {
        return unitResponseData.attributes().get("accountNumber").getAsString();
    }

    public String getCurrency() {
        return unitResponseData.attributes().get("balance").getAsString();
    }

    public long getBalance() {
        return unitResponseData.attributes().get("balance").getAsLong();
    }

    public long getHold() {
        return unitResponseData.attributes().get("hold").getAsLong();
    }

    public long getAvailable() {
        return unitResponseData.attributes().get("available").getAsLong();
    }
}
