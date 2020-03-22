package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0<br>
 */
@Data
@TableName("tbl_sensitive_words")
public class SensitiveWords implements Serializable {

    private static final long serialVersionUID = -4131342264794602360L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long wordId;

    @ApiModelProperty(value = "敏感词")
    private String words;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String modifyBy;

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "状态: A 有效 X无效")
    private String state;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private String tenantId;
}
