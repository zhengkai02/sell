package com.iwhalecloud.retail.offer.dto.client.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProdInventory implements Serializable {

    private static final long serialVersionUID = -4020140539668196794L;

    private String productCode;

    private Integer saleNum;
}
