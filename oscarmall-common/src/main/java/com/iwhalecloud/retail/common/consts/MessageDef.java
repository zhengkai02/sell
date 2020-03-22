package com.iwhalecloud.retail.common.consts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * MQ统一前缀 虚拟主机名称_项目名_环境名_微服务名称_QueueName/TopicName
 * Created by xh on 2019/3/15.
 */
@Component("MessageDef")
public class MessageDef {

    /**
     * 虚拟主机名称
     */
    @Value("${spring.rabbitmq.virtualHost}")
    private String vhostName;

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
     * 微服务名称,目前写死
     */
    @Value("${spring.application.name}")
    private String microserviceName;

    /**
     * 版本
     */
    @Value("${project.version}")
    private String version;

    /**
     * 订单日志同步EXCHANGE
     */
    public static String ORDER_LOG_SYNC_EXCHANGE;

    /**
     * 订单日志同步TOPIC
     */
    public static String ORDER_LOG_SYNC_TOPIC;
    /**
     * 订单日志同步QUEUE
     */
    public static String ORDER_LOG_SYNC_QUEUE;

    /**
     * 优惠券下订单TOPIC (浩鲸 -> 亚信)
     * 2019-03-29 和亚信对接按照文档修改为 OSCAR_BOSS_RECIVECOUPON
     */
    public static String COUPON_ORDER_RECEIVE_QUEUE;

    public static String COUPON_ORDER_RECEIVE_TOPIC;

    /**
     * 优惠券回退
     */
    public static String COUPON_REFUND_QUEUE;

    public static String COUPON_REFUND_TOPIC = "OSCAR_COUPON_REFUND";

    /**
     * 优惠券下订单TOPIC(亚信 -> 浩鲸)
     */
    public static String COUPON_ORDER_CALLBACK_QUEUE;

    public static String COUPON_ORDER_CALLBACK_TOPIC;


    /**
     * 商品下订单TOPIC(亚信 -> 浩鲸)
     */

    public static String OFFER_ORDER_CALLBACK_QUEUE;

    public static String ORDER_CONFIRM_QUEUE;

    public static String ORDER_CONFIRM_TOPIC;

    /**
     * 资源核销
     */
    public static String QRCODE_CONFIRM_QUEUE;

    /**
     * 发送消息
     */
    public static String NOTIFY_SEND_TOPIC;

    /*
     * * 取消订单TOPIC(活动中心 =>订单中心)
     */
    public static String CAMPAIGN_ORDER_CANCEL_TOPIC;

    /*
     * * 取消订单QUEUE(活动中心 =>订单中心)
     */
    public static String CAMPAIGN_ORDER_CANCEL_QUEUE;

    /**
     * 消息推送EXCHANGE
     */
    public static String NOTIFY_PUSH_EXCHANGE;

    /**
     * 消息推送TOPIC
     */
    public static String NOTIFY_PUSH_TOPIC;


    /*
     * * 完成订单TOPIC(活动中心 -> 订单中心)
     */
    public static String CAMPAIGN_ORDER_COMPLETE_TOPIC;

    public static String OSCAR_MALL_OPER_LOG_QUEUE;

    public static String OSCAR_MALL_OPER_LOG_TOPIC;

    public static String CAMPAIGN_ORDER_COMPLETE_QUEUE;

    /*
     * * 订单中心缴费成功TOPIC(订单中心 -> 活动中心)
     */
    public static String ORDER_CAMPAIGN_PAYMENT_SUCC_TOPIC;

    public static String ORDER_CAMPAIGN_PAYMENT_SUCC_QUEUE;

    public static String ORDER_CAMPAIGN_CANCEL_GRP_TOPIC;

    public static String ORDER_CAMPAIGN_CANCEL_GRP_QUEUE;


    /**
     * 站内信
     */
    public static String OSCAR_MALL_MAIL;

    /**
     * 流量开通通知
     */
    public static String OSCAR_MALL_ADDDATA;

    /**
     * 审批结果通知
     */
    public static String OSCAR_MALL_AUDITRESULT;

    /**
     * 经分中心接口-浏览和加入购物车
     */
    public static String OSCAR_BOSS_OPERATION_OTHER;

    /**
     * 经分中心接口-下订单
     */
    public static String OSCAR_BOSS_OPERATION_SUBMIT_ORDER;

    /**
     * 经分中心接口-资源核销
     */
    public static String OSCAR_BOSS_OPERATION_CONFIRM_RESOURCE;

    /**
     * 对车企推送的开通通知
     */
    public static String OSCAR_MALL_FLOW_OPEN_NOTIFY_QUEUE;

