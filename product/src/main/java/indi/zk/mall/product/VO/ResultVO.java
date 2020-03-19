package indi.zk.mall.product.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ZhengKai
 * @data 2019-09-15 12:06
 */
@Data
@ApiModel(value = "响应")
public class ResultVO<T> {

    @ApiModelProperty(value = "响应码")
    private Integer code;

    @ApiModelProperty(value = "响应消息")
    private String message;

    private T data;

}
