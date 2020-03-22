package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接触渠道(域)
 * 
 * @author fanxiaofei
 * @date 2019-03-04
 */
@Data
@TableName("tbl_contact_channel")
public class ContactChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "渠道id")
    @TableId(type = IdType.INPUT)
    private String contactChannelId;

    @ApiModelProperty(value = "类型 1 H5商城 2 中移集团掌厅 3 江苏移动掌厅")
    private Long channelType;

    @ApiModelProperty(value = "名称")
    private String contactChannelName;

    @ApiModelProperty(value = "编码")
    private String channelCode;

    @ApiModelProperty(value = "支付中心渠道编码")
    private String payCenterChannel;

    @ApiModelProperty(value = "描述")
    private String comments;

    @ApiModelProperty(value = "机构")
    private String orgId;

    @ApiModelProperty(value = "租户")
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
