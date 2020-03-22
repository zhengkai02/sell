package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author hu.minghang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/1 <br>
 * @see com.iwhalecloud.retail.management.client.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class ModGoodsCatAttrListReq {

    @ApiModelProperty(value = "目录ID")
    private String catId;

    @ApiModelProperty(value = "商品目录属性值")
    private List<ProdGoodsCatAttrValueReq> prodGoodsCatAttrValueList;
}
