package indi.zk.mall.apigateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author ZhengKai
 * @data 2019-12-10 10:46
 */
@Component
@Slf4j
public class LimitFilter extends ZuulFilter {

    @Value("${oldURI}")
    private String oldURI;

    @Value("${newURI}")
    private String newURI;

    private static final String REQUEST_URI="requestURI";

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SERVLET_DETECTION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String requestURI = ((String)requestContext.get(REQUEST_URI)).replace(oldURI, newURI);
        requestContext.put(REQUEST_URI,requestURI);
        return null;
    }
}
