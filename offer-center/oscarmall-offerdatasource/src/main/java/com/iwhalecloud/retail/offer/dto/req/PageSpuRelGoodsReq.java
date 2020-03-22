package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.common.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 关联商品分页
 * 
 * @author fanxiaofei
 * @date 2019-05-27
 */
@Data
public class PageSpuRelGoodsReq extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "spuId")
    private String spuId;

    @ApiModelProperty(value = "商品名称")
    private String name;

}
