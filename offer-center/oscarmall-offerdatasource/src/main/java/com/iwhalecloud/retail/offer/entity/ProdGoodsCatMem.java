package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/2/28 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
@ApiModel(value = "对应模型tbl_prod_goods_cat_mem, ProdGoodsCatMem")
@TableName("tbl_prod_goods_cat_mem")
public class ProdGoodsCatMem implements Serializable {

    private static final long serialVersionUID = 1279261246252263880L;

    private Long id;

    /**
     * 目录成员标识
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "目录成员标识")
    private String catMemId;

    /**
     * 目录标识
     */
    @ApiModelProperty(value = "目录标识")
    private String catId;

    /**
     * 商品标识
     */
    @ApiModelProperty(value = "商品标识")
    private String goodsId;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更改人")
    private String modifyBy;

    @ApiModelProperty(value = "更改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "状态")
    private String state;
}
