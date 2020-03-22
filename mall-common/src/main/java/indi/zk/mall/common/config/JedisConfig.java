package com.iwhalecloud.retail.common.config;

import com.iwhalecloud.retail.common.cache.redis.RedisCache;
import com.iwhalecloud.retail.common.cache.redis.RedisClusterCache;
import com.iwhalecloud.retail.common.properties.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class JedisConfig {

    private static final int MAX_ID = 200;

    private static final int MAX_TOTAL = 300;

    private static final int POOL_NUM = 10000;

    @Autowired
    RedisProperties redisProperties;

    @Bean
    @ConditionalOnProperty(prefix = "spring.redis.cluster", value = "enabled", matchIfMissing = true,
        havingValue = "false")
    public JedisPool defaultJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(MAX_ID);
        jedisPoolConfig.setMaxTotal(MAX_TOTAL);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestOnReturn(false);
        JedisPool jedisPool = null;
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(), POOL_NUM,
                redisProperties.getPassword(), 0);
        }
        else {
            jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(), POOL_NUM);
        }

        log.info("JedisPool注入成功！");
        log.info("redis地址：" + redisProperties.getHost() + ":" + redisProperties.getPort());
        return jedisPool;
    }

    @Bean
    @ConditionalOnBean(name = "defaultJedisPool")
    public RedisCache getRedisCache(@Qualifier("defaultJedisPool") JedisPool jedisPool) {
        return new RedisCache(jedisPool);
    }

    /**
     * 注意： 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
     * 
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.redis.cluster", value = "enabled", matchIfMissing = false,
        havingValue = "true")
    public JedisCluster defaultJedisCluster() {
        String[] serverArray = StringUtils.commaDelimitedListToStringArray(redisProperties.getCluster().getNodes());
        Set<HostAndPort> nodes = new HashSet<>();

        for (String ipPort : serverArray) {
            String[] ipPortPair = StringUtils.delimitedListToStringArray(ipPort, ":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }

        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        // 最大空闲数
        genericObjectPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        // 连接池的最大数据库连接数
        genericObjectPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
        // 最大建立链接等待时间
        genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMilli());
        // 每次逐出检查时 逐出的最大数目 如果为附属就是 ：1/abs（n）,默认3
        genericObjectPoolConfig.setNumTestsPerEvictionRun(redisProperties.getNumTestsPerEvictionRun());
        // 逐出扫描的时间间隔（毫秒）如果为负数，则不运行逐出线程，默认-1
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(redisProperties.getTimeBetweenEvictionRunsMillis());
        // 是否在从池中去除连接前进行检验，如果检验失败，则从池中去除连接并尝试取出另一个
        genericObjectPoolConfig.setTestOnBorrow(redisProperties.isTestOnBorrow());
        // 在空闲时检查有效性，默认false
        genericObjectPoolConfig.setTestWhileIdle(redisProperties.isTestWhileIdle());
        // 设置最小连接数
        genericObjectPoolConfig.setMinIdle(redisProperties.getMinIdle());
        // 向资源归还连接是否做有效性检查
        genericObjectPoolConfig.setTestOnReturn(redisProperties.isTestOnReturn());
        // 资源池中资源最小空闲时间
        genericObjectPoolConfig.setMinEvictableIdleTimeMillis(redisProperties.getMinEvictableIdleTimeMillis());
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            return new JedisCluster(nodes, redisProperties.getConnectionTimeout(), redisProperties.getSoTimeout(), 1,
                redisProperties.getPassword(), new GenericObjectPoolConfig());
        }
        else {
            return new JedisCluster(nodes, redisProperties.getConnectionTimeout(), redisProperties.getSoTimeout(), 1,
                new GenericObjectPoolConfig());
        }
    }

    @Bean
    @ConditionalOnBean(name = "defaultJedisCluster")
    public RedisClusterCache getRedisClusterCache(@Qualifier("defaultJedisCluster") JedisCluster jedisCluster) {
        return new RedisClusterCache(jedisCluster);
    }
}
