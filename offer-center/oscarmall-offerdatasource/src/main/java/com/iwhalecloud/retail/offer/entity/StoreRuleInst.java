package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺适用规则实例
 * 
 * @author fanxiaofei
 * @date 2019-04-28
 */
@Data
@TableName("tbl_store_rule_inst")
public class StoreRuleInst implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "规则实例id")
    private String ruleInstId;

    @ApiModelProperty(value = "店铺标识")
    private String storeId;

    @ApiModelProperty(value = "适用规则标识")
    private Integer ruleId;

    @ApiModelProperty(value = "适用对象标识")
    private String objId;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;

}
