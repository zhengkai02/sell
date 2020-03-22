package com.iwhalecloud.retail.common.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xh on 2019/6/3.
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 不带租户的表
     */
    private static final List<String> FILTER_TABLES = Arrays.asList(
            "tbl_system_param", "tbl_domain", "tbl_channel_type", "tbl_def_lang", "tbl_sys_regions",
            "tbl_oper_detail", "tbl_oper_log", "tbl_oper_log_event", "tbl_order_reason",
            "tbl_mq_error_message", "tbl_guard_message_rule", "tbl_message_transaction_log",
            "tbl_prod_goods_type", "tbl_prod_goods_sales_rule", "tbl_prod_goods_rel_type", "tbl_goods_content_type",
            "tbl_payment_method", "tbl_payment_type", "tbl_currency", "tbl_memo_event_type",
            "tbl_order_type", "tbl_ord_order_state", "tbl_ord_order_rela_type", "tbl_ord_comments",
            "tbl_price_factor", "tbl_store_rule", "tbl_cn_area", "tbl_prod_product", "tbl_prod_product_sale_record",
            "tbl_shop_template_theme", "tbl_shop_panel_type", "tbl_prod_res_rel", "tbl_url_template", "tbl_url_lib",
            "tbl_notify_type", "tbl_campaign_type", "tbl_coupon_type", "tbl_coupon_spec_rule", "tbl_coupon_type_rela",
            "tbl_operating_position_bind", "tbl_shop_page",
            "tbl_car_brand", "tbl_car_series", "tbl_car_spec",
            "tbl_tenant", "tbl_employee",
            "tbl_resource_type", "tbl_resource_record", "tbl_prod_product", "tbl_prod_product_sale_record",
            "tbl_prod_res_rel", "tbl_user", "tbl_shop_page", "tbl_content_img_spec", "tbl_user_res");
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page =  new PaginationInterceptor();

        List<ISqlParser> sqlParserList = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {

            @Override
            public Expression getTenantId() {
                UserDTO user = HttpSessionUtil.get();
                if (user != null) {
                    return new StringValue(user.getTenantId());
                }
                return null;

            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                int exists;
                if (FILTER_TABLES.contains(tableName)) {
                    exists = 0;
                }
                else {
                    exists = 1;
                }
                return exists == 0;
            }
        });

        sqlParserList.add(tenantSqlParser);
        page.setSqlParserList(sqlParserList);
        page.setSqlParserFilter(metaObject -> {
            UserDTO user = HttpSessionUtil.get();
            if (user != null && user.getIsPlatformUser() != null && user.getIsPlatformUser()) {
                return true;
            }
            MappedStatement mappedStatement = SqlParserHelper.getMappedStatement(metaObject);
            String methodName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf('.') + 1);
            Method method = getMethod(mappedStatement);
            return "selectById".equals(methodName) || (method != null && method.isAnnotationPresent(DisableTenantParam.class));
        });
        return page;
    }

    @Bean
    public OracleKeyGenerator oracleKeyGenerator() {
        return new OracleKeyGenerator();
    }


    public static Method getMethod(MappedStatement mappedStatement)   {
        String msId = mappedStatement.getId();

        String methodName = msId.substring(msId.lastIndexOf('.') + 1);

        String namespace = msId.substring(0, msId.lastIndexOf('.'));
        Class<?> clazz = null;
        try {
            clazz = ClassUtils.forName(namespace, (ClassLoader) null);
        }
        catch (ClassNotFoundException e) {
            return null;
        }

        return getMapperMethod(clazz, methodName);

    }

    public static Method getMapperMethod(Class<?> clazz, String methodName) {
        Method[] methods = clazz.getMethods();
        Method[] var3 = methods;
        int var4 = methods.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            if (methodName.equals(method.getName())) {
                return method;
            }
        }
        return null;
    }
}
