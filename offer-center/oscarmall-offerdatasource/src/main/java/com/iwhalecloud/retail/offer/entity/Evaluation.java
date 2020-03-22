package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tbl_evaluation")
public class Evaluation implements Serializable {

    private static final long serialVersionUID = -2221865547410568494L;

    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "评价ID")
    private Long evaluationId;

    @ApiModelProperty(value = "评价对象: A 商品, B 内容")
    private String objType;

    @ApiModelProperty(value = "对象ID")
    private String objId;

    @ApiModelProperty(value = "评分 5  好评  3 中评  1 差评")
    private Integer rate;

    @ApiModelProperty(value = "评价用户ID")
    private String userId;

    @ApiModelProperty(value = "评价用户手机号")
    private String phone;

    @ApiModelProperty(value = "评价用户名")
    private String nickname;

    @ApiModelProperty(value = "评价描述")
    private String evaluationComments;

    @ApiModelProperty(value = "状态: A 有效  B 隐藏  C 待审核 D 审核不通过 X 删除")
    private String state;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新员工")
    private String modifyBy;

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "是否匿名评价: Y 是 N 否")
    private String anonymous;

    @ApiModelProperty(value = "关联评价ID")
    private Long relaId;

    @ApiModelProperty(value = "关联类型: A 追评， B 评价的评价  C 折叠")
    private String relaType;

    @ApiModelProperty(value = "赞")
    private Integer usefulCount;

    @ApiModelProperty(value = "踩")
    private Integer uselessCount;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private String tenantId;
}
