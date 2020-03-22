package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 属性值
 * 
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
@TableName("tbl_attr_value")
public class AttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标识")
    private Long id;

    /**
     * 属性值id
     */
    @ApiModelProperty(value = "属性值id")
    private String attrValueId;

    /**
     * 属性id
     */
    @ApiModelProperty(value = "属性id")
    private String attrId;

    /**
     * 属性值
     */
    @ApiModelProperty(value = "属性值")
    private String value;

    /**
     * 属性标志
     */
    @ApiModelProperty(value = "属性标志")
    private String valueMark;

    /**
     * tenantId
     */
    @ApiModelProperty(value = "tenantId")
    private String tenantId;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "状态")
    private String state;

}
