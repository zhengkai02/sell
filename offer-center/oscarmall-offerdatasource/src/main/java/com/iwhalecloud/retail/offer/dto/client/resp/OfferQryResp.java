package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName OfferQryResp
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/3/20 12:11
 **/
@Data
public class OfferQryResp implements Serializable {

    private static final long serialVersionUID = -1530974233106727419L;

    /**
     * 商品标识
     **/
    @ApiModelProperty(value = "商品标识")
    private String offerId;

    /**
     * 商品名称
     **/
    @ApiModelProperty(value = "商品名称")
    private String offerName;

    /**
     * 商品编码
     **/
    @ApiModelProperty(value = "商品编码")
    private String offerCode;

    /**
     * 商品副标题
     **/
    @ApiModelProperty(value = "商品副标题")
    private String desc;

    /**
     * 类型
     **/
    @ApiModelProperty(value = "类型")
    private String offerType;

    /**
     * 类型名称
     **/
    @ApiModelProperty(value = "类型名称")
    private String offerTypeName;

    /**
     * 产品编码
     **/
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 管理目录标识
     **/
    @ApiModelProperty(value = "管理目录标识")
    private String offerCatgId;

    /**
     * 管理目录名称
     **/
    @ApiModelProperty(value = "管理目录名称")
    private String offerCatgName;

    /**
     * 好评率
     **/
    @ApiModelProperty(value = "好评率")
    private Long evaluationRate;

    /**
     * 评价数
     **/
    @ApiModelProperty(value = "评价数")
    private Long evaluationCount;

    /**
     * 是否关怀商品
     */
    @ApiModelProperty(value = "是否关怀商品")
    private String careFlag;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private Long stockQty;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String state;

}
