package com.iwhalecloud.retail.offer.consts;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/6 <br>
 * @see com.iwhalecloud.retail.offer.consts <br>
 * @since V9.0C<br>
 */
public abstract class ProdGoodsStateDef {

    private ProdGoodsStateDef() { }

    public static final String CREATED = "A";

    public static final String AUDIT = "B";

    public static final String AUDIT_SUCCESS = "F";

    public static final String PUT_ON_SALE = "C";

    public static final String AUDIT_FAIL = "D";

    public static final String PULL_OFF_SALE = "E";

    public static final String INACTIVE = "X";

    /**
     * 实名认证  Y:需要实名认证 N/Null:不需要实名认证
     */
    public static final String IS_CERTIFICATION_Y = "Y";

    /**
     * 实名认证  Y:需要实名认证 N/Null:不需要实名认证
     */
    public static final String IS_CERTIFICATION_N = "N";
}
