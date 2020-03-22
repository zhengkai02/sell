package indi.zk.mall.product.cache;

import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;


/**
 * 操作redis,将从长安接口获取的access_token放入缓存中
 * 将access_token有效期设置为从长安接口获取的令牌有效期.
 * @author ZhengKai
 * @data 2019-10-31 16:26
 */
@Service("redisCacheService")
public class RedisCacheService{

    // 注入redisPool
    private RedisPool redisPool;

    /**
     * 把要存储的值放进缓存
     * @param cacheKey
     * @param toSaveValue
     * @param timeOutSecond  有效时间，秒
     */
    public void saveCache(String cacheKey, String toSaveValue, int timeOutSecond) {
        if (toSaveValue == null) {
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = redisPool.instance();
            shardedJedis.setex(cacheKey, timeOutSecond, toSaveValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisPool.safeClose(shardedJedis);
        }
    }

    /**
     * 根据键值从缓存中获取数据
     * @param cacheKey
     * @return
     */
    public String getFromCache(String cacheKey) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = redisPool.instance();
            String value = shardedJedis.get(cacheKey);
            return value;
        } catch (Exception e) {
            return null;
        } finally {
            redisPool.safeClose(shardedJedis);
        }
    }
}
