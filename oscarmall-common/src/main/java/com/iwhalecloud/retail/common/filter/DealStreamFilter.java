package com.iwhalecloud.retail.common.filter;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(filterName = "dealStreamFilter", urlPatterns = {"/*"})
public class DealStreamFilter implements Filter {
    /**
     * 销毁
     */
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            // 遇到post方法才对request进行包装
            String methodType = httpRequest.getMethod();
            // 上传文件时同样不进行包装
            String servletPath = httpRequest.getRequestURI().toString();
            if (!servletPath.contains("/offer/file") && !servletPath.contains("fastdfs/upload/file")) {
                requestWrapper = new BufferedServletRequestWrapper(
                        (HttpServletRequest) request);
            }
        }

        if (null == requestWrapper) {
            chain.doFilter(request, response);
        }
        else {
            chain.doFilter(requestWrapper, response);
        }

    }

    /**
     * 初始化
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
