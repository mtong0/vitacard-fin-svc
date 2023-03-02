package com.vitacard.finsvc.commons.unit;


import com.google.gson.JsonObject;
import commons.InternalJsonObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UnitCreateRewardRequest (
        @NotNull long amount,
        @NotBlank String receivingAccountType,
        @NotBlank String receivingAccountId,
        @NotBlank String fundingAccountType,
        @NotBlank String fundingAccountId,
        String description
) {
    private static final String type = "reward";

    public JsonObject getAttributes() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", amount);
        jsonObject.addProperty("description", description);
        return jsonObject;
    }

    public JsonObject getRelationships() {
        return new InternalJsonObject()
                .add("receivingAccount", new InternalJsonObject()
                    .add("data", new InternalJsonObject()
                            .addProperty("type", receivingAccountType)
                            .addProperty("id", receivingAccountId)
                            .jsonObject())
                        .jsonObject()
                )
                .add("fundingAccount", new InternalJsonObject()
                        .add("data", new InternalJsonObject()
                            .addProperty("type", fundingAccountType)
                            .addProperty("id", fundingAccountId)
                            .jsonObject())
                        .jsonObject())
                .jsonObject();
    }

    public String value() {
        return new InternalJsonObject()
                .add("data", new InternalJsonObject()
                        .addProperty("type", type)
                        .add("attributes", getAttributes())
                        .add("relationships", getRelationships())
                        .jsonObject())
                .jsonObject()
                .toString();
    }
}
