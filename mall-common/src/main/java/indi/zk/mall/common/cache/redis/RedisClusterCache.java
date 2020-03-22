package com.iwhalecloud.retail.common.cache.redis;

import com.iwhalecloud.retail.common.cache.ICache;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Created by xh on 2019/2/14.
 */
public class RedisClusterCache implements ICache {
    private JedisCluster jedisCluster;

    public RedisClusterCache(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }


    @Override
    public Serializable get(int nameSpace, String key) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();

        byte[] cacheValue = jedisCluster.get(cacheKey);
        return (Serializable) byteToObject(cacheValue);
    }

    @Override
    public void set(int nameSpace, String key, Serializable value) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        final byte[] cacheValue = objectToByte(value);
        this.jedisCluster.set(cacheKey, cacheValue);
    }

    @Override
    public void set(int nameSpace, String key, Serializable value, int expireTime) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        this.set(nameSpace, key, value);

        this.jedisCluster.expire(cacheKey, expireTime);
    }

    @Override
    public void set(String nameSpace, String key, Serializable value, int expireTime) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        this.set(nameSpace, key, value);

        this.jedisCluster.expire(cacheKey, expireTime);
    }

    @Override
    public void set(String nameSpace, String key, Serializable value, long millisecondsTimestamp) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        this.set(nameSpace, key, value);

        this.jedisCluster.pexpireAt(cacheKey, millisecondsTimestamp);
    }

    @Override
    public void delete(int nameSpace, String key) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        this.jedisCluster.del(cacheKey);
    }

    @Override
    public void delete(String nameSpace, String key) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        this.jedisCluster.del(cacheKey);
    }

    @Override
    public void clear(int nameSpace) {
        final byte[] cacheKey = getKey(nameSpace, "*").getBytes();
        this.jedisCluster.del(cacheKey);
    }

    @Override
    public Long incrBy(String nameSpace, String key, Integer value) {
        return this.jedisCluster.incrBy(getKey(nameSpace, key).getBytes(), value);
    }

    @Override
    public Long decrBy(String nameSpace, String key, Integer value) {
        return this.jedisCluster.decrBy(getKey(nameSpace, key).getBytes(), value);
    }

    @Override
    public Long incr(String nameSpace, String key) {
        return this.jedisCluster.incr(getKey(nameSpace, key).getBytes());
    }


    @Override
    public Long decr(String nameSpace, String key) {
        return this.jedisCluster.decr(getKey(nameSpace, key).getBytes());
    }

    private String getKey(int nameSpace, String key) {
        return nameSpace + "_" + key;
    }

    private String getKey(String nameSpace, String key) {
        return nameSpace + "_" + key;
    }

    private byte[] objectToByte(Object obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
            oo.close();
            bo.close();
        }
        catch (Exception e) {
            e.getMessage();
        }
        return (bytes);
    }

    private static Object byteToObject(byte[] bytes) {
        Object obj = null;
        if (bytes != null && bytes.length > 0) {
            try {
                ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
                ObjectInputStream oi = new ObjectInputStream(bi);
                obj = oi.readObject();
                oi.close();
                bi.close();
            }
            catch (Exception e) {
                e.getMessage();
            }
        }
        return obj;
    }

    @Override
    public Serializable get(String nameSpace, String key) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();

        byte[] cacheValue = jedisCluster.get(cacheKey);
        if (cacheValue == null) {
            return null;
        }
        return (Serializable) byteToObject(cacheValue);
    }

    @Override
    public void set(String nameSpace, String key, Serializable value) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        final byte[] cacheValue = objectToByte(value);
        this.jedisCluster.set(cacheKey, cacheValue);
    }

    @Override
    public String getNumber(String nameSpace, String key) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        byte[] cacheValue = jedisCluster.get(cacheKey);
        return new StringRedisSerializer().deserialize(cacheValue);
    }

    @Override
    public void setNumber(String nameSpace, String key, String value) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        final byte[] cacheValue = new StringRedisSerializer().serialize(value);
        this.jedisCluster.set(cacheKey, cacheValue);
    }

}
