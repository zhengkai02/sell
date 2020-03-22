package com.iwhalecloud.retail.offer.dto.resp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/24 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0C<br>
 */
@Data
public class SynMgtInvoiceCatgNameResp implements Serializable {

    private static final long serialVersionUID = 3046531097683858035L;

    @ApiModelProperty(value = "同步结果")
    private String syncResult;
}
