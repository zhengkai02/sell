package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName EvaluationModeReq
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/5/10 14:41
 **/
@Data
public class EvaluationModeReq implements Serializable {

    private static final long serialVersionUID = 7344052065595403009L;

    @ApiModelProperty(value = "评论ID")
    private Long evaluationId;

    @ApiModelProperty(value = "评论描述")
    private String evaluationComments;

    @ApiModelProperty(value = "本时间段内评分")
    private String rate;

    @ApiModelProperty(value = "状态")
    private String state;

    /**
     * 更改人
     */
    @ApiModelProperty(value = "更改人")
    private String modifyBy;

}
