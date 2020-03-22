package com.iwhalecloud.retail.common.exception;

public class ErrorMessage {
    String code;
    String message;
    String description;
    int type;
    String transactionId;
    String stack;

    public ErrorMessage() {
    }

    public ErrorMessage(String code, String message, String description, int type) {
        this(code, message, description, type, (String) null);
    }

    public ErrorMessage(String code, String message, String description, int type, String stack) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.type = type;
        this.stack = stack;

    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getStack() {
        return this.stack;
    }
}
