package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;
import java.util.Date;

import com.iwhalecloud.retail.common.dto.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * <Description> <br> 
 *  
 * @author wang.zhongbao<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年3月6日 <br>
 * @since V9.0C<br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 */
@Data
public class PublishGoodReq implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 4817766843034747530L;

    @ApiModelProperty(value = "上架")
    private Date marketBeg;

    @ApiModelProperty(value = "下架")
    private Date marketEnd;

    @ApiModelProperty(value = "用户信息")
    private UserDTO userDTO;
}
