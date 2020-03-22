package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author hu.minghang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/25 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
@ApiModel(value = "对应模型tbl_prod_goods_cat_attr_value, ProdGoodsCatAttrValue")
@TableName("tbl_prod_goods_cat_attr_value")
public class ProdGoodsCatAttrValue implements Serializable {

    private static final long serialVersionUID = -867713730446451167L;

    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "目录属性值标识")
    private String catAttrValueId;

    @ApiModelProperty(value = "属性值")
    private String attrValue;

    @ApiModelProperty(value = "目录标识")
    private String catId;

    @ApiModelProperty(value = "属性标识")
    private String attrId;

    @ApiModelProperty(value = "优先级")
    private Long priority;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String modifyBy;

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "租户标识")
    private String tenantId;

    @ApiModelProperty(value = "是否是继承属性，用于标识销售目录继承关联的管理目录的属性")
    private String inheritedFlag;

    @ApiModelProperty(value = "状态")
    private String state;
}
