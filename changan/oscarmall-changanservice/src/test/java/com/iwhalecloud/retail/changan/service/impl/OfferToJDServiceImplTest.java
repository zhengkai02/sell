package com.iwhalecloud.retail.changan.service.impl;

import com.iwhalecloud.retail.changan.ChanganApplicationTests;
import com.iwhalecloud.retail.changan.DTO.Attributes;
import com.iwhalecloud.retail.changan.DTO.OfferToJDRequestDTO;
import com.iwhalecloud.retail.changan.DTO.PackageList;
import com.iwhalecloud.retail.changan.VO.OfferToJDResultVO;
import com.iwhalecloud.retail.changan.service.OfferToJDService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhengKai
 * @data 2019-11-22 16:37
 */
public class OfferToJDServiceImplTest extends ChanganApplicationTests {

    @Autowired
    private OfferToJDService offerToJDService;

    @Test
    public void commonNotification() {
    }

    @Test
    public void syncOfferToJD() {
        OfferToJDRequestDTO offerToJDRequestDTO = new OfferToJDRequestDTO();
        offerToJDRequestDTO.setEnterpriseId("123");
        offerToJDRequestDTO.setActiveTime("2019-11-18");

        Attributes attributes = new Attributes();
        attributes.setKey("123");
        attributes.setValue("value");
        attributes.setDescription("描述");

        List<Attributes> attributesList = new ArrayList<>();
        attributesList.add(attributes);
        offerToJDRequestDTO.setAttributes(attributesList);

        Integer[] eSIMTypes = {1,2};
        offerToJDRequestDTO.setESIMTypes(eSIMTypes);
        offerToJDRequestDTO.setInvoiceContent("发票内容");
        offerToJDRequestDTO.setExpireTime("2019-11-20");
        offerToJDRequestDTO.setPrice(10);

        List<PackageList> packageList = new ArrayList<>();
        PackageList packages = new PackageList();
        packages.setId("0001");
        packages.setPackagePeriod(30);
        packages.setPackageBalance(1024);
        packages.setSerial("10002");
        packageList.add(packages);
        offerToJDRequestDTO.setPackageList(packageList);

        offerToJDRequestDTO.setProductDescription("商品描述");
        offerToJDRequestDTO.setPeriod(2);
        offerToJDRequestDTO.setProductName("商品描述");
        offerToJDRequestDTO.setProductSerial("1");
        offerToJDRequestDTO.setPurchaseMode(3);
        offerToJDRequestDTO.setStatus(1);
        offerToJDRequestDTO.setSyncType(0);
        offerToJDRequestDTO.setThreshold(1L);

        OfferToJDResultVO result = offerToJDService.syncOfferToJD(offerToJDRequestDTO);
        Assert.assertTrue(null != result);

    }
}