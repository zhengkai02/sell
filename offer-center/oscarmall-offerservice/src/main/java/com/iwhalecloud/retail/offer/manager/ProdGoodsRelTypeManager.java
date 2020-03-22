package com.iwhalecloud.retail.offer.manager;

import com.iwhalecloud.retail.offer.dto.ProdGoodsRelTypeDTO;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsRelTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fanxiaofei
 * @date 2019/02/25
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProdGoodsRelTypeManager {

    @Resource
    private ProdGoodsRelTypeMapper mapper;


    /**
     * 查询全部商品关系类型
     * @return List<ProdGoodsRelTypeDTO>
     */
    public List<ProdGoodsRelTypeDTO> listProdGoodsRelType() {
        log.info("ProdGoodsRelTypeManager listProdGoodsRelType start");

        List<ProdGoodsRelTypeDTO> result = new ArrayList<>();
        List<ProdGoodsRelTypeDTO> list = mapper.listProdGoodsRelType();
        if (CollectionUtils.isNotEmpty(list)) {
            result = list;
        }

        log.info("ProdGoodsRelTypeManager listProdGoodsRelType end");
        return result;
    }

}
