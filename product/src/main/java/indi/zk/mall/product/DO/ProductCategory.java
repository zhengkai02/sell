package indi.zk.mall.product.DO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author ZhengKai
 * @data 2019-09-15 11:44
 */
@Data
@Entity
@ApiModel("商品类目")
public class ProductCategory {
    /**
     * 类目ID.
     */
    @Id
    @GeneratedValue
    @ApiModelProperty("类目ID")
    private Integer categoryId;

    /**
     * 类目名称.
     */
    @ApiModelProperty("类目名称")
    private String categoryName;

    /**
     * 类目编号.
     */
    @ApiModelProperty("类目编号")
    private Integer categoryType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;
}
