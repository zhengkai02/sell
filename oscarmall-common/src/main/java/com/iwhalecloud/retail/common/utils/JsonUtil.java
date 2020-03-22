package com.iwhalecloud.retail.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;

import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.rabbitmq.tools.json.JSONReader;
import com.rabbitmq.tools.json.JSONWriter;

/**
 * Created by xh on 2019/3/15.
 */
public abstract class JsonUtil {

    private static JSONWriter jsonWriter = new JSONWriter();
    private static JSONReader jsonReader = new JSONReader();

    public static String object2Json(Object object) {
        return jsonWriter.write(object);
    }

    public static Object json2Object(String json) {
        return jsonReader.read(json);
    }

    private JsonUtil() {

    }

    public static <T> T json2T(String json, Class<T> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException, BaseException {
        AssertUtil.isNotEmpty(json, CommonBaseMessageCodeEnum.REQ_PARAM_NOT_NULL);
        Map<String, Object> map = (HashMap<String, Object>) JsonUtil.json2Object(json);
        T t = clazz.newInstance();
        ConvertUtils.register(new DateConverter(), Date.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new BooleanConverter(null), Boolean.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        BeanUtils.populate(t, map);
        return t;
    }

}
