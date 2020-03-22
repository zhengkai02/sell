package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @CreateDate 2019/2/25 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
@ApiModel(value = "对应模型prod_goods_cat, ProdGoodsCat")
@TableName("tbl_prod_goods_cat")
public class ProdGoodsCat implements Serializable {

    private static final long serialVersionUID = -867713730446451167L;

    @ApiModelProperty(value = "ID")
    private Long id;
    /**
     * 目录标识
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "目录标识")
    private String catId;

    /**
     * 目录名称
     */
    @ApiModelProperty(value = "目录名称")
    private String name;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String catType;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String comments;

    /**
     * 店铺
     */
    @ApiModelProperty(value = "店铺")
    private String storeId;

    /**
     * 上级目录标识
     */
    @ApiModelProperty(value = "上级目录标识")
    private String parentId;

    /**
     * LOGO
     */
    @ApiModelProperty(value = "LOGO")
    private String logo;

    /**
     * realLOGO
     */
    @ApiModelProperty(value = "RealLOGO")
    @TableField(exist = false)
    private String realLogo;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long catOrder;

    /**
     * TANANT_ID
     */
    @ApiModelProperty(value = "TENANT_ID")
    private String tenantId;

    /**
     * 是否活动目录
     */
    @ApiModelProperty(value = "是否活动目录")
    private String activeFlag;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String modifyBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "Y:可以开发票 N/Null:不可以开发票")
    private String invoiceFlag;

    @ApiModelProperty(value = "发票类目名称")
    private String invoiceCatgName;

    @ApiModelProperty(value = "状态")
    private String state;

}
