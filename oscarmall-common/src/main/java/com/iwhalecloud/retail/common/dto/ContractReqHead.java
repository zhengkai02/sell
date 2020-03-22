package com.iwhalecloud.retail.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xh on 2019/4/8.
 */
@Data
public class ContractReqHead implements Serializable {
    private static final long serialVersionUID = 1L;

     private String apiId;
     private String channelCode;
     private String transactionId;
     private String reqTime;
     private String sign;
     private String version = "1.0";

}
