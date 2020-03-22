package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接触渠道(域)
 * 
 * @author fanxiaofei
 * @date 2019-07-01
 */
@Data
public class QueryContactChannelResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道id")
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

}
