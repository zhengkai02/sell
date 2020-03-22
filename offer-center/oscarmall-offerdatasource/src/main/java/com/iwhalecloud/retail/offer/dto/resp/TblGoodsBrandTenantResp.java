package com.iwhalecloud.retail.offer.dto.resp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    private static final long serialVersionUID = 2986987684344204088L;

    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌图片")
    private String brandImg;

    @ApiModelProperty(value = "品牌介绍")
    private String brandIntro;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;
}
