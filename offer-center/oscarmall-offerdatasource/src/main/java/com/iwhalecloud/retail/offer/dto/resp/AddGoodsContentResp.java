package com.iwhalecloud.retail.offer.dto.resp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/8 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0C<br>
 */
@Data
public class AddGoodsContentResp implements Serializable {

    private static final long serialVersionUID = -8130174413299830079L;

    @ApiModelProperty(value = "商品内容ID")
    private String goodsContentId;
}
