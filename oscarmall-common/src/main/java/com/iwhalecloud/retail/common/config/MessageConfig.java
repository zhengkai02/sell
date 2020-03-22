package com.iwhalecloud.retail.common.config;

import com.iwhalecloud.retail.common.consts.MessageDef;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/18 <br>
 * @see com.iwhalecloud.retail.common.config <br>
 * @since IOT <br>
 */
@Configuration
@DependsOn("MessageDef")
public class MessageConfig {

    @Bean("couponCollectMsg")
    public Queue couponCollectMsg() {
        return new Queue(MessageDef.COUPON_ORDER_RECEIVE_QUEUE);
    }

    @Bean("couponCallbackMsg")
    public Queue couponCallbackMsg() {
        return new Queue(MessageDef.COUPON_ORDER_CALLBACK_QUEUE);
    }

    @Bean("orderLogMsg")
    public Queue orderQueueMsg() {
        return new Queue(MessageDef.ORDER_LOG_SYNC_QUEUE);
    }

    @Bean("couponRefundMsg")
    public Queue couponRefundMsg() {
        return new Queue(MessageDef.COUPON_REFUND_QUEUE);
    }

    @Bean("submitOrderMsg")
    public Queue offerRefundMsg() {
        return new Queue(MessageDef.OFFER_ORDER_CALLBACK_QUEUE);
    }

    @Bean("orderConfirmMsg")
    public Queue orderConfirmQueue() {
        return new Queue(MessageDef.ORDER_CONFIRM_QUEUE);
    }

    @Bean("submitcouponMsg")
    public Queue submitcouponMsg() {
        return new Queue(MessageDef.COUPON_ORDER_CALLBACK_TOPIC);
    }

    @Bean("sendNotifyMsg")
    public Queue sendNotifyMsg() {
        return new Queue(MessageDef.NOTIFY_SEND_TOPIC);
    }

    /**
     * 站内信通知
     * 
     * @return Queue
     */
    @Bean("oscarMallMailQueue")
    public Queue oscarMallMail() {
        return new Queue(MessageDef.OSCAR_MALL_MAIL);
    }

    /**
     * 流量开通通知
     * 
     * @return Queue
     */
    @Bean("oscarMallAdddata")
    public Queue oscarMallAdddata() {
        return new Queue(MessageDef.OSCAR_MALL_ADDDATA);
    }

    /**
     * 审批结果通知
     * 
     * @return Queue
     */
    @Bean("oscarMallAuditResult")
    public Queue oscarMallAuditResult() {
        return new Queue(MessageDef.OSCAR_MALL_AUDITRESULT);
    }

    /**
     * 经分中心接口-浏览和加入购物车
     * 
     * @return Queue
     */
    @Bean("oscarBossOperationOther")
    public Queue oscarBossOperationOther() {
        return new Queue(MessageDef.OSCAR_BOSS_OPERATION_OTHER);
    }

