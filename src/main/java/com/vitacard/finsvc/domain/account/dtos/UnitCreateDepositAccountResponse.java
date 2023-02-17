package com.vitacard.finsvc.domain.account.dtos;

import com.vitacard.finsvc.commons.UnitDateFormat;
import unit.UnitResponse;

import java.sql.Timestamp;

public class UnitCreateDepositAccountResponse extends UnitResponse {
    public UnitCreateDepositAccountResponse(String responseBody) {
        super(responseBody);
    }
    public String getId() {
        return getData().get("id").getAsString();
    }
    public Timestamp getCreatedAt() {
        return UnitDateFormat.parse(getAttributes().get("createdAt").getAsString());
    }
    public String getStatus() {
        return getAttributes().get("status").getAsString();
    }
    public String getDepositProduct() {
        return getAttributes().get("depositProduct").getAsString();
    }
    public String getRoutingNumber() {
        return getAttributes().get("routingNumber").getAsString();
    }
    public String getAccountNumber() {
        return getAttributes().get("accountNumber").getAsString();
    }

    public String getCurrency() {
        return getAttributes().get("balance").getAsString();
    }

    public long getBalance() {
        return getAttributes().get("balance").getAsLong();
    }

    public long getHold() {
        return getAttributes().get("hold").getAsLong();
    }

    public long getAvailable() {
        return getAttributes().get("available").getAsLong();
    }
}
