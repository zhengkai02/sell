package com.iwhalecloud.retail.common.consts;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/28 <br>
 * @see com.iwhalecloud.retail.common.consts <br>
 * @since V9.0C<br>
 */
public abstract class OperLogEventDef {

    private OperLogEventDef() { }

    public static final Long OPER_EVENT_BATCH_DEL_GOODS = 1L;

    public static final Long OPER_EVENT_BATCH_DEL_COUPON = 2L;

    public static final Long OPER_EVENT_BATCH_DEL_CAMPAIGN = 3L;

    public static final Long OPER_EVENT_BATCH_DEL_ORDER = 4L;

    public static final Long OPER_EVENT_BATCH_PUT_ON_GOODS = 5L;

    public static final Long OPER_EVENT_BATCH_PULL_OFF_GOODS = 6L;

}
