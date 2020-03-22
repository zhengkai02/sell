package com.iwhalecloud.retail.changan.DTO;

import java.util.Date;
import java.util.List;

/**
 * @author ZhengKai
 * @data 2019-11-22 15:18
 */
public class ProductList {

    private String enterpriseId;
    private String  productSerial ;
    private String  productName ;
    private String  productDescription ;
    private int price;
    private int status;
    private Date activeTime;
    private Date expireTime;
    private int purchaseMode;
    private Date  eSIMTypes ;
    private int threshold;
    private int period;
    private List<Attributes> attributes ;
    private List<Package> aPackage;
    private int syncType;
}
