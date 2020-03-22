package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName AddTblStoreCatgResp
 * @Author wangzhongbao
 * @Description
 * @Date 2019/4/28 15:43
 **/
@Data
public class AddTblStoreCatgResp implements Serializable {

    private static final long serialVersionUID = -6084365170986887039L;

    @ApiModelProperty(value = "目录ID")
    private String catId;

}
