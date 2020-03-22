package com.iwhalecloud.retail.common.aware;

import com.iwhalecloud.retail.common.utils.SpringContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by xh on 2019/2/22.
 */
@Service
public class SpringContextAware implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContext.setApplicationContext(applicationContext);
    }
}
