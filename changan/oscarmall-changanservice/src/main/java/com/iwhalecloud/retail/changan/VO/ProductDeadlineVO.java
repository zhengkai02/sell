package com.iwhalecloud.retail.changan.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 产品到期推送配置配置
 * @author ZhengKai
 * @data 2019-10-30 09:40
 */
@Data
public class ProductDeadlineVO implements Serializable {

    /** 所属应用 */
    private String appId;

    /** 到期提醒剩余天数 */
    private ArrayList<Integer> days;
}
