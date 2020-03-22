package com.iwhalecloud.retail.common.consts;


/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/9 <br>
 * @see com.iwhalecloud.retail.common.consts <br>
 * @since V9.0C<br>
 */
public abstract class MqErrorMessageStateDef {

    private MqErrorMessageStateDef() { }

    public static final String ERROR = "A";

    public static final String IN_PROCESS = "B";

    public static final String SUCCESS = "C";

    public static final String INACTIVE = "X";


}
