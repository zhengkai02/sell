package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/1 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0C<br>
 */
@Data
public class QryGoodsCatMemListResp implements Serializable {

    private static final long serialVersionUID = -4572295672451839298L;

    @ApiModelProperty(value = "目录类目ID")
    private String catMemId;

    @ApiModelProperty(value = "目录ID")
    private String catId;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String sn;

    @ApiModelProperty(value = "类型ID")
    private String typeId;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "销售目录ID")
    private String saleCatId;

    @ApiModelProperty(value = "销售目录名称")
    private String saleCatName;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "店铺状态")
    private String storeState;

    @ApiModelProperty(value = "是否推荐标志")
    private String isRecommend;

    @ApiModelProperty(value = "销售渠道")
    private String saleChannel;

    @ApiModelProperty(value = "库存数量")
    private Long stockQty;

    @ApiModelProperty(value = "购买量")
    private Long buyCount;

    @ApiModelProperty(value = "价格")
    private Long price;

    @ApiModelProperty(value = "市场价")
    private Long mktprice;

    @ApiModelProperty(value = "上架时间")
    private Date marketingBeginTime;

    @ApiModelProperty(value = "下架时间")
    private Date marketingEndTime;
}
