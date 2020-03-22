package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.common.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fanxiaofei
 * @date 2019-07-30
 */
@Data
public class CpspQueryOfferListReq extends PageVO implements Serializable {


    @ApiModelProperty(value = "请求交易流水 M")
    private Long requestId;

    @ApiModelProperty(value = "商品类型")
    private String offerType;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "销售目录标识")
    private String offerCatgId;

    @ApiModelProperty(value = "商品名称，支持模糊")
    private String offerName;

    @ApiModelProperty(value = "商品编码，精确匹配")
    private String offerCode;

    @ApiModelProperty(value = "商品标识,多个之间用逗号分隔 O")
    private String offerIds;

    @ApiModelProperty(value = "商品标识列表,由offerIds切割而来")
    private List<String> offerIdList;

    @ApiModelProperty(value = "渠道编码 O")
    private String channelCode;

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value="销售价格 O")
    private String price;

    @ApiModelProperty(value="销售价格上限 O")
    private String startPrice;

    @ApiModelProperty(value="销售价格下限 O")
    private String endPrice;

    //是否返回图片url，值为 1代表需要返回picList，其他情况不返回
    @ApiModelProperty(value="是否返回内容信息 O")
    private String isGetContentInfo;

    @ApiModelProperty(value="Y 推荐商品 N 非推荐商品 A 所有商品  默认非推荐商品 O")
    private String isRecommend;

}
