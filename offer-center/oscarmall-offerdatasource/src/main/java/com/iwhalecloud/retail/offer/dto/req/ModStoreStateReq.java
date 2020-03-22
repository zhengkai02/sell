package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName ModStoreStateReq
 * @Author wangzhongbao
 * @Description
 * @Date 2019/5/29 14:25
 **/
@Data
public class ModStoreStateReq implements Serializable {

    private static final long serialVersionUID = 3345408078122166018L;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "用户ID")
    private String modifyBy;
}
