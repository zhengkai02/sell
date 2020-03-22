package indi.zk.mall.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限拦截，区分买家和卖家
 * @author ZhengKai
 * @data 2020-03-21 21:03
 */
@Component
public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        /**
         * /order/create 只能买家访问
         * /order/finish 只能卖家访问
         * /product/list/ 都可以访问
         */
        return null;
    }
}
