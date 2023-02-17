package com.vitacard.finsvc.domain.application.model;

public enum ApplicationStatus {
    APPROVED(0, "Approved");
    private int code;
    private String status;

    ApplicationStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public ApplicationStatus setCode(int code) {
        this.code = code;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ApplicationStatus setStatus(String status) {
        this.status = status;
        return this;
    }

    public static ApplicationStatus getByCode(int code) {
        for (ApplicationStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    public static ApplicationStatus getByStatus(String s) {
        for (ApplicationStatus status : values()) {
            if (status.getStatus().equals(s)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid statusCode: " + s);
    }
}