    @PostConstruct
    private void init() {
        String prefix = vhostName + "_" + projectName + "_" + projectEnv + "_" + "tsop" + "_" + version + "_";
        OFFER_ORDER_CALLBACK_QUEUE = prefix + "OSCAR_MALL_ADDORDER_QUEUE";
        ORDER_LOG_SYNC_EXCHANGE = prefix + "xxxx";
        ORDER_LOG_SYNC_TOPIC = prefix + "OSCAR_BOSS_MALL_ORDER_LOG_RECEIVE_TOPIC";
        ORDER_LOG_SYNC_QUEUE = prefix + "OSCAR_BOSS_MALL_ORDER_LOG_RECEIVE";
        COUPON_ORDER_RECEIVE_QUEUE = prefix + "OSCAR_BOSS_RECIVECOUPON_QUEUE";
        COUPON_ORDER_RECEIVE_TOPIC = prefix + "OSCAR_BOSS_RECIVECOUPON";
        COUPON_REFUND_QUEUE = prefix + "OSCAR_COUPON_REFUND_QUEUE";
        COUPON_REFUND_TOPIC = prefix + "OSCAR_COUPON_REFUND";
        COUPON_ORDER_CALLBACK_QUEUE = prefix + "OSCAR_MALL_ADDCOUPON_QUEUE";
        COUPON_ORDER_CALLBACK_TOPIC = prefix + "OSCAR_MALL_ADDCOUPON";
        ORDER_CONFIRM_QUEUE = prefix + "OSCAR_MALL_CONFIRM_ORDER_QUEUE";
        ORDER_CONFIRM_TOPIC = prefix + "OSCAR_BOSS_ORDER_CONFIRM";
        QRCODE_CONFIRM_QUEUE = prefix + "OSCAR_MALL_CONFIRMQRCODE";
        NOTIFY_SEND_TOPIC = prefix + "OSCAR_MALL_NOTIFY_SEND";
        CAMPAIGN_ORDER_CANCEL_TOPIC = prefix + "OSCAR_MALL_CANCELORDER4GRP";
        CAMPAIGN_ORDER_CANCEL_QUEUE = prefix + "OSCAR_MALL_CANCELORDER4GRP_QUEUE";
        NOTIFY_PUSH_EXCHANGE = prefix + "OSCAR_MALL_NOTIFY_PUSH_EXCHANGE";
        NOTIFY_PUSH_TOPIC = prefix + "OSCAR_BOSS_MALL_NOTIFY_PUSH_TOPIC";
        CAMPAIGN_ORDER_COMPLETE_TOPIC = prefix + "OSCAR_MALL_GRPSUCC";
        OSCAR_MALL_OPER_LOG_QUEUE = prefix + "OSCAR_MALL_OPER_LOG_QUEUE";
        OSCAR_MALL_OPER_LOG_TOPIC = prefix + "OSCAR_MALL_OPER_LOG_TOPIC";
        CAMPAIGN_ORDER_COMPLETE_QUEUE = prefix + "OSCAR_MALL_GRPSUCC_QUEUE";
        ORDER_CAMPAIGN_PAYMENT_SUCC_TOPIC = prefix + "OSCAR_MALL_GRPSUCC4ORDER";
        ORDER_CAMPAIGN_PAYMENT_SUCC_QUEUE = prefix + "OSCAR_MALL_GRPSUCC4ORDER_QUEUE";
        ORDER_CAMPAIGN_CANCEL_GRP_TOPIC = prefix + "OSCAR_MALL_CANCELGRP4ORDER";
        ORDER_CAMPAIGN_CANCEL_GRP_QUEUE = prefix + "OSCAR_MALL_CANCELGRP4ORDER_QUEUE";
        OSCAR_MALL_MAIL = prefix + "OSCAR_MALL_MAIL_QUEUE";
        OSCAR_MALL_ADDDATA = prefix + "OSCAR_MALL_ADDDATA_QUEUE";
        OSCAR_MALL_AUDITRESULT = prefix + "OSCAR_MALL_AUDITRESULT_QUEUE";
        OSCAR_BOSS_OPERATION_OTHER = prefix + "OSCAR_BOSS_OPERATION_OTHER_QUEUE";
        OSCAR_BOSS_OPERATION_SUBMIT_ORDER = prefix + "OSCAR_BOSS_OPERATION_SUBMIT_ORDER_QUEUE";
        OSCAR_BOSS_OPERATION_CONFIRM_RESOURCE = prefix + "OSCAR_BOSS_OPERATION_CONFIRM_RESOURCE_QUEUE";
        OSCAR_MALL_FLOW_OPEN_NOTIFY_QUEUE = prefix + "OSCAR_MALL_FLOW_OPEN_NOTIFY_QUEUE";
    }
}