    /**
     * 经分中心接口-浏览和加入购物车
     * 
     * @return Binding
     */
    @Bean
    public Binding bindingExchangeOscarBossOperationOther(@Qualifier("oscarBossOperationOther") Queue queueMessage,
        TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.OSCAR_BOSS_OPERATION_OTHER);
    }

    /**
     * 经分中心接口-下订单
     * 
     * @return Queue
     */
    @Bean("oscarBossOperationSubmitOrder")
    public Queue oscarBossOperationSubmitOrder() {
        return new Queue(MessageDef.OSCAR_BOSS_OPERATION_SUBMIT_ORDER);
    }

    /**
     * 经分中心接口-下订单
     * 
     * @return Binding
     */
    @Bean
    public Binding bindingExchangeOscarBossOperationSubmitOrder(
        @Qualifier("oscarBossOperationSubmitOrder") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.OSCAR_BOSS_OPERATION_SUBMIT_ORDER);
    }

    /**
     * 经分中心接口-资源核销
     * 
     * @return Queue
     */
    @Bean("oscarBossOperationConfirmResource")
    public Queue oscarBossOperationConfirmResource() {
        return new Queue(MessageDef.OSCAR_BOSS_OPERATION_CONFIRM_RESOURCE);
    }

    /**
     * 经分中心接口-资源核销
     * 
     * @return Binding
     */
    @Bean
    public Binding bindingExchangeOscarBossOperationConfirmResource(
        @Qualifier("oscarBossOperationConfirmResource") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.OSCAR_BOSS_OPERATION_CONFIRM_RESOURCE);
    }

    @Bean("qrCodeConfirmQueue")
    public Queue qrCodeConfirmQueue() {
        return new Queue(MessageDef.QRCODE_CONFIRM_QUEUE);
    }

    @Bean("campaignOrderCancelMsg")
    public Queue campaignOrderCancelMsgMsg() {
        return new Queue(MessageDef.CAMPAIGN_ORDER_CANCEL_QUEUE);
    }

    @Bean("operLogMsg")
    public Queue operLogMsg() {
        return new Queue(MessageDef.OSCAR_MALL_OPER_LOG_QUEUE);
    }

    @Bean("notifyPushMsg")
    public Queue notifyPushMsg() {
        return new Queue(MessageDef.NOTIFY_PUSH_TOPIC);
    }

    @Bean("order2CampaignSuccQueue")
    public Queue orderToCampaignSuccQueue() {
        return new Queue(MessageDef.ORDER_CAMPAIGN_PAYMENT_SUCC_QUEUE);
    }

    @Bean("campaign2OrderCompleteQueue")
    public Queue campaignToOrderCompleteQueue() {
        return new Queue(MessageDef.CAMPAIGN_ORDER_COMPLETE_QUEUE);
    }

    @Bean("order2CampaignCancelGrpQueue")
    public Queue order2CampaignCancelGrpQueue() {
        return new Queue(MessageDef.ORDER_CAMPAIGN_CANCEL_GRP_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(MessageDef.ORDER_LOG_SYNC_EXCHANGE);
    }

    @Bean
    public FanoutExchange notifyExchange() {
        return new FanoutExchange(MessageDef.NOTIFY_PUSH_EXCHANGE);
    }

    @Bean
    public Binding bindingExchangeNotifyPushMsg(@Qualifier("notifyPushMsg") Queue queueMessage,
        FanoutExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange);
    }

    @Bean
    public Binding bindingExchangeCouponCollectMsg(@Qualifier("couponCollectMsg") Queue queueMessage,
        TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.COUPON_ORDER_RECEIVE_TOPIC);
    }

    @Bean
    public Binding bindingExchangeCouponCallBackMsg(@Qualifier("couponCallbackMsg") Queue queueMessage,
        TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.COUPON_ORDER_CALLBACK_TOPIC);
    }

    @Bean
    public Binding bindingExchangeCouponRefundMsg(@Qualifier("couponRefundMsg") Queue queueMessage,
        TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.COUPON_REFUND_TOPIC);
    }

    @Bean
    public Binding bindingExchangeOrderLogMsgMsg(@Qualifier("orderLogMsg") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.ORDER_LOG_SYNC_TOPIC);
    }

    @Bean
    public Binding bindingExchangeOrderConfirmMsg(@Qualifier("orderConfirmMsg") Queue queueMessage,
        TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.ORDER_CONFIRM_TOPIC);
    }

    @Bean
    public Binding bindingExchangeSendNotifyMsg(@Qualifier("sendNotifyMsg") Queue queueMessage,
        TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.NOTIFY_SEND_TOPIC);
    }

    @Bean
    public Binding bindingExchangeOrderCancelMsg(@Qualifier("campaignOrderCancelMsg") Queue queueMessage,
        TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.CAMPAIGN_ORDER_CANCEL_TOPIC);
    }

    @Bean
    public Binding bindingExchangeOperLogMsg(@Qualifier("operLogMsg") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.OSCAR_MALL_OPER_LOG_TOPIC);
    }

    @Bean
    public Binding bindingExchangeOrder2CampaignSuccMsg(@Qualifier("order2CampaignSuccQueue") Queue queueMessage,
        TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.ORDER_CAMPAIGN_PAYMENT_SUCC_TOPIC);
    }

    @Bean
    public Binding bindingExchangeCampaign2OrderSuccMsg(@Qualifier("campaign2OrderCompleteQueue") Queue queueMessage,
        TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.CAMPAIGN_ORDER_COMPLETE_TOPIC);
    }

    @Bean
    public Binding bindingExchangeOrder2CampaignCancelGrpMsg(
        @Qualifier("order2CampaignCancelGrpQueue") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MessageDef.ORDER_CAMPAIGN_CANCEL_GRP_TOPIC);
    }

}
