package com.iwhalecloud.retail.offer.dto.req;

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
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class QryErrorMqMessageReq extends AbstractPageReq {

    private static final long serialVersionUID = 369489382184621577L;

    @ApiModelProperty(value = "exchange")
    private String exchange;

    @ApiModelProperty(value = "主题")
    private String topic;

    @ApiModelProperty(value = "queue")
    private String queue;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "处理时间从")
    private Date dealDateFrom;

    @ApiModelProperty(value = "处理时间对")
    private Date dealDateTo;
}
