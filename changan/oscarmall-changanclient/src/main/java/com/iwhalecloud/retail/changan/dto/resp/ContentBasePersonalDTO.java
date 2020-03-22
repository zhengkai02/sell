package com.iwhalecloud.retail.changan.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ContentBase
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "内容详情个性化信息")
public class ContentBasePersonalDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // 目录信息
    @ApiModelProperty(value = "目录信息")
    private List<CataLog> cataLogs;

    // 标签
    @ApiModelProperty(value = "标签")
    private List<TagDTO> tags;

    // 内容标签
    @ApiModelProperty(value = "内容标签")
    private List<ContentTagDTO> contentTags;

    // 内容素材
    @ApiModelProperty(value = "内容素材")
    private List<ContentMaterialDTO> contentMaterials;

    // 软文
    @ApiModelProperty(value = "软文")
    private List<ContentTextDTO> contentTexts;
}
