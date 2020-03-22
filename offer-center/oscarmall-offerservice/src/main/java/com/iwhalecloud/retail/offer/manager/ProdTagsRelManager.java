package com.iwhalecloud.retail.offer.manager;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.mapper.ProdTagsRelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author ji.maowei <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019-10-9 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0C<br>
 */
@Service
@Slf4j
public class ProdTagsRelManager {

    @Autowired
    ProdTagsRelMapper prodTagsRelMapper;

    /**
     * 根据在页面上选择的目录、内容类型、内容状态、标签以及输入的关键字等查询所有内容, 不分页
     *
     * @param tagId
     * @return
     * @author ji.maowei
     * @date 2019/10/9
     */
    public List<String> queryProdGoodsIdListByTagId(String tagId) throws BaseException {
        log.info("prodTagsRelController queryProdGoodsIdListByTagId start");
        AssertUtil.isNotEmpty(tagId, OfferBaseMessageCodeEnum.TAG_ID_IS_NULL);
        List<String> prodGoodsIdList = prodTagsRelMapper.listProdGoodsIdByTagId(tagId);
        log.info("prodTagsRelController queryProdGoodsIdListByTagId end");
        return prodGoodsIdList;

    }
}
