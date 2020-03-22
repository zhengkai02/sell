package com.iwhalecloud.retail.offer.dto.client.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ContentMaterial
 * 
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型content_material, 对应实体ContentMaterial类")
public class ContentMaterialDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // 属性 begin
    /**
     * matid
     */
    @ApiModelProperty(value = "媒体内容ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long matId;

    /**
     * contentid
     */
    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    /**
     * name
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * path
     */
    @ApiModelProperty(value = "url")
    private String url;

    /**
     * thumbpath
     */
    @ApiModelProperty(value = "缩略图URL")
    private String thumbUrl;

    /**
     * level
     */
    @ApiModelProperty(value = "媒体类型")
    private Integer matType;

    @ApiModelProperty(value = "排序")
    private Long seq;

    @ApiModelProperty(value = "对象类型")
    private Integer objType;

    @ApiModelProperty(value = "对象ID")
    private String objId;

    @ApiModelProperty(value = "对象地址")
    private String objUrl;

    @ApiModelProperty(value = "showUrl")
    private String showUrl;

    @ApiModelProperty(value = "路径token")
    private String pathToken;

    @ApiModelProperty(value = "缩略图token")
    private String thumbpathToken;

}
