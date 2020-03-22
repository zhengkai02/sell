package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TagDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long tagId; //标签ID

    @ApiModelProperty(value = "标签名称")
    private String tagName; //标签名称

    @ApiModelProperty(value = "标签说明")
    private String tagDesc; //标签说明

    @ApiModelProperty(value = "标签颜色")
    private String tagColor; //标签颜色

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime; //更新时间

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "状态变更时间")
    private Date stateDate;

    @ApiModelProperty(value = "透明度")
    private Integer alpha;

    @ApiModelProperty(value = "rgb")
    private String rgb;
}

