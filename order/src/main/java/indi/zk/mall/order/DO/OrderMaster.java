package indi.zk.mall.order.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:05
 */
@Data
@Entity
public class OrderMaster {

    /**
     * 订单ID.
     */
    @Id
    private String orderId;

    /**
     * 买家名字.
     */
    private String buyerName;

    /**
     * 买家手机号.
     */
    private String buyerPhone;

    /**
     * 买家地址.
     */
    private String buyerAddress;

    /**
     * 买家微信OpenId.
     */
    private String buyerOpenId;

    /**
     * 订单总金额.
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态，默认0为新下单.
     */
    private Integer orderStatus;

    /**
     * 支付状态,默认0为未支付.
     */
    private Integer payStatus;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;
}
