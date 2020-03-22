package com.iwhalecloud.retail.offer.consts;

/**
 * 店铺级别状态
 * 
 * @author fanxiaofei
 * @date 2019-04-28
 */
public abstract class StoreLevelStateDef {

    private StoreLevelStateDef() {
    }

    /**
     * 正常
     */
    public static final String STATE_A = "A";

    /**
     * 失效
     */
    public static final String STATE_X = "X";
}
