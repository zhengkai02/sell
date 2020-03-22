package com.iwhalecloud.retail.offer.dto.client.resp;

import java.io.Serializable;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/20 <br>
 * @see com.iwhalecloud.retail.offer.dto.client.resp <br>
 * @since V9.0C<br>
 */
@Data
public class CmSyncGoodsItemInfoResp implements Serializable {

    private static final long serialVersionUID = -2455716136468533640L;

    private Boolean syncResult;
}
