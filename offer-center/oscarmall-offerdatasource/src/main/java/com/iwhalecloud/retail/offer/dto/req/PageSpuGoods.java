package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.common.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * SPU关联商品分页
 * 
 * @author fanxiaofei
 * @date 2019-05-26
 */
@Data
public class PageSpuGoods extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "spuID")
    private String spuId;

}
