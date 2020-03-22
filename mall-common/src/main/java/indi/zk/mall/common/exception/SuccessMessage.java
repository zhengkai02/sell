package com.iwhalecloud.retail.common.exception;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/2/28 <br>
 * @see com.iwhalecloud.retail.common.exception <br>
 * @since V9.0C<br>
 */
@Data
public class SuccessMessage {

    private String code = "0000";

    private String message = "success";
}
