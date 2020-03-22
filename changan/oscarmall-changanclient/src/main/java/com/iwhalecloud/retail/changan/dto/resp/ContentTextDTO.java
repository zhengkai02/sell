package com.iwhalecloud.retail.changan.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @ApiModelProperty(value = "内容id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long contentId;

    /**
     * textid
     */
    @ApiModelProperty(value = "文章段落id")
    private Long textId;

    @ApiModelProperty(value = "内容类型")
    private Integer contentType;

    /**
     * url
     */
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

    /**
     * url
     */
    @ApiModelProperty(value = "内容顺序")
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
