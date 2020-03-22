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
 * @CreateDate 2019/4/24 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since CRM <br>
 */
@Data
@TableName("tbl_cn_area")
public class Area implements Serializable {

    private Long id;

    private Long level;

    private Long parentCode;

    private Long areaCode;

    private Long zipCode;

    private Long cityCode;

    private String name;

    private String shortName;

    private String mergerName;

    private String pinyin;

    private String lng;

    private String lat;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;

}
