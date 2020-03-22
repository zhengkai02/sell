package com.iwhalecloud.retail.offer.consts;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/14 <br>
 * @see com.iwhalecloud.retail.offer.consts <br>
 * @since V9.0C<br>
 */
public abstract class ProdGoodsRelTypeDef {

    private ProdGoodsRelTypeDef() {
    }

    /**
     * 互斥
     */
    public static final Long MUTEX = 1L;

    /**
     * 推荐
     */
    public static final Long RECOMMENT = 2L;
}
