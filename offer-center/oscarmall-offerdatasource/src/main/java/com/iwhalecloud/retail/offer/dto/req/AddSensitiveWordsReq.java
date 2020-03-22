package com.iwhalecloud.retail.offer.dto.req;

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
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0<br>
 */
@Data
public class AddSensitiveWordsReq implements Serializable {

    private static final long serialVersionUID = -4725814025834539590L;

    @ApiModelProperty(value = "敏感词")
    private String words;
}
