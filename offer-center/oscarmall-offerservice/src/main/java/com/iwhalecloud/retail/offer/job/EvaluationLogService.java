package com.iwhalecloud.retail.offer.job;

import com.iwhalecloud.retail.common.consts.CommonFiledDef;
import com.iwhalecloud.retail.common.utils.NumberFormatUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.AbstractEvaluationDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.entity.EvaluationLog;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.mapper.EvaluationLogMapper;
import com.iwhalecloud.retail.offer.mapper.EvaluationMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 评分计算,每日凌晨触发
 * @author fanxiaofei
 * @date 2019/05/07
 */
@Slf4j
@RestController
public class EvaluationLogService {

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private EvaluationLogMapper evaluationLogMapper;


    @PostMapping("/offer/autoevaluationlog")
    @Transactional(rollbackFor = Exception.class)
    public void autoUpdateEvaluationLog() {
        log.info("EvaluationLogService autoUpdateEvaluationLog start");

        List<ProdGoods> prodGoodsList = prodGoodsMapper.qryListByStateCAndStateF();
        log.info("EvaluationLogService queryLastByObjId prodGoodsList = [{}]", prodGoodsList);
        if (CollectionUtils.isNotEmpty(prodGoodsList)) {
            for (ProdGoods prodGoods : prodGoodsList) {
                String goodsId = prodGoods.getGoodsId();
                // 先查最新一条评价计算日志,没有表示第一次触发
                EvaluationLog evaluationLog = evaluationLogMapper.queryLastByObjId(goodsId);
                log.info("EvaluationLogService queryLastByObjId evaluationLog = [{}]", evaluationLog);
                Date now = DBDateUtil.getCurrentDBDateTime();
                // 本时间段内评价用户数
                Integer userCount;
                if (null == evaluationLog) {
                    userCount = evaluationMapper.countListByObjTypeAndObjId(AbstractEvaluationDef.OBJ_TYPE_A, goodsId);
                }
                else {
                    userCount = evaluationMapper.countListByObjTypeAndObjIdAndTime(AbstractEvaluationDef.OBJ_TYPE_A, goodsId, evaluationLog.getLogDate(), now);
                }
                log.info("EvaluationLogService queryLastByObjId userCount = [{}]", userCount);
                // 没有新增评价直接跳过
                if (userCount > 0) {
                    EvaluationLog newEvaluationLog = new EvaluationLog();
                    newEvaluationLog.setLogId(UidGeneator.getUID());
                    newEvaluationLog.setObjType(AbstractEvaluationDef.OBJ_TYPE_A);
                    newEvaluationLog.setObjId(goodsId);
                    newEvaluationLog.setLogDate(now);
                    // 本时间段内的评分
                    Integer rate;
                    // 计算后总评价用户数
                    Integer afterUserCount;
                    // 计算后的评价
                    Integer afterRate;
                    if (null == evaluationLog) {
                        rate = evaluationMapper.sumRateByObjTypeAndObjId(AbstractEvaluationDef.OBJ_TYPE_A, goodsId);
                        afterUserCount = userCount;
                        afterRate = rate;
                    }
                    else {
                        rate = evaluationMapper.sumRateByObjTypeAndObjIdAndTime(AbstractEvaluationDef.OBJ_TYPE_A, goodsId, evaluationLog.getLogDate(), now);
                        afterUserCount = evaluationLog.getUserCount() + userCount;
                        afterRate = evaluationLog.getRate() * (evaluationLog.getUserCount() / afterUserCount) + rate * (userCount / afterUserCount);
                        newEvaluationLog.setPreRate(evaluationLog.getAfterRate());
                        newEvaluationLog.setPreRateDate(evaluationLog.getLogDate());
                        newEvaluationLog.setPreUserCount(evaluationLog.getAfterUserCount());
                        newEvaluationLog.setPreLogId(evaluationLog.getLogId());
                    }
                    newEvaluationLog.setRate(rate);
                    newEvaluationLog.setUserCount(userCount);
                    newEvaluationLog.setAfterRate(afterRate);
                    newEvaluationLog.setAfterUserCount(afterUserCount);
                    newEvaluationLog.setCreateBy(CommonFiledDef.SYSTEM);
                    newEvaluationLog.setCreateTime(now);
                    newEvaluationLog.setModifyBy(CommonFiledDef.SYSTEM);
                    newEvaluationLog.setModifyTime(now);
                    newEvaluationLog.setState(CommonStateDef.ACTIVE);
                    evaluationLogMapper.insertByJob(newEvaluationLog);
                    // 5分记录数
                    Integer fiveRate = evaluationMapper.countRateIsFiveListByObjTypeAndObjId(AbstractEvaluationDef.OBJ_TYPE_A, goodsId);
                    log.info("EvaluationLogService countRateIsFiveListByObjTypeAndObjId fiveRate = [{}]", fiveRate);
                    Integer evaluationRate = NumberFormatUtil.getCeilNum(fiveRate, afterUserCount);
                    prodGoodsMapper.updateEvaluationByGoodsId(goodsId, fiveRate, evaluationRate);
                }
            }
        }

        log.info("EvaluationLogService autoUpdateEvaluationLog end");
    }
}
