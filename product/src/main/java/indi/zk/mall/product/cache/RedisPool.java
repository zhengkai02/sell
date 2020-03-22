package indi.zk.mall.product.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;


/**
 * 单例模式创建redis连接池
 * @author ZhengKai
 * @data 2019-10-31 16:29
 */
@Service("redisPool")
@Slf4j
public class RedisPool {

    private ShardedJedisPool shardedJedisPool;

    public ShardedJedis instance() {
        return shardedJedisPool.getResource();
    }

    public void safeClose(ShardedJedis shardedJedis) {
        try {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        } catch (Exception e) {
            log.error("redis关闭异常");
        }
    }

}
