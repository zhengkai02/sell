package com.iwhalecloud.retail.offer.dto.resp;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/9 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0C<br>
 */
@Data
public class QryErrorMqMessageResp implements Serializable {

    private static final long serialVersionUID = -4642886323610213483L;

    @ApiModelProperty(value = "消息ID")
    private String messageId;

    @ApiModelProperty(value = "主题")
    private String topic;

    @ApiModelProperty(value = "消息内容")
    private String messageContent;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "处理时间")
    private Date dealDate;

    /**
     * A:异常 C:成功
     */
    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "描述")
    private String comments;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "queue")
    private String queue;

    @ApiModelProperty(value = "exchange")
    private String exchange;

    @ApiModelProperty(value = "重处理次数")
    private Long rehandleTimes;

}
