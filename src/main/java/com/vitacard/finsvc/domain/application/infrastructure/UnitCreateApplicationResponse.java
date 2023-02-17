package com.vitacard.finsvc.domain.application.infrastructure;

import unit.UnitResponse;

public class UnitCreateApplicationResponse extends UnitResponse {
    public UnitCreateApplicationResponse(String unitResponseString) {
        super(unitResponseString);
    }

    public String getId() {
        return getData().get("id").getAsString();
    }

    public String getStatus() {
        return getAttributes().get("status").getAsString();
    }

    public String getCustomerId() {
        return getRelationships()
                .get("customer")
                .get(0)
                .getId();
    }
}
