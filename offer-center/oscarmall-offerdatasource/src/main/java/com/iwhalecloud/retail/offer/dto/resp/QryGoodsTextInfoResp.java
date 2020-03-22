package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/8 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class QryGoodsTextInfoResp implements Serializable {

    private static final long serialVersionUID = -9045019184872057936L;

    @ApiModelProperty(value = "介绍")
    private String intro;

    @ApiModelProperty(value = "售后服务")
    private String afterSale;
}
