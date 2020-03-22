package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/29 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0<br>
 */
@Data
public class ModRelaBrandCatgReq implements Serializable {

    private static final long serialVersionUID = -8904497014866283025L;

    @ApiModelProperty(value = "关联品牌ID")
    List<String> relaBrandIds;
}
