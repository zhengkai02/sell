package com.iwhalecloud.retail.changan.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "对应模型t_tag, 对应实体TagDTO类")
public class TagDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tagId;

    @ApiModelProperty(value = "标签名称")
    private String tagName;

    @ApiModelProperty(value = "标签说明")
    private String tagDesc;

    @ApiModelProperty(value = "标签颜色")
    private String tagColor;

    @ApiModelProperty(value = "更新时间")
    private Date updDate;

    @ApiModelProperty(value = "状态 A:正常 X:失效")
    private String state;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    @ApiModelProperty(value = "创建用户ID")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新用户ID")
    private String modifyBy;

    @ApiModelProperty(value = "tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "透明度")
    private Integer alpha;

    @ApiModelProperty(value = "rgb")
    private String rgb;

    @ApiModelProperty(value = "内容目录表id")
    private Long cataId;

    @ApiModelProperty(value = "标签目录名称")
    private String cataName;

}

