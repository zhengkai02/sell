package com.iwhalecloud.retail.common.consts;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * redis缓存key定义
 *
 * @author hu.minghang
 * @date 2019/01/31
 */
@Component("CommonCacheKeyDef")
public class CacheKeyDef {
    private CacheKeyDef() { }

    /**
     * 项目名称
     */
    @Value("${project.name}")
    private String projectName;

    /**
     * 项目环境
     */
    @Value("${project.env}")
    private String projectEnv;

    /**
     * 项目版本
     */
    @Value("${project.version}")
    private String projectVer;

    /**
     * 微服务名称
     */
    @Value("${spring.application.name}")
    private String microserviceName;

    private static final String CACHE_MODULE = "oscar_mall";

    private static final String CACHE_DELIMITER = "_";

    private static final String CACHE_NAME_PREFIX = CACHE_MODULE + CACHE_DELIMITER;

    public static String GOODS_QTY;

    public static String GOODS;

    public static String MQ_RULE;

    public static String MQ_DEAL_QTY;


    private static final String COUPON_CACHE_MODULE = "coupon";

    private static final String COUPON_CACHE_DELIMITER = "_";

    private static final String COUPON_CACHE_NAME_PREFIX = COUPON_CACHE_MODULE + COUPON_CACHE_DELIMITER;

    public static String COUPON_QTY;

    public static String GOODS_DEAIL_ID_CACHE;

    public static String GOODS_DEAIL_CODE_CACHE;

    /**
     * 点赞数量 key = 踩or赞 + objtype + objid
     */
    public static String USEFUL_KEY;

    /**
     * 点赞数量 key = 踩or赞 + objtype + objid
     */
    public static String USELESS_KEY;

    public static String USER_INFO;

    public static String OSCAR_MALL_CHANNEL_ID;

    public static String OSCAR_MALL_USER_ID;

    /**
     * 长安token
     */
    public static String CHANGAN_RULE;

    public static String CHANGAN_ACCESS_TOLEN;

    @PostConstruct
    private void init() {
        String prefix = projectName + "_" + projectEnv + "_" + "oscar-service" + "_" + projectVer + "_";
        GOODS_QTY = prefix + CACHE_NAME_PREFIX + "offerstock";
        GOODS = prefix + CACHE_NAME_PREFIX + "offerspec";
        MQ_RULE = prefix + CACHE_NAME_PREFIX + "mqrule";
        MQ_DEAL_QTY = prefix + CACHE_NAME_PREFIX + "mqdealqty";
        COUPON_QTY =  prefix + COUPON_CACHE_NAME_PREFIX + "couponstock";
        GOODS_DEAIL_ID_CACHE = GOODS + "_id";
        GOODS_DEAIL_CODE_CACHE = GOODS + "_sn";
        USEFUL_KEY = prefix + CACHE_NAME_PREFIX + "_useful";
        USELESS_KEY = prefix + CACHE_NAME_PREFIX + "_useless";
        USER_INFO = prefix + CACHE_NAME_PREFIX + "user_info";

        OSCAR_MALL_CHANNEL_ID = prefix +  CACHE_NAME_PREFIX + "channel_id";
        OSCAR_MALL_USER_ID =  prefix +  CACHE_NAME_PREFIX + "user_id";

        CHANGAN_RULE = prefix + CACHE_NAME_PREFIX + "changan";

        CHANGAN_ACCESS_TOLEN = prefix +  CACHE_NAME_PREFIX + "access_token";
    }
}
