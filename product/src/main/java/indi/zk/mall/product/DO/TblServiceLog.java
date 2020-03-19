package indi.zk.mall.product.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author ZhengKai
 * @data 2019-12-19 21:09
 */
@Data
@Entity
public class TblServiceLog {

    @Id
    private String logId;

    /** 请求主机IP. */
    private String ip;

    /** 请求流水. */
    private String requestId;

    /** 微服务名称. */
    private String 	microserviceName;

    /** 响应码. */
    private String systemCode;

    /** 类名. */
    private String className;

    /** 请求路径. */
    private String requestPath;

    /** 接口名. */
    private String interfaceName;

    /** 请求参数. */
    private String requestMsg;

    /** 响应参数. */
    private String responseMsg;

    /** 接口调用时间. */
    private long costTime;

    /** 渠道. */
    private String contactChannelId;

    /** 租户ID/企业ID. */
    private String tenantId;

    private String createBy;

    private String createTime;

    private String modifyBy;

    private String modifyTime;

    private long id;

}
