package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * <Description> <br> 
 *  
 * @author wang.zhongbao<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年3月4日 <br>
 * @since V9.0C<br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 */
@Data
public class DomainReq {
    
    /**
     * tableName
     */
    @ApiModelProperty(value = "表名")
    String tableName;
    
    /**
     * columnName
     */
    @ApiModelProperty(value = "列名")
    String columnName;

}
