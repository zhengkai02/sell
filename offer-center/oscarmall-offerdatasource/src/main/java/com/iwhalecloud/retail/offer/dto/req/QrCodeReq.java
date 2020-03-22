package com.iwhalecloud.retail.offer.dto.req;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @ClassName QrCodeReq
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/4/2 10:57
 **/
@Data
public class QrCodeReq implements Serializable {

    private static final long serialVersionUID = -3209312120032602421L;

    /**
     * requestId
     */
    private Long requestId;

    /**
     * ticketId
     */
    private String ticketId;

    /**
     * userId
     */
    private String userId;

    /**
     * phone
     */
    private String phone;

    /**
     * useTime
     */
    private Date useTime;

    /**
     * shopName
     */
    private String shopName;




}
