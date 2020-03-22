package com.iwhalecloud.retail.changan.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @auther add by wanghw on 2019/11/17
 * @return
 */
@Data
public class OfferToJDResultVO<T> implements Serializable {
    /**
     * 当前操作结果
     */
    @JsonProperty("status")
    private String  status;

    /**
     * 当前操作结果描述，字符串数组
     */
    @JsonProperty("messages")
    private String[]  messages;

    /**
     * 返回结果
     */
    @JsonProperty("result")
    private List result;

    public OfferToJDResultVO(){}

    public OfferToJDResultVO(String status) {
        this.status = status;
    }

    public OfferToJDResultVO(String status, String[] messages){
        this.status = status;
        this.messages = messages;
    }

    public OfferToJDResultVO(String status, String[] messages, List result){
        this.status = status;
        this.messages = messages;
        this.result = result;
    }
}
