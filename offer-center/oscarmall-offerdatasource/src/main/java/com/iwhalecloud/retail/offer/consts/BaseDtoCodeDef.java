package com.iwhalecloud.retail.offer.consts;


/**
 * DTO中公用
 *
 * @author fanxiaofei
 * @date 2019/03/05
 */
public abstract class BaseDtoCodeDef {

    /**
     * 公共
     */
    public static final String DATA_TYPE = "yyyy-MM-dd HH:mm:ss";

    public static final String GOODS_ID_IS_NULL = "商品标识不能为空";

    /**
     * ProdGoodsStockWaveReq
     * 自动库存上下架
     */
    public static final String SEQ_IS_NULL = "波次序号不能为空";

    public static final String STOCK_IN_DATE_IS_NULL = "上架时间不能为空";

    public static final String STOCK_IN_NUM_IS_NULL = "上架数量不能为空";

    public static final String STOCK_IN_TYPE_IS_NULL = "上架方式不能为空";


}
