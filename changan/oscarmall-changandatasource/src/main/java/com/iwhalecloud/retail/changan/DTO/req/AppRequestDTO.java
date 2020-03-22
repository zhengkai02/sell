package com.iwhalecloud.retail.changan.DTO.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZhengKai
 * @data 2019-11-01 14:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 产品归属品牌 [Integer] 0：乘用车 1：新能源 2：商用车.
     */
    private Integer brandId;

    /**
     * 应用名.
     */
    private String appName;

    /**
     * 应用编码.
     */
    private String appCode;
}
