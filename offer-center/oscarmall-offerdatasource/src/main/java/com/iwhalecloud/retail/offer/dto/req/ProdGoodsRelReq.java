package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * <Description> <br> 
 *  
 * @author wang.zhongbao<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年3月7日 <br>
 * @since V9.0C<br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 */
@Data
public class ProdGoodsRelReq implements Serializable {
    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -7947372758912962934L;

    @ApiModelProperty(value = "A端商品id")
    private String agoodsId;

    @ApiModelProperty(value = "商品关系类型")
    private String relType;

    @ApiModelProperty(value = "Z端商品id")
    private List<String> zgoodsIds;
    

}
