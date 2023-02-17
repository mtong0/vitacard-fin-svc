package com.vitacard.finsvc.domain.account.dtos;

import lombok.Value;
import org.json.JSONObject;

@Value
public class UnitCreateDepositAccountRequest {
    private static final String accountType = "depositAccount";
    String depositProduct;
    String relationType;
    String relationId;

    public UnitCreateDepositAccountRequest(String depositProduct, String relationType, String relationId) {
        this.depositProduct = depositProduct;
        this.relationType = relationType;
        this.relationId = relationId;
    }

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
