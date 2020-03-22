package com.iwhalecloud.retail.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.common.dto <br>
 * @since V9.0C<br>
 */
@Data
public class MqErrorMessage implements Serializable {

    private static final long serialVersionUID = 4876741216659067215L;

    private String messageId;

    private String topic;

    private String messageContent;

    private Date createTime;

    private Date dealDate;

    /**
     * A:异常 C:成功
     */
    private String state;

    private Date stateDate;

    private String comments;

    private String modifyBy;

    private Date modifyTime;

    private String exchange;

    private String queue;

    private Long rehandleTimes;
}
