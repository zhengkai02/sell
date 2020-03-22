package com.iwhalecloud.retail.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xh on 2019/4/8.
 */
@Data
public class ContractRespBody implements Serializable {
    private static final long serialVersionUID = 1L;

    private String resultCode;
    private String resultMsg;

}
