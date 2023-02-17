package com.vitacard.finsvc.domain.application.model;

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

    public static DocumentType getByCode(int code) {
        for (DocumentType documentType : values()) {
            if (documentType.getCode() == code) {
                return documentType;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    public static DocumentType getByType(String s) {
        for (DocumentType documentType : values()) {
            if (documentType.getType().equals(s)) {
                return documentType;
            }
        }
        throw new IllegalArgumentException("Invalid type: " + s);
    }


}
