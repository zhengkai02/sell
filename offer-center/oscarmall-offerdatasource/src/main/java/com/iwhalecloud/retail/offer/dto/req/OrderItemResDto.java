package com.iwhalecloud.retail.offer.dto.req;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @ClassName OrderItemResDto
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/4/2 14:27
 **/
@Data
public class OrderItemResDto implements Serializable {

    /**
     * 订单项资源id
     */
    private String resId;

    /**
     *订单项id
     */
    private String itemId;

    /**
     *订单id
     */
    private String orderId;

    /**
     *资源编码
     */
    private String resCode;

    /**
     *资源编号
     */
    private String resSn;

    /**
     *资源类型
     */
    private String resType;

    /**
     *资源名称
     */
    private String resName;

    /**
     *说明
     */
    private String comments;

    /**
     *资源提供商标识 1：世纪恒通
     */
    private Integer resSp;

    /**
     *资源创建时间
     */
    private Date createTime;

    /**
     *资源生效时间
     */
    private Date effDate;

    /**
     *资源失效时间
     */
    private Date expDate;

    /**
     *券额
     */
    private Long amount;

    /**
     *折扣卷
     */
    private Long discount;

    /**
     *使用方式
     */
    private Integer useWay;

    /**
     *使用方式名称
     */
    private String useWayName;

    /**
     *URL地址
     */
    private String urlPath;

    /**
     *资源使用时间
     */
    private Date usedDate;

    /**
     *资源使用情况说明
     */
    private String usedComnets;

    /**
     *A:可用 C:已使用
     */
    private String state;

    /**
     *状态时间
     */
    private Date stateDate;


}
