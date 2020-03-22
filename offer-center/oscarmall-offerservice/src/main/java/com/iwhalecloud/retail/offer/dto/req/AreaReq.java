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
 * @CreateDate 2019/4/24 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since CRM <br>
 */
@Data
public class AreaReq implements Serializable {

    static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "层级")
    private Long level;

    @ApiModelProperty(value = "父级编码")
    private Long parentCode;
}
