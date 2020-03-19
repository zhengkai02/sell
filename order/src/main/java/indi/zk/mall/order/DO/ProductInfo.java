package indi.zk.mall.order.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhengKai
 * @data 2019-09-15 11:42
 */
@Data
@Entity
public class ProductInfo {

    /**
     * 商品ID.
     */
    @Id
    private String productId;

    /**
     * 商品名称.
     */
    private String productName;

    /**
     * 商品价格.
     */
    private BigDecimal productPrice;

    /**
     * 商品库存.
     */
    private Integer productStock;

    /**
     * 商品描述.
     */
    private String productDescription;

    /**
     * 商品小图.
     */
    private String productIcon;

    /**
     * 商品状态：0-上架，1-下架
     */
    private Integer productStatus;

    /**
     * 类目.
     */
    private Integer categoryType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;
}
