package com.vitacard.finsvc.domain.account.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AccountStatus {
    OPEN(0, "Open");
    private int code;
    private String status;
    AccountStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public static AccountStatus getFromStatus(String status) {
        return Arrays.stream(AccountStatus.values()).filter((accountStatus -> accountStatus.status.equals(status))).findFirst().orElseThrow();
    }

    public static AccountStatus getFromCode(int code) {
        return Arrays.stream(AccountStatus.values()).filter((accountStatus -> accountStatus.code == code)).findFirst().orElseThrow();
    }
}
