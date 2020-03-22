package com.iwhalecloud.retail.offer.consts;

/**
 * Created by xxx on 2019/3/28.
 */
public abstract class ProdGoodsStockWaveStateDef {

    private ProdGoodsStockWaveStateDef() {
    }

    public static final String CREATED = "A";

    public static final String DELETED = "X";

    /**
     * 上架中
     */
    public static final String STOCKING = "B";

    /**
     * 上架完成
     */
    public static final String STOCKED = "C";
}
