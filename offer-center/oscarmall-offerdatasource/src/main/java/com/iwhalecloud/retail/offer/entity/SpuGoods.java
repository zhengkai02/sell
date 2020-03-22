package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * SPU关联商品
 * 
 * @author fanxiaofei
 * @date 2019-05-27
 */
@Data
@TableName("tbl_spu_goods")
public class SpuGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "sku标识")
    private String skuId;

    @ApiModelProperty(value = "spu标识")
    private String spuId;

    @ApiModelProperty(value = "规则")
    private String rule;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "状态 A:正常 X:失效")
    private String state;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    @ApiModelProperty(value = "创建用户ID")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新用户ID")
    private String modifyBy;

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "租户")
    private String tenantId;
}
