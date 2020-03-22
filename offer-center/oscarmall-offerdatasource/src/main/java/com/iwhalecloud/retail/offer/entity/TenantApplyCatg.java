package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 租户可使用的商品目录
 * 
 * @author fanxiaofei
 * @date 2019-05-22
 */
@Data
@TableName("tbl_tenant_apply_catg")
public class TenantApplyCatg implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "标识")
    private String applyId;

    @ApiModelProperty(value = "租户标识")
    private String tenantId;

    @ApiModelProperty(value = "租户标识")
    private String catId;

    @ApiModelProperty(value = "状态 A:有效 X:无效")
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

}
