package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/18 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since CRM <br>
 */
@Data
public class CarSpecReq implements Serializable {

    static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "车辆品牌ID")
    private String carBrandId;
}
