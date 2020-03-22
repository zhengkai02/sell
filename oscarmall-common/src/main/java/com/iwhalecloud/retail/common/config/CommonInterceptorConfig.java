package com.iwhalecloud.retail.common.config;

import com.iwhalecloud.retail.common.interceptor.CommonInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/11 <br>
 * @see com.iwhalecloud.retail.mobile.config <br>
 * @since IOT <br>
 */
@Configuration
public class CommonInterceptorConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor())
                .excludePathPatterns("/**/mall/**")
                .excludePathPatterns("/**/washcar/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/**/login")
                .excludePathPatterns("/**/logout")
                .excludePathPatterns("/**/verifycode")
                // 定制中心
                .excludePathPatterns("/**/custom/**")
                .excludePathPatterns("/**/order/jiangsu/**")
                .excludePathPatterns("/**/db/controller/queryDB")
                .excludePathPatterns("/**/db/controller/updateDB")
                // job任务
                .excludePathPatterns("/**/operation/wechat/job/send")
                .excludePathPatterns("/**/campaign/fission_grp_expire")
                .excludePathPatterns("/**/order/order_pay_expire")
                .excludePathPatterns("/**/order/state/expdate")
                .excludePathPatterns("/**/offer/auto_shelf")
                .excludePathPatterns("/**/offer/autosaveqty")
                .excludePathPatterns("/**/offer/autoevaluationlog")
                .excludePathPatterns("/**/offer/autoupdategoodsstate")
                // MQ接口
                .excludePathPatterns("/**/offer/messagerule/**")
                .excludePathPatterns("/**/offer/errormessage/**")
                .excludePathPatterns("/**/order/messagetransactionlog/check")
                .excludePathPatterns("/**/order/messagetransactionlog/insert")
                // fastdfs
                .excludePathPatterns("/**/oscar/offer/file/**")
                .excludePathPatterns("/**/oscar/fastdfs/upload/file/**")
                // 商城模板
                .excludePathPatterns("/**/content/shopTemplate/qry")
                // 文章
                .excludePathPatterns("/**/content/contentBase/queryContentDeatil")
                .excludePathPatterns("/**/content/contentBase/viewCount")
                .excludePathPatterns("/**/offer/evaluation/query")
                .excludePathPatterns("/**/content/contentBase/qryList")
                .excludePathPatterns("/**/content/contentBase/auto/update/state")
                // 审批MQ
                .excludePathPatterns("/**/offer/audit")
                .excludePathPatterns("/**/content/contentBase/audit")
                .excludePathPatterns("/**/coupon/audit")
                .excludePathPatterns("/**/operation/notifycontent/audit")
                // 经分中心资源核销
                .excludePathPatterns("/**/order/item/res")
                // 支付成功异步回调
                .excludePathPatterns("/**/order/asynpaynotify")
                .excludePathPatterns("/**/offer/prodgoods/addbuycount")
                // 发票类目同步
                .excludePathPatterns("/**/offer/invoicecatg")
                .excludePathPatterns("/**/order/invoice/asyninvoicenotify")
                .excludePathPatterns("/**/configuration/ui")
                .excludePathPatterns("/**/swagger-resources")
                .excludePathPatterns("/**/v2/api-docs")
                .excludePathPatterns("/**/configuration/security")
                // 长安接口
                .excludePathPatterns("/**/huservice/api/service/**");
        super.addInterceptors(registry);
    }


    @Bean
    public CommonInterceptor commonInterceptor() {
        return new CommonInterceptor();
    }
}
