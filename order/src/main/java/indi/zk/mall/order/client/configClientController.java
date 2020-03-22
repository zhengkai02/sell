package indi.zk.mall.order.client;

import indi.zk.mall.order.config.ConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhengKai
 * @data 2019-10-16 17:33
 */
@RestController
public class configClientController {
    @Autowired
    private ConfigInfo config;

//    @Value("${config.info}")
//    private String confgiInfo;

    @GetMapping("/configInfo")
    public String getConfgiInfo(String configInfo){
        return config.getInfo();

    }

}
