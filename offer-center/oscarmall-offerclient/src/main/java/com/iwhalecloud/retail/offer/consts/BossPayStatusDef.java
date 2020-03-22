package com.iwhalecloud.retail.offer.consts;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/15 <br>
 * @see com.iwhalecloud.retail.offer.consts <br>
 * @since V9.0C<br>
 */
public abstract class BossPayStatusDef {
    private BossPayStatusDef() {
    }

    /**
     * 0:未支付
     */
    public static final Integer WAIT_PAY = 0;

    /**
     * 1:支付成功
     */
    public static final Integer PAY_SUCCESS = 1;

    /**
     * 2：支付失败
     */
    public static final Integer PAY_FAIL = 1;
}
