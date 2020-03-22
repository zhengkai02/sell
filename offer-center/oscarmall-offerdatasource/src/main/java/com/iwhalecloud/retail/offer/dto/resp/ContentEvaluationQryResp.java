package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author li.sunlong01<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/13 <br>
 * @see com.iwhalecloud.retail.management.client.offer.dto.resp <br>
 * @since V9.0<br>
 */
@Data
public class ContentEvaluationQryResp implements Serializable {

    private static final long serialVersionUID = 751045376191668911L;

    /**
     * id
     */
    @ApiModelProperty(value = "评论ID")
    private String evaluationId;

    /**
     * obj
     */
    @ApiModelProperty(value = "对象ID")
    private String objId;

    /**
     * 评论人
     */
    @ApiModelProperty(value = "评论人")
    private String nickname;

    /**
     * 评论手机号
     */
    @ApiModelProperty(value = "评论手机号")
    private String phone;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容")
    private String evaluationComments;

    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间")
    private String createTime;

    /**
     * 评价级别
     */
    @ApiModelProperty(value = "评价级别")
    private String rate;

    /**
     * 文章名称
     */
    @ApiModelProperty(value = "文章名称")
    private String title;

    /**
     * 评价级别
     */
    @ApiModelProperty(value = "评价级别")
    private String state;

}
