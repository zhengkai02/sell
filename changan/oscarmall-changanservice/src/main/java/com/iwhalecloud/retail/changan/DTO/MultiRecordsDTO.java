package com.iwhalecloud.retail.changan.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @auther add by wanghw on 2019/11/18
 * @return
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiRecordsDTO implements Serializable {
    /**
     * 企业ID.
     */
    private String enterpriseId;

    /**
     * 结果集.
     */
    private List multiRecords;


}
