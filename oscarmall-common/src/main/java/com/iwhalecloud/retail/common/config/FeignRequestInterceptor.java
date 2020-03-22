package com.iwhalecloud.retail.common.config;

import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xh on 2019/6/3.
 */
@Slf4j
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        log.info("FeignRequestInterceptor apply start");

        UserDTO user = HttpSessionUtil.get();
        log.info("FeignRequestInterceptor apply user = [{}]", user);
        // 请求唯一id
        template.header("requestId", UidGeneator.getUIDStr());
        if (user != null) {
            if (null != user.getUserId()) {
                template.header("operatorId", user.getUserId().toString());
            }
            if (StringUtils.isNotEmpty(user.getTenantId())) {
                template.header("eId", user.getTenantId());
            }
            if (StringUtils.isNotEmpty(user.getStoreId())) {
                template.header("storeId", user.getStoreId());
            }
            if (null != user.getChannelType()) {
                template.header("channelType", user.getChannelType().toString());
            }
            if (null != user.getOrgId()) {
                template.header("orgId", user.getOrgId());
            }
            template.header("language", user.getLanguage());
        }

        log.info("FeignRequestInterceptor apply end");
    }
}
