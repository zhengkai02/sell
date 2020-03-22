package com.iwhalecloud.retail.changan.tianji;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZhengKai
 * @data 2019-10-28 14:26
 */
@Data
public class ResultVO<T> implements Serializable {

    private String code;

    private String message;

    private T data;

    public ResultVO(){}

    public ResultVO(String code) {
        this.code = code;
    }

    public ResultVO(String code, String message){
        this.code = code;
        this.message = message;
    }

    public ResultVO(String code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
