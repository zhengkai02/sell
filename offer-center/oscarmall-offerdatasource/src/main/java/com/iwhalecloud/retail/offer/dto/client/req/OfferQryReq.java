package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @ClassName OfferQryReq
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/3/20 12:09
 **/
@Data
public class OfferQryReq implements Serializable {

    private static final long serialVersionUID = -2412148649936451711L;

    /**
     * 交易流水
     **/
    @ApiModelProperty(value = "交易流水")
    private Long requestId;

    /**
     * 商品类型
     **/
    @ApiModelProperty(value = "商品类型")
    private List<String> offerType;

    /**
     * 产品编码
     **/
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 目录标识
     **/
    @ApiModelProperty(value = "目录标识")
    private String offerCatgId;

    /**
     * 状态
     **/
    @ApiModelProperty(value = "状态")
    private String state;

    /**
     * 商品名称
     **/
    @ApiModelProperty(value = "商品名称")
    private String offerName;

    /**
     * 关怀商品
     **/
    @ApiModelProperty(value = "关怀商品")
    private String careFlag;

    /**
     * 租户id
     **/
    @ApiModelProperty(value = "租户id")
    private String tenantId;

}
