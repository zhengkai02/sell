package com.iwhalecloud.retail.common.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 出参公共类
 *
 * @author fanxiaofei
 * @date 2019/02/25
 */
@Data
@ToString
public class ResultVO<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应编码
     */
    @ApiModelProperty(value = "响应编码")
    private String code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private String message;

    private T data;

    public ResultVO() {

    }

    public ResultVO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVO(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
