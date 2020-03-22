package com.iwhalecloud.retail.common.cache.redis;

import com.iwhalecloud.retail.common.cache.ICache;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 操作redis缓存实现类
 *
 * @author Z
 * @date 2018/11/23
 */
public class RedisCache implements ICache {
    private JedisPool jedisPool;

    public RedisCache(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    @Override
    public Serializable get(int nameSpace, String key) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();

        Jedis jed1 = null;
        byte[] cache1 = null;
        try {
            jed1 = jedisPool.getResource();
            cache1 = jed1.get(cacheKey);
        }
        catch (Exception ex1) {
            ex1.getMessage();

        }
        finally {
            if (null != jed1) {
                jed1.close();
            }
        }
        if (null == cache1) {
            return null;
        }
        return (Serializable) byteToObject(cache1);
    }

    @Override
    public void set(int nameSpace, String key, Serializable value) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        final byte[] cacheValue = objectToByte(value);

        Jedis jedis2 = null;
        try {
            jedis2 = jedisPool.getResource();
            jedis2.set(cacheKey, cacheValue);
        }
        catch (Exception ex2) {
            ex2.getStackTrace();
        }
        finally {
            if (null != jedis2) {
                jedis2.close();
            }
        }
    }

    @Override
    public void set(int nameSpace, String key, Serializable value, int expireTime) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        this.set(nameSpace, key, value);

        Jedis jedis3 = null;
        try {
            jedis3 = jedisPool.getResource();
            jedis3.expire(cacheKey, expireTime);
        }
        catch (Exception ex3) {
            ex3.getStackTrace();
        }
        finally {
            if (null != jedis3) {
                jedis3.close();
            }
        }
    }

    @Override
    public void set(String nameSpace, String key, Serializable value, int expireTime) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        this.set(nameSpace, key, value);

        Jedis jedis4 = null;
        try {
            jedis4 = jedisPool.getResource();
            jedis4.expire(cacheKey, expireTime);
        }
        catch (Exception ex4) {
            ex4.getStackTrace();
        }
        finally {
            if (null != jedis4) {
                jedis4.close();
            }
        }
    }

    @Override
    public void set(String nameSpace, String key, Serializable value, long millisecondsTimestamp) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        this.set(nameSpace, key, value);

        Jedis jedis5 = null;
        try {
            jedis5 = jedisPool.getResource();
            jedis5.pexpireAt(cacheKey, millisecondsTimestamp);
        }
        catch (Exception ex5) {
            ex5.getStackTrace();
        }
        finally {
            if (null != jedis5) {
                jedis5.close();
            }
        }
    }

    @Override
    public void delete(int nameSpace, String key) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();

        Jedis jedis6 = null;
        try {
            jedis6 = jedisPool.getResource();
            jedis6.del(cacheKey);
        }
        catch (Exception ex6) {
            ex6.getStackTrace();
        }
        finally {
            if (null != jedis6) {
                jedis6.close();
            }
        }
    }

    @Override
    public void delete(String nameSpace, String key) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();

        Jedis jedis7 = null;
        try {
            jedis7 = jedisPool.getResource();
            jedis7.del(cacheKey);
        }
        catch (Exception ex7) {
            ex7.getStackTrace();
        }
        finally {
            if (null != jedis7) {
                jedis7.close();
            }
        }
    }

    @Override
    public void clear(int nameSpace) {
        final byte[] cacheKey = getKey(nameSpace, "*").getBytes();
        Jedis jedis8 = null;
        try {
            jedis8 = jedisPool.getResource();
            jedis8.del(cacheKey);
        }
        catch (Exception ex8) {
            ex8.getStackTrace();
        }
        finally {
            if (null != jedis8) {
                jedis8.close();
            }
        }
    }

    @Override
    public Long incrBy(String nameSpace, String key, Integer value) {
        Jedis jedis9 = null;
        Long num = null;
        try {
            jedis9 = jedisPool.getResource();
            num = jedis9.incrBy(getKey(nameSpace, key).getBytes(), value);
        }
        catch (Exception ex9) {
            ex9.getStackTrace();
        }
        finally {
            if (null != jedis9) {
                jedis9.close();
            }
        }
        return num;
    }

    @Override
    public Long decrBy(String nameSpace, String key, Integer value) {

        Jedis jedis10 = null;
        Long num = null;
        try {
            jedis10 = jedisPool.getResource();
            num = jedis10.decrBy(getKey(nameSpace, key).getBytes(), value);
        }
        catch (Exception ex10) {
            ex10.getStackTrace();
        }
        finally {
            if (null != jedis10) {
                jedis10.close();
            }
        }
        return num;
    }

    @Override
    public Long incr(String nameSpace, String key) {

        Jedis jedis11 = null;
        Long num = null;
        try {
            jedis11 = jedisPool.getResource();
            num = jedis11.incr(getKey(nameSpace, key).getBytes());
        }
        catch (Exception ex11) {
            ex11.getStackTrace();
        }
        finally {
            if (null != jedis11) {
                jedis11.close();
            }
        }
        return num;
    }


    @Override
    public Long decr(String nameSpace, String key) {

        Jedis jedis12 = null;
        Long num = null;
        try {
            jedis12 = jedisPool.getResource();
            num = jedis12.decr(getKey(nameSpace, key).getBytes());
        }
        catch (Exception ex12) {
            ex12.getStackTrace();
        }
        finally {
            if (null != jedis12) {
                jedis12.close();
            }
        }
        return num;
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
            bo.close();
            oo.close();
        }
        catch (Exception ex13) {
            ex13.getMessage();
        }
        return (bytes);
    }

    private static Object byteToObject(byte[] bytes) {
        Object obj14 = null;
        if (bytes != null && bytes.length > 0) {
            try {
                ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
                ObjectInputStream oi = new ObjectInputStream(bi);
                obj14 = oi.readObject();
                bi.close();
                oi.close();
            }
            catch (Exception ex14) {
                ex14.getMessage();
            }
        }
        return obj14;
    }

    @Override
    public Serializable get(String nameSpace, String key) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();

        Jedis jedis15 = null;
        byte[] cacheValue = null;
        try {
            jedis15 = jedisPool.getResource();
            cacheValue = jedis15.get(cacheKey);
        }
        catch (Exception ex15) {
            ex15.getStackTrace();
        }
        finally {
            if (null != jedis15) {
                jedis15.close();
            }
        }
        if (cacheValue == null) {
            return null;
        }
        return (Serializable) byteToObject(cacheValue);
    }

    @Override
    public void set(String nameSpace, String key, Serializable value) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        final byte[] cacheValue = objectToByte(value);

        Jedis jedis16 = null;
        try {
            jedis16 = jedisPool.getResource();
            jedis16.set(cacheKey, cacheValue);
        }
        catch (Exception ex16) {
            ex16.getStackTrace();
        }
        finally {
            if (null != jedis16) {
                jedis16.close();
            }
        }

    }

    @Override
    public void setNumber(String nameSpace, String key, String value) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();
        final byte[] cacheValue = new StringRedisSerializer().serialize(value);

        Jedis jedis17 = null;
        try {
            jedis17 = jedisPool.getResource();
            jedis17.set(cacheKey, cacheValue);
        }
        catch (Exception ex17) {
            ex17.getStackTrace();
        }
        finally {
            if (null != jedis17) {
                jedis17.close();
            }
        }

    }

    @Override
    public String getNumber(String nameSpace, String key) {
        final byte[] cacheKey = getKey(nameSpace, key).getBytes();

        Jedis jedis18 = null;
        byte[] cacheValue = null;
        try {
            jedis18 = jedisPool.getResource();
            cacheValue = jedis18.get(cacheKey);
        }
        catch (Exception e) {
            e.getStackTrace();
        }
        finally {
            if (null != jedis18) {
                jedis18.close();
            }
        }
        if (null == cacheValue) {
            return null;
        }
        return new StringRedisSerializer().deserialize(cacheValue);
    }
}
