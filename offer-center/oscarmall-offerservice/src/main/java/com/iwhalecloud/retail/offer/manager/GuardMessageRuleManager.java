package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.common.dto.GuardMessageRule;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.entity.TblGuardMessageRule;
import com.iwhalecloud.retail.offer.mapper.TblGuardMessageRuleMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/7 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0C<br>
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GuardMessageRuleManager {

    @Autowired
    private TblGuardMessageRuleMapper tblGuardMessageRuleMapper;

    @Autowired
    private ICache redisCache;

    public GuardMessageRule qryGuardMessageRuleByQueue(String queue) throws BaseException {
        log.info("GuardMessageRuleManager qryGuardMessageRuleByQueue start, queue = {}", queue);
        AssertUtil.isNotEmpty(queue, OfferBaseMessageCodeEnum.QUEUE_IS_EMPTY);

        GuardMessageRule guardMessageRule = (GuardMessageRule) redisCache.get(CacheKeyDef.MQ_RULE, queue);
        if (null != guardMessageRule) {
            log.info("GuardMessageRuleManager qryGuardMessageRuleByQueue end, redis guardMessageRule = [{}]",
                guardMessageRule);
            return guardMessageRule;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ColumnNameDef.QUEUE, queue);
        queryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        TblGuardMessageRule tblGuardMessageRule = tblGuardMessageRuleMapper.selectOne(queryWrapper);

        guardMessageRule = new GuardMessageRule();
        if (null == tblGuardMessageRule) {
            guardMessageRule.setQueue(queue);
            guardMessageRule.setCanDowngrade("Y");
        }
        else {
            BeanUtils.copyProperties(tblGuardMessageRule, guardMessageRule);
        }

        redisCache.set(CacheKeyDef.MQ_RULE, guardMessageRule.getQueue(), guardMessageRule, getNextHourTimeSeconds());
        log.info("GuardMessageRuleManager qryGuardMessageRuleByQueue end, guardMessageRule = [{}]", guardMessageRule);
        return guardMessageRule;
    }

    private int getNextHourTimeSeconds() {
        Date now = DBDateUtil.getCurrentDBDateTime();
        Calendar ca = Calendar.getInstance();
        ca.setTime(now);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.add(Calendar.HOUR, 1);
        Date newDate = ca.getTime();
        // 这里的秒数最大就是60 * 60 = 3600，可以直接将long转int
        final int division = 1000;
        int seconds = (int) (newDate.getTime() - now.getTime()) / division;
        log.info("GuardMessageRuleManager getNextHourTime, newDate = {}", newDate);
        return seconds;
    }

}
