package com.iwhalecloud.retail.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.iwhalecloud.retail.common.consts.ChannelTypeDef;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.DateUtil;
import com.iwhalecloud.retail.common.utils.GetRequestJsonUtils;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.IPUtil;
import com.iwhalecloud.retail.common.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by xh on 2019/6/3.
 */
@Slf4j
public class CommonInterceptor implements HandlerInterceptor {

    /**
     * operationLog
     */
    private static final Logger visitLog = LoggerFactory.getLogger("manageVisit");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("CommonInterceptor preHandle start");

        //H5商城过来 用户id不存在
        String channelType = request.getHeader("channelType");
        String tenantId = request.getHeader("eId");
        String userId = request.getHeader("operatorId");
        String orgId = request.getHeader("orgId");
        String isPlatformUser = request.getHeader("isPlatformUser");

        UserDTO tmpUserDTO = new UserDTO();
        //H5和管理后台的用户不是同一种用户  H5过来的请求,不需要登陆后台管理系统
        if (!ChannelTypeDef.H5.equals(channelType) && !ChannelTypeDef.CAR.equals(channelType)) {
            if (StringUtils.isEmpty(userId)) {
                throw new BaseException(CommonBaseMessageCodeEnum.USER_ID_IS_NULL);
            }
            tmpUserDTO.setOrgId(orgId);
            tmpUserDTO.setUserId(Long.valueOf(userId));
            tmpUserDTO.setStoreId(request.getHeader("storeId"));
            tmpUserDTO.setLanguage(request.getHeader("language"));
        }

        if ("true".equalsIgnoreCase(isPlatformUser)) {
            tmpUserDTO.setIsPlatformUser(Boolean.TRUE);
        }

        log.info("CommonInterceptor preHandle channelType = [{}], tenantId = [{}], userId = [{}], orgId = [{}], isPlatformUser = [{}]",
                channelType, tenantId, userId, orgId, isPlatformUser);
        tmpUserDTO.setTenantId(tenantId);
        if (StringUtils.isNotEmpty(channelType)) {
            tmpUserDTO.setChannelType(Long.valueOf(channelType));
        }

        HttpSessionUtil.set(tmpUserDTO);
        logVisit(request, userId);

        log.info("CommonInterceptor preHandle end, tmpUserDTO = [{}]", tmpUserDTO);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws UnsupportedOperationException {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HttpSessionUtil.remove();
    }

    /**
     * 记录从H5过来的操作日志
     * sessionId
     * path  访问路径
     *
     * @param request
     */
    private void logVisit(HttpServletRequest request, String userId) {
        try {
            //获取请求的sessionId
            String sessionId = SessionUtil.getSessionId(request);
            //请求路径
            String path = request.getRequestURI();
            //请求方法
            String method = request.getMethod();
            //获取请求参数信息
            String paramData = JSON.toJSONString(request.getParameterMap(), SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);

            //获取body中的参数
            String bodyParam = GetRequestJsonUtils.getBodyString(request);

            if (StringUtils.isEmpty(bodyParam)) {
                bodyParam = new JSONObject().toJSONString();
            }
            else {
                bodyParam = JSON.toJSON(bodyParam).toString();
            }

            //请求的Ip
            String clientIp = IPUtil.getClientIpAddr(request);
            //请求时间
            Long timestamp = System.currentTimeMillis();
            String logTime = DateUtil.formatString(new Date(timestamp), DateUtil.DATETIME_FORMAT_1);
            
            // sessionId | clientIP | timestamp | logTime | userId | phone | userType | path | method | paramData
            visitLog.info("{},{},{},{},{},{},{},{},{},{}, {}", sessionId, clientIp, timestamp, logTime, userId, "", "B", path, method, paramData, bodyParam);
        }
        catch (Exception e) {
            log.error("error visitLog. error={}", e);
        }
    }
}
