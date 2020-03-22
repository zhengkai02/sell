package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/7 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class FileDeleteReq implements Serializable {

    private static final long serialVersionUID = -4507577842396204230L;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "关键字")
    private String key;

    @ApiModelProperty(value = "url")
    private String url;
}
