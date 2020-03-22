package com.iwhalecloud.retail.changan.dto.resp;

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


    //属性 begin
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
     * 内容目录类型
     */
    @ApiModelProperty(value = "内容目录类型")
    private String cataType;

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

    /**
     * 评论标志
     */
    @ApiModelProperty(value = "评论标志 Y 可评论 N 不可评论")
    private String discussFlag;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long sort;

    /**
     * 图片规格ID
     */
    @ApiModelProperty(value = "图片规格ID")
    private String specId;

    /**
     * 素材
     */
    @ApiModelProperty(value = "素材")
    private List<ContentMaterialDTO> contentMaterials;

    @ApiModelProperty(value = "目录名称")
    private String catalogName;

    @ApiModelProperty(value = "是否推荐到发现页")
    private String isDiscovery;

    /**
     * 阅读数
     */
    @ApiModelProperty(value = "阅读数")
    private Long viewCount;
    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    private Long usefulCount;
    /**
     * 评价数
     */
    @ApiModelProperty(value = "评价数")
    private Long evaluationCount;

    @ApiModelProperty(value = "评价审核方式")
    private String evaluationAuditMethod;

    @ApiModelProperty(value = "查看数量调整值")
    private Long adjViewCount;

    @ApiModelProperty(value = "赞数量调整值")
    private Long adjUsefulCount;

    @ApiModelProperty(value = "调整评价数")
    private Long adjEvaluationCount;

    /**
     *  图片url
     */
    @ApiModelProperty(value = "图片路径")
    private String picUrl;

    @ApiModelProperty(value = "图片的展示路径")
    private String picUrlImg;
}

