package com.iwhalecloud.retail.changan.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/29 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0<br>
 */
@Data
public class TblGoodsBrandTenantResp implements Serializable {

    private static final long serialVersionUID = 5823492554095796202L;

    @ApiModelProperty(value = "品牌id")
    private String brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌介绍")
    private String brandIntro;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "租户id")
    private String tenantId;
}
