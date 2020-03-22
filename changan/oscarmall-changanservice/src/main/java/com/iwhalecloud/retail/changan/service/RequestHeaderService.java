package com.iwhalecloud.retail.changan.service;

import java.util.Map;

/**
 * @author ZhengKai
 * @data 2019-11-11 14:12
 */
public interface RequestHeaderService {
    // 从redis缓存中获取最新的token, 包装请求头信息.
    Map<String, String> getHeader();
}
