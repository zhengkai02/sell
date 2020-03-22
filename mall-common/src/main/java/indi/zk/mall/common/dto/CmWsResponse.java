package com.iwhalecloud.retail.common.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CmWsResponse<T> {

    private String status;

    private List<String> messages;

    private Timestamp responseTime;

    private T result;

    public CmWsResponse() {
        messages = new ArrayList<>();
    }

    public CmWsResponse(String status, List<String> messages, T result) {
        this.status = status;
        this.messages = messages;
        this.result = result;
        this.responseTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }

    public Timestamp getResponseTime() {
        return responseTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "error code:" + status + " result:" + result;
    }

}
