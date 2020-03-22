package com.iwhalecloud.retail.changan.dto.resp;

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
    @ApiModelProperty(value = "媒体内容id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long matId;

    /**
     * contentid
     */
    @ApiModelProperty(value = "内容id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long contentId;

    /**
     * name
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * path
     */
    @ApiModelProperty(value = "视频或者图片路径")
    private String url;

    /**
     * thumbpath
     */
    @ApiModelProperty(value = "缩略图路径")
    private String thumbUrl;

    /**
     * level
     */
    @ApiModelProperty(value = "媒体类型")
    private Integer matType;

    @ApiModelProperty(value = "显示顺序轮播图和图集用")
    private Long seq;

    @ApiModelProperty(value = "关联对象类型")
    private Integer objType;

    @ApiModelProperty(value = "关联对象id")
    private String objId;

    @ApiModelProperty(value = "关联对象url")
    private String objUrl;

    @ApiModelProperty(value = "展示url")
    private String showUrl;

    @ApiModelProperty(value = "路径token")
    private String pathToken;

    @ApiModelProperty(value = "缩略图路径token")
    private String thumbpathToken;

}
