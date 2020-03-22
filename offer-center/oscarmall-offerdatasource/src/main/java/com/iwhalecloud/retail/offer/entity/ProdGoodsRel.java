package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品关系
 * 
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
@TableName("tbl_prod_goods_rel")
public class ProdGoodsRel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 商品关系id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "商品关系id")
    private String relId;

    /**
     * A端商品id
     */
    @ApiModelProperty(value = "A端商品id")
    private String aGoodsId;

    /**
     * 商品关系类型
     */
    @ApiModelProperty(value = "商品关系类型")
    private String relType;

    /**
     * Z端商品id
     */
    @ApiModelProperty(value = "Z端商品id")
    private String zGoodsId;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;

}
