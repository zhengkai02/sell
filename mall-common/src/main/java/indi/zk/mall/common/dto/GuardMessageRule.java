package com.iwhalecloud.retail.common.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
public class GuardMessageRule implements Serializable {

    private static final long serialVersionUID = -6024117494054902658L;

    private String queue;

    /**
     * Y:可以降级 N:不可以降级
     */
    private String canDowngrade;

    /**
     * 重试次数，不可以降级时必须填写，不填时默认值为1
     */
    private Long retryNum;
}
