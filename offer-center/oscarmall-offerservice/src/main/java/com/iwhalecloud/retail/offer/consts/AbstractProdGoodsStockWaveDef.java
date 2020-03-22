package com.iwhalecloud.retail.offer.consts;


/**
 * 自动库存上下架
 * @author fanxiaofei
 * @date 2019/02/13
 */
public abstract class AbstractProdGoodsStockWaveDef {

    private AbstractProdGoodsStockWaveDef() { }

    /**
     * 新增
     */
    public static final String STATUS_A = "A";

    /**
     * 删除
     */
    public static final String STATUS_X = "X";

    /**
     * 上架中
     */
    public static final String STATUS_B = "B";

    /**
     * 上架完成
     */
    public static final String STATUS_C = "C";

    /**
     * 覆盖
     */
    public static final String STOCK_IN_TYPE_C = "C";

    /**
     * 追加
     */
    public static final String STOCK_IN_TYPE_P = "P";

}
