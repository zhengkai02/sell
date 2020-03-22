package com.iwhalecloud.retail.changan.exception;

import com.iwhalecloud.retail.changan.enums.AppResultEnum;
import com.iwhalecloud.retail.changan.enums.GroupResultEnum;
import com.iwhalecloud.retail.changan.enums.ProductEnum;

/**
 * @author ZhengKai
 * @data 2019-11-19 09:40
 */
public class ChangAnException extends RuntimeException{

    private Integer code;

    public ChangAnException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public ChangAnException(AppResultEnum appResultEnum){
        super(appResultEnum.getMessage());
        this.code = appResultEnum.getCode();
    }

    public ChangAnException(GroupResultEnum groupResultEnum) {
        super(groupResultEnum.getMessage());
        this.code = groupResultEnum.getCode();
    }

    public ChangAnException(ProductEnum productEnum){
        super(productEnum.getMessage());
        this.code = productEnum.getCode();
    }
}
