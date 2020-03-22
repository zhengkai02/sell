package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.offer.dto.resp.UserRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @ClassName EvaluationReq
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/5/7 13:49
 **/
@Data
public class EvaluationReq implements Serializable {

    @ApiModelProperty(value = "对象")
    private List<EvaluationItemReq> objs;

    @ApiModelProperty(value = "用户信息")
    private UserRsp userRsp;
}
