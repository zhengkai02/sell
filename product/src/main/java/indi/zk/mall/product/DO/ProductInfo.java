package indi.zk.mall.product.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhengKai
 * @data 2019-09-15 11:42
 */
@Data
@Entity
@ApiModel(value = "商品信息")
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 4722663225369273393L;

    /**
     * 商品ID.
     */
    @Id
    @ApiModelProperty(value = "商品ID")
    private String productId;

    /**
     * 商品名称.
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;

    /**
     * 商品价格.
     */
    @ApiModelProperty(value = "商品价格")
    private BigDecimal productPrice;

    /**
     * 商品库存.
     */
    @ApiModelProperty(value = "商品库存")
    private Integer productStock;

    /**
     * 商品描述.
     */
    @ApiModelProperty(value = "商品描述")
    private String productDescription;

    /**
     * 商品小图.
     */
    @ApiModelProperty(value = "商品小图")
    private String productIcon;

    /**
     * 商品状态：0-上架，1-下架
     */
    @ApiModelProperty(value = "商品状态")
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
