package com.iwhalecloud.retail.offer.consts;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/8 <br>
 * @see com.iwhalecloud.retail.offer.consts <br>
 * @since V9.0C<br>
 */
public abstract class CommonStateDef {

    private CommonStateDef() {
    }

    public static final String ACTIVE = "A";

    public static final String INACTIVE = "X";

    /** 上架方式：覆盖 */
    public static final String STOCK_IN_TYPE_C = "C";

    /** 上架方式：追加 */
    public static final String STOCK_IN_TYPE_P = "P";

    /**
     * ord_order_item_res表state字段 状态 A:可用 C:已使用
     */
    public static final String ORD_ORDER_ITEM_RES_STATE_C = "C";

}
