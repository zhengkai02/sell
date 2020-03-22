package com.iwhalecloud.retail.offer.dto.client.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName QueryOrgReq
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/5/29 16:34
 **/
@Data
public class QueryOrgReq implements Serializable {

    private static final long serialVersionUID = 8240774052108144732L;

    private String storeId;
}
