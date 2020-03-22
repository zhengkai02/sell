package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @ClassName EvaluationResp
 * @Author wangzhongbao
 * @Description
 * @Date 2019/5/7 14:50
 **/
@Data
public class EvaluationResp implements Serializable {

    private static final long serialVersionUID = 5712063784380159004L;


    @ApiModelProperty(value = "评论ID")
    private String evaluationId;

    @ApiModelProperty(value = "评论人")
    private String nickname;

    @ApiModelProperty(value = "评论手机号")
    private String phone;

    @ApiModelProperty(value = "评论内容")
    private String evaluationComments;

    @ApiModelProperty(value = "评论时间")
    private Date createTime;

    @ApiModelProperty(value = "评价级别")
    private String rate;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "对象名称")
    private String objName;

    @ApiModelProperty(value = "是否点赞")
    private String praiseFlag;

    @ApiModelProperty(value = "对象ID")
    private String objId;

    @ApiModelProperty(value = "对象类型")
    private String objType;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "赞")
    private Integer usefulCount;

    @ApiModelProperty(value = "踩")
    private Integer uselessCount;

}
