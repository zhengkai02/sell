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
public class CarBrandResp implements Serializable {

    @ApiModelProperty(value = "车品牌ID")
    private String carBrandId;

    @ApiModelProperty(value = "车品牌名称")
    private String carBrandName;

    @ApiModelProperty(value = "首字母")
    private String idx;

    @ApiModelProperty(value = "图片地址")
    private String img;
}
