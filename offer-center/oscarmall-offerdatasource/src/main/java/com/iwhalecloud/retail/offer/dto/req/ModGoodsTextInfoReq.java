package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;

import com.iwhalecloud.retail.common.dto.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/8 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class ModGoodsTextInfoReq implements Serializable {

    private static final long serialVersionUID = -9045019184872057936L;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "介绍")
    private String intro;

    @ApiModelProperty(value = "售后服务")
    private String afterSale;

    @ApiModelProperty(value = "用户信息")
    private UserDTO userDTO;
}
