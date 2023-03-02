package com.vitacard.finsvc.domain.transaction.dtos;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vitacard.finsvc.commons.unit.UnitDateFormat;
import unit.UnitMerchant;
import unit.UnitResponseData;

import java.sql.Timestamp;
import java.util.Optional;

public record UnitCardTransaction(UnitResponseData unitResponseData) implements UnitTransaction {
    public static final String TYPE = "cardTransaction";
    public String id() {
        return unitResponseData.id();
    }
    public String type() {
        return unitResponseData.type();
    }
    public Timestamp createdAt() {
        return UnitDateFormat.parse(unitResponseData.attributes().get("createdAt").getAsString());
    }

    public long amount() {
        return unitResponseData.attributes().get("amount").getAsLong();
    }

    public long balance() {
        return unitResponseData.attributes().get("balance").getAsLong();
    }

    public String direction() {
        return unitResponseData.attributes().get("direction").getAsString();
    }

    public String cardLast4Digits() {
        return unitResponseData.attributes().get("cardLast4Digits").getAsString();
    }

    public UnitMerchant merchant() {
        JsonObject merchantObject = unitResponseData.attributes().get("merchant").getAsJsonObject();
        return new UnitMerchant(
                Optional.ofNullable(merchantObject.get("name"))
                        .map(JsonElement::getAsString)
                        .orElse(null),
                Optional.ofNullable(merchantObject.get("type"))
                        .map(JsonElement::getAsString)
                        .orElse(null),
                Optional.ofNullable(merchantObject.get("category"))
                        .map(JsonElement::getAsString)
                        .orElse(null),
                Optional.ofNullable(merchantObject.get("location"))
                        .map(JsonElement::getAsString)
                        .orElse(null),
                Optional.ofNullable(merchantObject.get("id"))
                        .map(JsonElement::getAsString)
                        .orElse(null)
        );
    }

}
