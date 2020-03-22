package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/2/15 <br>
 * @see com.iwhalecloud.retail.offer.dto.request <br>
 * @since IOT <br>
 */
@Data
public abstract class AbstractPageReq implements Serializable {

    private static final long serialVersionUID = 3763143915437085393L;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    Integer pageNo;

    /**
     * 页大小
     */
    @ApiModelProperty(value = "页大小")
    Integer pageSize;
}
