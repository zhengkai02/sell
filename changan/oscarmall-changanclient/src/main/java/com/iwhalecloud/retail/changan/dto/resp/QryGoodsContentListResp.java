package com.iwhalecloud.retail.changan.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author hu.minghang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/1 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0C<br>
 */
@Data
public class QryGoodsContentListResp implements Serializable {

    private static final long serialVersionUID = -4572295672451839298L;

    @ApiModelProperty(value = "商品内容id")
    private String goodsContentId;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "商品内容类型")
    private Long goodsContentType;

    @ApiModelProperty(value = "内容id")
    private String contentId;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "内容详情")
    private ContentBaseResponseDTO contentDeatil;
}
