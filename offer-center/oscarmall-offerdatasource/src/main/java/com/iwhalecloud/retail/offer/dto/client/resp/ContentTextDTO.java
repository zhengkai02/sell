package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ContentText
 * 
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_text, 对应实体ContentText类")
public class ContentTextDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // 属性 begin
    /**
     * contentid
     */
    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    /**
     * textid
     */
    @ApiModelProperty(value = "文章段落ID")
    private Long textId;

    @ApiModelProperty(value = "内容类型")
    private Integer contentType;

    /**
     * url
     */
    @ApiModelProperty(value = "imgUrl")
    private String imgUrl;

    /**
     * url
     */
    @ApiModelProperty(value = "排序")
    private Integer seq;

    /**
     * oprid
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * upddate
     */
    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "showUrl")
    private String showUrl;

    @ApiModelProperty(value = "token")
    private String token;
}
