package com.iwhalecloud.retail.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author wang.yamei <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/4/17 <br>
 * @see com.iwhalecloud.retail.operation.dto.resp <br>
 * @since R9.0<br>
 */
@Data
public class VerifyCodeResp implements Serializable {

    private static final long serialVersionUID = 1L;

    private String verifyCode;

    private Date expDate;
}
