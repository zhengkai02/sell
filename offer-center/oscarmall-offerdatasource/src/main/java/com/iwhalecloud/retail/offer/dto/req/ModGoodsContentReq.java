package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author hu.minghang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/28 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class ModGoodsContentReq implements Serializable {

    private static final long serialVersionUID = -9045019184872057936L;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "内容ID")
    private String contentId;

    @ApiModelProperty(value = "商品内容类型")
    private Long goodsContentType;

    @ApiModelProperty(value = "用户ID")
    private String userId;
}
