package com.iwhalecloud.retail.offer.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.AddSensitiveWordsReq;
import com.iwhalecloud.retail.offer.dto.req.ModSensitiveWordsReq;
import com.iwhalecloud.retail.offer.dto.req.QrySensitiveWordsReq;
import com.iwhalecloud.retail.offer.dto.resp.AddSensitiveWordsResp;
import com.iwhalecloud.retail.offer.dto.resp.ModSensitiveWordsResp;
import com.iwhalecloud.retail.offer.entity.SensitiveWords;
import com.iwhalecloud.retail.offer.mapper.SensitiveWordsMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0<br>
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SensitiveWordsManager {

    @Autowired
    private SensitiveWordsMapper sensitiveWordsMapper;

    public Page<SensitiveWords> qryAllSensitiveWords(QrySensitiveWordsReq request) {
        log.info("SensitiveWordsManager qryAllSensitiveWords start");
        Page<SensitiveWords> page = new Page<>(request.getPageNo(), request.getPageSize());
        Page<SensitiveWords> resp = sensitiveWordsMapper.qryAllSensitiveWords(page, request);
        log.info("SensitiveWordsManager qryAllSensitiveWords end");
        return  resp;
    }

    public AddSensitiveWordsResp addSensitiveWords(AddSensitiveWordsReq request, Long userId) throws BaseException {
        log.info("SensitiveWordsManager addSensitiveWords start request = [{}]", request);
        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(request.getWords(), OfferBaseMessageCodeEnum.SENSITIVE_WORDS_IS_NULL);
        AddSensitiveWordsResp resp = new AddSensitiveWordsResp();
        // 敏感词不重复
        Map<String, Object> sameWordsMap = new HashMap<>();
        sameWordsMap.put(ColumnNameDef.WORDS, request.getWords());
        sameWordsMap.put(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<SensitiveWords> sameWordsList = sensitiveWordsMapper.selectByMap(sameWordsMap);
        if (sameWordsList != null && !sameWordsList.isEmpty()) {
            throw new BaseException(OfferBaseMessageCodeEnum.SENSITIVE_WORDS_IS_EXIST);
        }
        SensitiveWords sensitiveWords = new SensitiveWords();
        sensitiveWords.setWordId(Long.valueOf(UidGeneator.getUIDStr()));
        sensitiveWords.setWords(request.getWords());
        Date now = DBDateUtil.getCurrentDBDateTime();
        sensitiveWords.setCreateTime(now);
        sensitiveWords.setCreateBy(userId.toString());
        sensitiveWords.setModifyTime(now);
        sensitiveWords.setModifyBy(userId.toString());
        sensitiveWords.setState(CommonStateDef.ACTIVE);
        sensitiveWords.setStateDate(now);
        sensitiveWordsMapper.insert(sensitiveWords);
        resp.setId(sensitiveWords.getId());
        log.info("SensitiveWordsManager addSensitiveWords end");
        return  resp;
    }

    public ModSensitiveWordsResp modifySensitiveWords(Long id, ModSensitiveWordsReq request, Long userId) throws BaseException {
        log.info("SensitiveWordsManager modSensitiveWords start request = [{}]", request);
        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotNull(id, OfferBaseMessageCodeEnum.SENSITIVE_WORDS_ID_IS_NULL);
        AssertUtil.isNotEmpty(request.getWords(), OfferBaseMessageCodeEnum.SENSITIVE_WORDS_IS_NULL);

        SensitiveWords sensitiveWords = checkSensitiveWordsExist(id);
        ModSensitiveWordsResp resp = new ModSensitiveWordsResp();
        // 敏感词重复校验
        Map<String, Object> sameWordsMap = new HashMap<>();
        sameWordsMap.put(ColumnNameDef.WORDS, request.getWords());
        sameWordsMap.put(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<SensitiveWords> sameWordsList = sensitiveWordsMapper.selectByMap(sameWordsMap);
        if (CollectionUtils.isNotEmpty(sameWordsList) && !sameWordsList.get(0).getId().equals(id)) {
                throw new BaseException(OfferBaseMessageCodeEnum.SENSITIVE_WORDS_IS_EXIST);
        }
        if (!sensitiveWords.getWords().equals(request.getWords())) {
            sensitiveWords.setWords(request.getWords());
            sensitiveWords.setModifyBy(userId.toString());
            sensitiveWords.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            sensitiveWordsMapper.updateById(sensitiveWords);
        }
        resp.setId(id);
        log.info("SensitiveWordsManager modSensitiveWords end");
        return resp;
    }

    public void deleteSensitiveWords(Long wordId, Long userId) throws BaseException {
        log.info("SensitiveWordsManager deleteSensitiveWords start wordId = ", wordId);
        AssertUtil.isNotNull(wordId, OfferBaseMessageCodeEnum.SENSITIVE_WORDS_ID_IS_NULL);
        SensitiveWords sensitiveWords = checkSensitiveWordsExist(wordId);
        sensitiveWords.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        sensitiveWords.setModifyBy(userId.toString());
        sensitiveWords.setState(CommonStateDef.INACTIVE);
        sensitiveWords.setStateDate(DBDateUtil.getCurrentDBDateTime());
        sensitiveWordsMapper.updateById(sensitiveWords);
        log.info("SensitiveWordsManager deleteSensitiveWords end");
    }

    private SensitiveWords checkSensitiveWordsExist(Long wordId) throws BaseException {
        SensitiveWords sensitiveWords = sensitiveWordsMapper.selectById(wordId);
        if (sensitiveWords == null || !sensitiveWords.getState().equals(CommonStateDef.ACTIVE)) {
            throw new BaseException(OfferBaseMessageCodeEnum.SENSITIVE_WORDS_NOT_EXIST);
        }
        return sensitiveWords;
    }


    /**
     * 查询符合状态的敏感词名称,不传查所有状态
     * @param state state
     * @return List<String>
     */
    public List<String> listSensitiveWordsByState(String state) {
        log.info("SensitiveWordsManager listSensitiveWordsByState start, state = [{}]", state);
        List<String> result = sensitiveWordsMapper.listSensitiveWordsByState(state);
        log.info("SensitiveWordsManager listSensitiveWordsByState end");
        return result;
    }

}
