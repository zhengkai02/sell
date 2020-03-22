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
public class CarSpecResp implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "车辆规格ID")
    private String carSpecId;

    @ApiModelProperty(value = "车辆规格名称")
    private String carSpecName;

    @ApiModelProperty(value = "车辆品牌ID")
    private String carBrandId;

    @ApiModelProperty(value = "父级ID")
    private String parentId;
}
