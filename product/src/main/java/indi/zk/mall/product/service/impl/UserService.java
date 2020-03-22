package indi.zk.mall.product.service.impl;

import indi.zk.mall.product.consts.NoticeConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author ZhengKai
 * @data 2019-12-06 13:14
 */
@Service
@Slf4j
public class UserService {

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        log.trace("当前IP来源[X-Forwarded-For], 值[{}]", ip);
        if (!StringUtils.isEmpty(ip) && !NoticeConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        log.trace("当前IP来源[X-Real-IP], 值[{}]", ip);
        if (!StringUtils.isEmpty(ip) && !NoticeConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        if (StringUtils.isEmpty(ip) || NoticeConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.trace("当前IP来源[Proxy-Client-IP], 值[{}]", ip);
        }
        if (StringUtils.isEmpty(ip) || NoticeConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.trace("当前IP来源[WL-Proxy-Client-IP], 值[{}]", ip);
        }
        if (StringUtils.isEmpty(ip) || NoticeConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.trace("当前IP来源[HTTP_CLIENT_IP], 值[{}]", ip);
        }
        if (StringUtils.isEmpty(ip) || NoticeConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.trace("当前IP来源[HTTP_X_FORWARDED_FOR], 值[{}]", ip);
        }
        if (StringUtils.isEmpty(ip) || NoticeConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.trace("当前IP来源[getRemoteAddr], 值[{}]", ip);
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            String ipv4FromLocal = getIpv4FromLocal();
            if (StringUtils.isNotEmpty(ipv4FromLocal)) {
                ip = ipv4FromLocal;
            }
        }
        return ip;
    }

    /**
     * 获取本地IP地址     * @return IP地址
     */
    private static String getIpv4FromLocal() {
        String ip = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            Process process = Runtime.getRuntime().exec("cmd.exe /c ipconfig | findstr IPv4");
            is = process.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line = br.readLine();
            ip = line.substring(line.indexOf(':') + 1).trim();
        } catch (IOException e) {
            log.warn("获取本地IP异常", e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.debug("流关闭异常", e);
            }
        }
        return ip;
    }
}
