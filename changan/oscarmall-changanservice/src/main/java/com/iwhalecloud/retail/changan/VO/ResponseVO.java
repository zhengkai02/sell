package com.iwhalecloud.retail.changan.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZhengKai
 * @data 2019-10-30 09:31
 */
@Data
public class ResponseVO implements Serializable {

    /**
     * 操作结果，是否成功(生效)
     */
    @JsonProperty("status")
    private boolean  status;

    /**
     *  操作失败(无效)原因.
     */
    @JsonProperty("reason")
    private String reason;
}
