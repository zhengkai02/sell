package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author li.sunlong01<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/13 <br>
 * @see com.iwhalecloud.retail.management.client.offer.dto.req <br>
 * @since V9.0<br>
 */
@Data
public class ContentEvaluationQryReq extends AbstractPageReq {

    private static final long serialVersionUID = 7165873895560505803L;

    /**
     * 文章名称模糊搜索
     */
    @ApiModelProperty(value = "文章名称模糊搜索")
    private String title;

    /**
     * 根据文章名称到content中心模糊查询，得到的Id
     */
    @ApiModelProperty(value = "根据文章名称到content中心模糊查询，得到的Id")
    private List<String> objIds;

    /**
     * 评价人
     */
    @ApiModelProperty(value = "评价人")
    private String nickname;

    /**
     * 文章处理状态
     */
    @ApiModelProperty(value = "文章处理状态")
    private String state;

    /**
     * 文章创建时间
     */
    @ApiModelProperty(value = "文章创建时间")
    private Date beginTime;

    /**
     * 文章删除时间
     */
    @ApiModelProperty(value = "文章删除时间")
    private Date endTime;

}
