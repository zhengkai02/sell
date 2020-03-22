package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ContentBase
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "内容详情信息：内容详情和个性化信息")
public class ContentBaseResponseDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    //内容基础信息表
    @ApiModelProperty(value = "内容基础信息表")
    private ContentBaseDTO contentBase;
    //内容个性化信息
    @ApiModelProperty(value = "内容个性化信息")
    private ContentBasePersonalDTO contentBasePersonal;

}
