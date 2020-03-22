package com.iwhalecloud.retail.offer.consts;


/**
 * 店铺状态
 * @author fanxiaofei
 * @date 2019/04/29
 */
public abstract class StoreStateDef {

    private StoreStateDef() { }

    /**
     * 新建
     */
    public static final String STATE_A = "A";

    /**
     * 待审批
     */
    public static final String STATE_B = "B";

    /**
     * 正常
     */
    public static final String STATE_C = "C";

    /**
     * 审批成功
     */
    public static final String STATE_F = "F";

    /**
     * 封禁
     */
    public static final String STATE_E = "E";

}
