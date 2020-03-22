package com.iwhalecloud.retail.common.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class IPUtil {

    public static final String UNKNOWN = "unknown";

    private IPUtil() {

    }

    public static String getServerIpAddr() {
        String ip = "";
        try {
            ip = normalizeHostAddress(InetAddress.getLocalHost());
        }
        catch (UnknownHostException e) {
            e.getMessage();
        }
        return ip;

    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String localIp = System.getProperty("myapplication.ip");
        try {
            ip = ipIndex(ip);
            ip = ipIfNull(request, ip);
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();

                if (localIp.equals(ip) || ("0:0:0:0:0:0:0:1").equals(ip)) {
                    InetAddress addr = InetAddress.getLocalHost();
                    ip = normalizeHostAddress(addr);
                }
            }
        }
        catch (Exception e) {
            e.getMessage();
        }
        return ip;
    }

    private static String ipIfNull(HttpServletRequest request, String ip) {
        String str = ip;
        if (str == null || str.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            str = request.getHeader("Proxy-Client-IP");
        }
        if (str == null || str.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            str = request.getHeader("WL-Proxy-Client-IP");
        }
        if (str == null || str.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            str = request.getHeader("HTTP_CLIENT_IP");
        }
        if (str == null || str.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            str = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        return str;
    }

    private static String ipIndex(String ip) {
        boolean exists = (!StringUtils.isEmpty(ip) && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) && (ip.indexOf(',') != -1);
        String str = ip;
        if (exists) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                str = ip.split(",")[0];
        }
        return str;
    }

    private static String normalizeHostAddress(final InetAddress localHost) {
        if (localHost instanceof Inet6Address) {
            return "[" + localHost.getHostAddress() + "]";
        }
        else {
            return localHost.getHostAddress();
        }
    }
}
