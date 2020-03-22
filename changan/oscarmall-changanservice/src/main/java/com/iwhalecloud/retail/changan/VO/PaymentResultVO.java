package com.iwhalecloud.retail.changan.VO;

import lombok.Data;

/**
 * @author ZhengKai
 * @data 2019-10-30 10:22
 */
@Data
public class PaymentResultVO<T> {

    /**
     * 操作结果(是否成功)
     */
    private boolean status;

    /**
     * 失败原因
     */
    private String reason;

    /**
     * 支付二维码或url
     */
    private T data;
}
