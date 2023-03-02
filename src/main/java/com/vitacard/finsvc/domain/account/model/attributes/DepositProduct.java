package com.vitacard.finsvc.domain.account.model.attributes;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DepositProduct {
    CHECKING(0, "checking");
    private final int code;
    private final String name;
    DepositProduct(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DepositProduct getFromName(String name) {
        return Arrays.stream(DepositProduct.values()).filter(depositProduct -> depositProduct.getName().equals(name)).findFirst().orElseThrow();
    }

    public static DepositProduct getFromCode(int code) {
        return Arrays.stream(DepositProduct.values()).filter(depositProduct -> depositProduct.getCode() == code).findFirst().orElseThrow();
    }
}
