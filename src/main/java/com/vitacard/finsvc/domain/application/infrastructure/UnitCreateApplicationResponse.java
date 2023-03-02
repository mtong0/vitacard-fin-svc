package com.vitacard.finsvc.domain.application.infrastructure;


import unit.UnitResponse;
import unit.UnitResponseData;

public record UnitCreateApplicationResponse(UnitResponseData unitResponseData) {
    public String getId() {
        return unitResponseData.id();
    }

    public String getStatus() {
        return unitResponseData.attributes().get("status").getAsString();
    }

    public String getCustomerId() {
        return unitResponseData().relationships()
                .get("customer")
                .get(0)
                .id();
    }
}
