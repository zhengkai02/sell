package com.iwhalecloud.retail.offer.dto.resp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0<br>
 */
@Data
public class AddSensitiveWordsResp implements Serializable {

    private static final long serialVersionUID = -4184863396817121898L;

    @ApiModelProperty(value = "ID")
    private Long id;
}
