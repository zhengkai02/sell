package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接触渠道(域)
 * 
 * @author fanxiaofei
 * @date 2019-03-04
 */
@Data
public class ContactChannelResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id")
    private String contactChannelId;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型 1 H5商城 2 中移集团掌厅 3 江苏移动掌厅")
    private Long channelType;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String contactChannelName;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String channelCode;

    /**
     * 支付中心渠道编码
     */
    @ApiModelProperty(value = "支付中心渠道编码")
    private String payCenterChannel;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String comments;

    /**
     * 租户
     */
    @ApiModelProperty(value = "租户")
    private String tenantId;

}
