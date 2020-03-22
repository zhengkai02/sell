package com.iwhalecloud.retail.offer.dto.client.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QryProdInventoryReq implements Serializable {

    private static final long serialVersionUID = 2124787520672470971L;

    private Long requestId;

    private String userId;

    private List<ProdInventory> productList;

}
