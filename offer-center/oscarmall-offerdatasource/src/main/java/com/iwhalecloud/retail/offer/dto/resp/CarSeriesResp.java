package com.iwhalecloud.retail.offer.dto.resp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/12 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since CRM <br>
 */
@Data
public class CarSeriesResp implements Serializable {

    @ApiModelProperty(value = "车型ID")
    private String carSeriesId;

    @ApiModelProperty(value = "车型")
    private String carSeriesName;

    @ApiModelProperty(value = "售价")
    private String price;

    @ApiModelProperty(value = "是否在售")
    private String inoutSale;

    @ApiModelProperty(value = "车辆规格ID")
    private String carSpecId;

    @ApiModelProperty(value = "车辆品牌ID")
    private String carBrandId;
}
