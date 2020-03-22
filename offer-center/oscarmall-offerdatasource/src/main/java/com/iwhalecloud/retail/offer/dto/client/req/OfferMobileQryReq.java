package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @ClassName OfferMobileQryReq
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/3/21 11:16
 **/
@Data
public class OfferMobileQryReq implements Serializable {

    private static final long serialVersionUID = -9127945065641678989L;

    /**
     *  offerType
     **/
    @ApiModelProperty(value = "商品类型")
    private String offerType;

    /**
     *offerName
     **/
    @ApiModelProperty(value = "商品名称")
    private String offerName;

    /**
     *catId
     **/
    @ApiModelProperty(value = "目录ID")
    private String catId;

    /**
     *offerCode
     **/
    @ApiModelProperty(value = "商品编码")
    private String offerCode;

    /**
     *offerId
     **/
    @ApiModelProperty(value = "商品ID")
    private String offerId;

    /**
     *rows
     **/
    @ApiModelProperty(value = "行数")
    private Long rows;

    /**
     *offset
     **/
    @ApiModelProperty(value = "当前页")
    private Long offset;

    /**
     *couponId
     **/
    @ApiModelProperty(value = "优惠券ID")
    private String couponId;

    /**
     *couponSpecId
     **/
    @ApiModelProperty(value = "优惠券规格ID")
    private String couponSpecId;

    /**
     * 店铺标识，根据传入的店铺标识来查询店铺下的商品列表
     */
    @ApiModelProperty(value = "店铺标识")
    private String storeId;

    /**
     * 店铺下目录标识
     */
    @ApiModelProperty(value = "店铺下目录标识")
    private String storeCatId;

    /**
     * contactChannelId
     */
    @ApiModelProperty(value = "渠道ID")
    private String contactChannelId;

    /**
     * Y:推荐商品/N:非推荐商品
     */
    @ApiModelProperty(value = "是否推荐")
    private String recommend;

    @ApiModelProperty(value = "商品ID集合")
    private List<String> offerIdList;

    @ApiModelProperty(value = "商品类型集合")
    private List<String> offerTypeList;




}
