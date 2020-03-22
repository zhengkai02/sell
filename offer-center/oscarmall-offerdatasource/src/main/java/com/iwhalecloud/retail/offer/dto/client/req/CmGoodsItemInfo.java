package com.iwhalecloud.retail.offer.dto.client.req;

import java.io.Serializable;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/20 <br>
 * @see com.iwhalecloud.retail.offer.dto.client.req <br>
 * @since V9.0C<br>
 */
@Data
public class CmGoodsItemInfo implements Serializable {

    private static final long serialVersionUID = 6472143145171422997L;

    private String goodsItemSerial;

    private String goodsItemName;

}
