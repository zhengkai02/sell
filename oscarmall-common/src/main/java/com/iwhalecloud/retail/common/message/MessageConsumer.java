package com.iwhalecloud.retail.common.message;

import com.iwhalecloud.retail.common.utils.CustomConsumer;
import com.iwhalecloud.retail.common.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/4/28 <br>
 * @see com.iwhalecloud.retail.common.message <br>
 * @since IOT <br>
 */
@Slf4j
public final class MessageConsumer {
    private MessageConsumer() {
    }

    public static <T> void receiveMsg(String msgObj, Class<T> clazz, CustomConsumer<T> consumer) {
        try {
            T t = JsonUtil.json2T(msgObj, clazz);
            consumer.accept(t);
        }
        catch (Exception e) {
            log.info("MessageConsumer receive message Exception = [{}]", e.getMessage());
        }
    }
}
