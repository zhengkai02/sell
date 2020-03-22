package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/12 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since CRM <br>
 */
@Data
@TableName("tbl_car_series")
public class CarSeries implements Serializable {

    private static final long serialVersionUID = 1925727010467244879L;

    private Long id;

    private String carSeriesId;

    private String carSeriesName;

    private String price;

    private String inoutSale;

    private String carSpecId;

    private String carBrandId;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;
}
