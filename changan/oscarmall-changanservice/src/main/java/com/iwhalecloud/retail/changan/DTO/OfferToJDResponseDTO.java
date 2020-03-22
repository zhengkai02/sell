package com.iwhalecloud.retail.changan.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "messages",
        "result"
})
    /**
     * @author wanghw
     * 商品同步接口、 通用通骏接口返回参数相同
     */
public class OfferToJDResponseDTO implements Serializable {
    /**
     * 当前操作结果(必须)
     */
    @JsonProperty("status")
    private String status;

    /**
     * 当前操作结果描述，字符串数组(必须)
     */
    @JsonProperty("messages")
    private String[] messages;

    /**
     * 返回结果(非必须)
     */
    @JsonProperty("result")
    private List result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }
}
