package com.iwhalecloud.retail.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.common.consts.CommonMessageEnumEnMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@WebFilter(filterName = "commonFilter", urlPatterns = {"/oscar/*", "/order/*", "/content/*", "/coupon/*", "/campaign/*", "/offer/*", "/operation/*"})
public class CommonFilter implements Filter {

    public static final String LANGUAGE = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("WebFilterUtil start ");
        HttpServletRequest req = (HttpServletRequest) request;
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);

        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
        responseWrapper.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的访问方法
        responseWrapper.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        responseWrapper.setHeader("Access-Control-Max-Age", "3600");
        responseWrapper.setHeader("Access-Control-Allow-Headers", "token,Origin, X-Requested-With, Content-Type, Accept");


        responseWrapper.setCharacterEncoding(LANGUAGE);
        responseWrapper.setContentType("application/json; charset=utf-8");
        String reqUrl  = req.getRequestURL().toString();
        String language = req.getHeader("language");
        String tenantId = req.getHeader("tenant_id");
        if (StringUtils.isEmpty(tenantId)) {
            tenantId = req.getHeader("eId");
        }
        String userId = req.getHeader("user_id");
        if (StringUtils.isEmpty(userId)) {
            userId = req.getHeader("operatorId");
        }
        log.info("WebFilterUtil reqUrl: " + reqUrl);
        log.info("WebFilterUtil language：" + language + ",tenantId: " + tenantId + ",userId: " + userId);
        chain.doFilter(request, responseWrapper);
        //获取到 response 里数据
        byte[] content = responseWrapper.getResponseData();
        log.info("WebFilterUtil response content length : " + content.length);
        if (content.length > 0) {
            ServletOutputStream out = response.getOutputStream();
            //验证码请求
            if (reqUrl.contains("verifycode") || reqUrl.contains("downloadinvoice")) {
                //验证码为图片格式，直接输出
                out.write(content);
            }
            else {
                //转换为汉字
                String str = new String(content, LANGUAGE);
                //用户语言为 en （英语）且 返回数据里有包含code（异常信息返回有 code）才做国际化
                if ("en".equals(language) && str.contains("code")) {
                    // 将json字符串转换为map
                    Map<String, String> map = (Map) JSONObject.parseObject(str);
                    // 是否能取到code的值
                    ifCode(map);
                    out.write(map.toString().getBytes(LANGUAGE));
                }
                else {
                    //没有包含code默认请求成功，直接输出
                    out.write(str.getBytes(LANGUAGE));
                }
                out.flush();
                out.close();
            }
        }
        log.info("WebFilterUtil end ");
    }

    private void ifCode(Map<String, String> map) {
        String respCode = map.get("code");
        if (StringUtils.isNotEmpty(respCode)) {
            //如果中文异常信息在英文异常信息里没有取到值，不做转换
            String enMessage = CommonMessageEnumEnMap.enMap.get(map.get("message"));
            if (StringUtils.isNotEmpty(enMessage)) {
                map.put("message", "responseCode: " + respCode + ",responseMessage: " + enMessage);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
