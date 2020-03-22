package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/2/15 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since IOT <br>
 */
@Data
public class ProdGoodsQueryReq extends AbstractPageReq {

    @ApiModelProperty(value = "商品ID列表")
    private List<String> ids;

    @ApiModelProperty(value = "商品分类id")
    private String catId;

    @ApiModelProperty(value = "关键词搜索")
    private String searchKey;

    @ApiModelProperty(value = "上下架状态")
    private String marketEnable;

    @ApiModelProperty(value = "类型ID列表")
    private List<String> typeIds;

    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    @ApiModelProperty(value = "排序类型值")
    private String sortType;

    @ApiModelProperty(value = "状态")
    private List<String> states;

    @ApiModelProperty(value = "渠道id")
    private String contactChannelId;

    @ApiModelProperty(value = "排除商品ID列表")
    private List<String> excludeIds;

}

