package com.iwhalecloud.retail.changan.DTO.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhengKai
 * @data 2019-11-07 14:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    /** 产品ID. */
    private String productId;

    /** 产品名称. */
    private String productName;

    /** 产品别名. */
    private String alias;

    /** 产品图片. */
    private String url;

    /** 产品价格. */
    private String price;

    /** 产品定价单位 U.S.$:美元 RMB：人民币. */
    private String priceUnit;

    /** 产品数量. */
    private Long number;

    /** 产品生效时间. */
    private String effectiveDate;

    /** 产品失效时间. */
    private String expiryDate;

    /** 产品描述. */
    private String description;

    /** 产品所属分类. */
    private String groupId;

    /** 所属应用ID. */
    private String appId;

    /** 产品归属品牌 [Integer] 0：乘用车 1：新能源 2：商用车*/
    private Integer brandId;

    private String usageUnit;

    private String usageValue;
}
