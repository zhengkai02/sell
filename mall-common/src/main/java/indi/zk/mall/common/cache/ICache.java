package com.iwhalecloud.retail.common.cache;

import java.io.Serializable;

/**
 * 缓存操作类
 * @author Z
 * @date 2018/11/23
 */
public interface ICache {


    /**
     * 根据Key值和命名空间返回对象
     * @param nameSpace 数据所在的namespace
     * @param key 要获取的数据的key
     * @return 数据
     */
    public Serializable get(int nameSpace, String key);
    
    /**
     * 根据Key值和命名空间返回对象
     * @param nameSpace 数据所在的namespace
     * @param key 要获取的数据的key
     * @return 数据
     */
    public Serializable get(String nameSpace, String key);


    /**
     * 按照命名空间设置数据，如果数据已经存在，则覆盖，如果不存在，则新增 <br>
     * 如果是新增，则有效时间为0，即不失效
     * @param nameSpace
     * @param key
     * @param value
     */
    public void set(int nameSpace, String key, Serializable value);
    
    public void set(String nameSpace, String key, Serializable value);

    public void setNumber(String nameSpace, String key, String value);

    public String getNumber(String nameSpace, String key);

    /**
     * 按照命名空间设置数据，如果数据已经存在，则相加，如果不存在，则新增 <br>
     * @param nameSpace
     * @param key
     * @param value
     */
    public Long incrBy(String nameSpace, String key, Integer value);

    /**
     * 按照命名空间设置数据，如果数据已经存在，则相加1，如果不存在，则新增1 <br>
     * @param nameSpace
     * @param key
     */
    public Long incr(String nameSpace, String key);

    /**
     * 按照命名空间设置数据，如果数据已经存在，则相减 <br>
     * @param nameSpace
     * @param key
     * @param value
     */
    public Long decrBy(String nameSpace, String key, Integer value);

    /**
     * 按照命名空间设置数据，如果数据已经存在，则减1 <br>
     * @param nameSpace
     * @param key
     */
    public Long decr(String nameSpace, String key);

    /**
     * 按照命名空间设置数据，如果数据已经存在，则覆盖，如果不存在，则新增 <br>
     * 如果是新增，则有效时间为expireTime，单位为秒
     * @param nameSpace
     * @param key
     * @param value
     * @param expireTime
     */
    public void set(int nameSpace, String key, Serializable value, int expireTime);

    /**
     * 按照命名空间设置数据，如果数据已经存在，则覆盖，如果不存在，则新增 <br>
     * 如果是新增，则有效时间为expireTime，单位为秒
     * @param nameSpace
     * @param key
     * @param value
     * @param expireTime
     */
    public void set(String nameSpace, String key, Serializable value, int expireTime);

    /**
     *
     * 按照命名空间设置数据，如果数据已经存在，则覆盖，如果不存在，则新增 <br>
     * 如果是新增，millisecondsTimestamp
     * @param nameSpace
     * @param key
     * @param value
     * @param millisecondsTimestamp
     */
    public void set(String nameSpace, String key, Serializable value, long millisecondsTimestamp);

    /**
     * 根据命名空间和key值删除相应的值
     * @param nameSpace
     * @param key
     */
    public void delete(int nameSpace, String key);

    /**
     * 根据命名空间和key值删除相应的值
     * @param nameSpace
     * @param key
     */
    public void delete(String nameSpace, String key);


    /**
     * 根据命名空间清除cache
     * @param nameSpace
     */
    public void clear(int nameSpace);
}
