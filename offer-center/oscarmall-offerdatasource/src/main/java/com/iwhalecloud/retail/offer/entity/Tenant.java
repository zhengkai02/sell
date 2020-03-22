package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 租户
 * 
 * @author fanxiaofei
 * @date 2019-05-22
 */
@Data
@TableName("tbl_tenant")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "租户标识")
    private String tenantId;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;
}
