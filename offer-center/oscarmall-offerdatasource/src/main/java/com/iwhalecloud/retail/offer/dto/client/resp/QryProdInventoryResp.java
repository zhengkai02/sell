package com.iwhalecloud.retail.offer.dto.client.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class QryProdInventoryResp implements Serializable {

    private static final long serialVersionUID = -6829770427592814203L;

    /**
     * 1:可以售卖;0:不能售卖
     */
    private Integer flag;
}
