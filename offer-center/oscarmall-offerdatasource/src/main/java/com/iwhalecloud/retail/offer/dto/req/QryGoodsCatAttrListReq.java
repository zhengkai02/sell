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
 * @CreateDate 2019/6/1 <br>
 * @see com.iwhalecloud.retail.management.client.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class QryGoodsCatAttrListReq extends PageVO {

    @ApiModelProperty(value = "目录ID")
    private String catId;

    @ApiModelProperty(value = "数量")
    private String qt;
}
