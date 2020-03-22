package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接触渠道类型(域)
 * 
 * @author huminghang
 * @date 2019-06-04
 */
@Data
@TableName("tbl_channel_type")
public class ChannelType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 渠道类型标识
     */
    @ApiModelProperty(value = "渠道类型标识")
    private Long channelType;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String channelTypeName;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String comments;

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
