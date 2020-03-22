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
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
@TableName("tbl_mq_error_message")
public class TblMqErrorMessage implements Serializable {

    private static final long serialVersionUID = 9192016122928344508L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "消息ID")
    private String messageId;

    @ApiModelProperty(value = "主题")
    private String topic;

    @ApiModelProperty(value = "消息内容")
    private String messageContent;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "处理时间")
    private Date dealDate;

    /**
     * A:异常 C:成功
     */
    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    @ApiModelProperty(value = "描述")
    private String comments;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "exchange")
    private String exchange;

    @ApiModelProperty(value = "queue")
    private String queue;

    @ApiModelProperty(value = "重处理次数")
    private Long rehandleTimes;


}
