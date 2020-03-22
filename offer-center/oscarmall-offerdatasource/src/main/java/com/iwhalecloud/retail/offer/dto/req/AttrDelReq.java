package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;

import com.iwhalecloud.retail.common.dto.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/16 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0<br>
 */
@Data
public class AttrDelReq implements Serializable {

    private static final long serialVersionUID = 843763527864142604L;

    @ApiModelProperty(value = "属性ID")
    private String attrId;

    @ApiModelProperty(value = "用户信息")
    private UserDTO userDTO;
}
