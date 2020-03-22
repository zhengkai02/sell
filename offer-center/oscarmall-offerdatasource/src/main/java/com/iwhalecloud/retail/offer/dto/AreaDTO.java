package com.iwhalecloud.retail.offer.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/4/24 <br>
 * @see com.iwhalecloud.retail.offer.dto <br>
 * @since CRM <br>
 */
@Data
public class AreaDTO implements Serializable {

    @ApiModelProperty(value = "标识")
    private Long id;

    @ApiModelProperty(value = "层级")
    private Long level;

    @ApiModelProperty(value = "父级行政代码")
    private Long parentCode;

    @ApiModelProperty(value = "行政代码")
    private Long areaCode;

    @ApiModelProperty(value = "邮政编码")
    private Long zipCode;

    @ApiModelProperty(value = "区号")
    private Long cityCode;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "简称")
    private String shortName;

    @ApiModelProperty(value = "组合号")
    private String mergerName;

    @ApiModelProperty(value = "拼音")
    private String pinyin;

    @ApiModelProperty(value = "经度")
    private String lng;

    @ApiModelProperty(value = "纬度")
    private String lat;
}
