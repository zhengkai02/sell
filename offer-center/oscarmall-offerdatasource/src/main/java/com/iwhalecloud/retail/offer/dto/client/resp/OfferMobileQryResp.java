package com.iwhalecloud.retail.offer.dto.client.resp;

import com.iwhalecloud.retail.offer.dto.resp.TagResp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @ClassName OfferMobileQryResp
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/3/21 11:11
 **/
@Data
public class OfferMobileQryResp implements Serializable {

    private static final long serialVersionUID = 8546541755602528406L;

    @ApiModelProperty(value = "商品ID")
    private String offerId;

    @ApiModelProperty(value = "商品编码")
    private String offerCode;

    @ApiModelProperty(value = "商品名称")
    private String offerName;

    @ApiModelProperty(value = "商品类型")
    private String offerType;

    @ApiModelProperty(value = "副标题")
    private String simpleName;

    @ApiModelProperty(value = "市场价")
    private Long mktPrice;

    @ApiModelProperty(value = "售价")
    private Long price;

    @ApiModelProperty(value = "购买数")
    private Long buyCount;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "标签")
    private List<String> tags;

    @ApiModelProperty(value = "标签集合")
    private List<TagResp> tagList;

    /**
     * 上架时间
     */
    @ApiModelProperty(value = "上架时间")
    private Date marketingBeginTime;

    /**
     * 下架时间
     */
    @ApiModelProperty(value = "下架时间")
    private Date marketingEndTime;

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
     * 税费
     **/
    @ApiModelProperty(value = "税费")
    private Long tax;
}
