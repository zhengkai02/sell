package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ContentTag
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_tag, 对应实体ContentTag类")
public class ContentTagDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // 属性 begin
    @ApiModelProperty(value = "关联ID")
    private Long relaId;

    /**
     * 内容ID
     */
    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    /**
     * 标签ID
     */
    @ApiModelProperty(value = "标签ID")
    private Long tagId;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String createBy;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private java.util.Date createTime;

    @ApiModelProperty(value = "state")
    private String state;

    @ApiModelProperty(value = "updateDate")
    private java.util.Date modifyTime;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String modifyBy;
}
