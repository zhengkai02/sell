package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xh on 2019/5/17.
 */
@Data
public class OfferSearchReq implements Serializable {

    /***
     * 1 手机 2 车机
     **/
    @ApiModelProperty(value = "设备")
    private String device;

    /***
     * 6.券类 7.内容会员 8 权益
     **/
    @ApiModelProperty(value = "商品类型")
    private String offerType;

    /***
     *商品名称 支持模糊查询
     **/
    //private String offerName;

    /***
     *目录ID
     **/
    @ApiModelProperty(value = "目录ID")
    private String catId;

    /**
     * 根目录(只要是商品目录或者父目录都可以)
     */
    @ApiModelProperty(value = "根目录")
    private String rootCatId;

    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private String brandId;

    /***
     *
     **/
    //private String offerCode;

    /***
     *offerId
     **/
    //private String offerId;

    /***
     *记录数默认20
     **/
    @ApiModelProperty(value = "记录数默认20")
    private Long rows;

    /***
     *开始位置 默认0
     **/
    @ApiModelProperty(value = "开始位置 默认0")
    private Long offset;

    /***
     *couponId
     **/
    @ApiModelProperty(value = "优惠券ID")
    private String couponId;

    /***
     *couponSpecId
     **/
    @ApiModelProperty(value = "优惠券规格ID")
    private String couponSpecId;

    /**
     * contactChannelId
     */
    @ApiModelProperty(value = "渠道ID")
    private String contactChannelId;

    @ApiModelProperty(value = "商品ID集合")
    private List<String> offerIdList;

    @ApiModelProperty(value = "商品类型集合")
    private List<String> offerTypeList;

    /**
     * 是否热销推荐商品 Y 只查询热销商品，其他查询所有
     */
    @ApiModelProperty(value = "是否热销推荐商品")
    private String isHot;

    /**
     * 模糊查询 支持 商品名、描述、key、目录等关键字
     */
    @ApiModelProperty(value = "模糊查询")
    private String q;

    /**
     * 排序  比如：sort=name 即按照name升序, sort=-name 即按照name降序
     */
    @ApiModelProperty(value = "排序")
    private String sort;

    /**
     * tag 和 attr 比如选中3个标签和颜色多选属性选择2个颜色过滤可以描述为filter=attr1:green;attr2:red;tag1;tag2;tag3
     */
    @ApiModelProperty(value = "过滤")
    private String filter;

    /**
     * 支持区间 逗号分隔价格
     */
    @ApiModelProperty(value = "支持区间")
    private String reservePrice;

    /**
     * Y:推荐商品/N:非推荐商品
     */
    @ApiModelProperty(value = "是否推荐")
    private String recommend;
}
