package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.common.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author hu.minghang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/2/27 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class QryAttrReq extends PageVO {

    private static final long serialVersionUID = -2431918822531849423L;

    @ApiModelProperty(value = "属性编码")
    private String attrCode;

    @ApiModelProperty(value = "属性名称")
    private String attrName;

    /**
     * 是否查询私有属性， Y不查询， N或者null都查询
     */
    @ApiModelProperty(value = "是否查询私有属性， Y不查询， N或者null都查询")
    private String hidePrivateAttr;
}
