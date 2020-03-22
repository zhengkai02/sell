package com.iwhalecloud.retail.offer.dto.client.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ContentBaseDTO
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_base, 对应实体ContentBase类")
public class ContentBaseDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // 属性 begin
    /**
     * 内容ID
     */
    @ApiModelProperty(value = "内容ID")
    @TableId(type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long contentId;

    /**
     * 内容ID
     */
    @ApiModelProperty(value = "内容ID")
    private String title;

    /**
     * 内容说明
     */
    @ApiModelProperty(value = "内容说明")
    private String comments;

    /**
     * 归属目录ID
     */
    @ApiModelProperty(value = "归属目录ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cataId;

    /**
     * 生效时间
     */
    @ApiModelProperty(value = "生效时间")
    private java.util.Date effDate;

    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间")
    private java.util.Date expDate;

    /**
     * 内容类型
     */
    @ApiModelProperty(value = "内容类型")
    private Integer type;

    /**
     * 内容状态
     */
    @ApiModelProperty(value = "内容状态")
    private String state;

    /**
     * 文案
     */
    @ApiModelProperty(value = "文案")
    private String copywriter;

    /**
     * 轮播间隔
     */
    @ApiModelProperty(value = "轮播间隔")
    private Integer slidesInterval;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "更新人")
    private String modifyBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private java.util.Date modifyTime;

    /**
     * 操作类型标签
     */
    @ApiModelProperty(value = "操作类型标签")
    private String tag;

    @ApiModelProperty(value = "评价审核方式 A 自动 M 手动")
    private String evaluationAuditMethod;

    /**
     * 素材
     */
    @ApiModelProperty(value = "素材")
    private List<ContentMaterialDTO> contentMaterials;

}
