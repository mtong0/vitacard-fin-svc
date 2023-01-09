package com.vitacard.finsvc.infrastructure;

public enum DocumentType {
    INDIVIDUAL_APPLICATION(0, "individualApplication");

    DocumentType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    private int code;
    private String type;

    public int getCode() {
        return code;
    }

    public DocumentType setCode(int code) {
        this.code = code;
        return this;
    }

    public String getType() {
        return type;
    }

    public DocumentType setType(String type) {
        this.type = type;
        return this;
    }
}
