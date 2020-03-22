package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺等级
 * 
 * @author fanxiaofei
 * @date 2019-04-28
 */
@Data
public class DeleteStoreLevelReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺等级id")
    private List<String> storeLevelIds;

    @ApiModelProperty(value = "更新用户ID")
    private String modifyBy;

}
