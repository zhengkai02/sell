package com.iwhalecloud.retail.changan;

import com.iwhalecloud.retail.changan.DTO.req.AppRequestDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.service.ApplicationService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author ZhengKai
 * @data 2019-11-07 21:26
 */
public class ApplicationServiceImplTest extends ChanganApplicationTests {

    @Value("${changan.url}")
    private String path;

    @Autowired
    private ApplicationService applicationService;

    @Test
    public void createApp() {
        AppRequestDTO appRequestDTO =new AppRequestDTO();
        appRequestDTO.setAppId("8B2F79AC4C7A4F2BB71A81046EB01E47");
        appRequestDTO.setAppName("测试应用");
        appRequestDTO.setAppCode("10001");
        appRequestDTO.setBrandId(0);
        ResultVO<String> result = applicationService.createApp(appRequestDTO);
        Assert.assertTrue(result.getData() != null);
    }

    @Test
    public void updateApp() {
    }

    @Test
    public void deleteApp() {
    }
}