package com.iwhalecloud.retail.common.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * Created by xh on 2019/2/4.
 */
public final class UidGeneator {
    private UidGeneator() { }

    public static Long getUID() {
        return IdWorker.getId();
    }

    public static String getUIDStr() {
        return IdWorker.getIdStr();
    }
}
