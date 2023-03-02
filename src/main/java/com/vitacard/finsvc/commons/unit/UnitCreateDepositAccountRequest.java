package com.vitacard.finsvc.commons.unit;

import jakarta.validation.constraints.NotBlank;
import org.json.JSONObject;

public record UnitCreateDepositAccountRequest (
        @NotBlank String depositProduct,
        @NotBlank String relationType,
        @NotBlank String relationId
) {
    private static final String accountType = "depositAccount";

    public JSONObject getAttributes() {
        return new JSONObject()
                .put("depositProduct", depositProduct);
    }

    public JSONObject getRelationships() {
        return new JSONObject()
                .put(relationType, new JSONObject()
                        .put("data", new JSONObject()
                                .put("type", relationType)
                                .put("id", relationId)));
    }

    public String value() {
        return new JSONObject()
                .put("data", new JSONObject()
                        .put("type", accountType)
                        .put("attributes", getAttributes())
                        .put("relationships", getRelationships()))
                .toString();
    }
}
