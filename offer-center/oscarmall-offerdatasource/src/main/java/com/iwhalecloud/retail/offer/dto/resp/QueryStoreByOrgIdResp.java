package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据机构Id查询关联的店铺列表
 * 
 * @author fanxiaofei
 * @date 2019-06-25
 */
@Data
public class QueryStoreByOrgIdResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺标识")
    private String storeId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "状态")
    private String state;
}
