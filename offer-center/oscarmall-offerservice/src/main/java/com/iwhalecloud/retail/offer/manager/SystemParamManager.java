package com.iwhalecloud.retail.offer.manager;

import com.iwhalecloud.retail.offer.entity.SystemParam;
import com.iwhalecloud.retail.offer.mapper.SystemParamMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SystemParamManager {

    @Autowired
    private SystemParamMapper systemParamMapper;

    public SystemParam selectSystemParamByMask(String mask) {
        log.info("SystemParamManager selectSystemParamByMask start, mask = [{}]", mask);
        SystemParam systemParam = systemParamMapper.selectSystemParamByMask(mask);
        log.info("SystemParamManager selectSystemParamByMask end");
        return systemParam;
    }
}
