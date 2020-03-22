package com.iwhalecloud.retail.offer.manager;

import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.AbstractUsefulUselessDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.client.req.UpdateContentBaseReq;
import com.iwhalecloud.retail.offer.dto.req.QueryUsefulUselessReq;
import com.iwhalecloud.retail.offer.dto.req.UsefulUselessReq;
import com.iwhalecloud.retail.offer.dto.resp.QueryUsefulUselessResp;
import com.iwhalecloud.retail.offer.entity.Evaluation;
import com.iwhalecloud.retail.offer.entity.UsefulUseless;
import com.iwhalecloud.retail.offer.mapper.EvaluationMapper;
import com.iwhalecloud.retail.offer.mapper.UsefulUselessMapper;
import com.iwhalecloud.retail.offer.service.ContentService;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 赞踩
 * 
 * @author fanxiaofei
 * @date 2019/05/07
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UsefulUselessManager {

    @Autowired
    private UsefulUselessMapper usefulUselessMapper;

    @Autowired
    private ICache redisCache;

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private ContentService contentService;

    @Value("${usefuluseless.redisNum.max}")
    private String usefulOrLessMaxNum;

    /**
     * 赞踩详情
     * 
     * @param req QueryUsefulUselessReq
     * @return QueryUsefulUselessResp
     */
    public QueryUsefulUselessResp query(QueryUsefulUselessReq req) throws BaseException {
        log.info("UsefulUselessManager query start, QueryUsefulUselessReq = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getUserId(), OfferBaseMessageCodeEnum.USER_ID_IS_NULL);
        AssertUtil.isNotNull(req.getObjType(), OfferBaseMessageCodeEnum.USEFULUSELESS_OBJ_TYPE_IS_NULL);
        AssertUtil.isNotNull(req.getObjId(), OfferBaseMessageCodeEnum.USEFULUSELESS_OBJ_ID_IS_NULL);
        QueryUsefulUselessResp result = usefulUselessMapper.queryByObjTypeAndObjId(req);
        // 不用抛出异常

        log.info("UsefulUselessManager query end");
        return result;
    }

    /**
     * 赞踩评价和文章
     * 
     * @param req UsefulUselessReq
     */
    public void action(UsefulUselessReq req) throws BaseException {
        log.info("UsefulUselessManager action start, UsefulUselessReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getUserId(), OfferBaseMessageCodeEnum.USER_ID_IS_NULL);
        AssertUtil.isNotNull(req.getObjType(), OfferBaseMessageCodeEnum.USEFULUSELESS_OBJ_TYPE_IS_NULL);
        AssertUtil.isNotNull(req.getObjId(), OfferBaseMessageCodeEnum.USEFULUSELESS_OBJ_ID_IS_NULL);
        AssertUtil.isNotNull(req.getType(), OfferBaseMessageCodeEnum.USEFULUSELESS_TYPE_IS_ERROR);
        AssertUtil.isNotNull(req.getAction(), OfferBaseMessageCodeEnum.USEFULUSELESS_ACTION_IS_ERROR);

        // 新增
        if (AbstractUsefulUselessDef.ACTION_A.equals(req.getAction())) {
            UsefulUseless usefulUseless = new UsefulUseless();
            BeanUtils.copyProperties(req, usefulUseless);
            usefulUseless.setUsefulUselessId(UidGeneator.getUID());
            usefulUseless.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            usefulUseless.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            usefulUseless.setModifyBy(req.getUserId().toString());
            usefulUseless.setCreateBy(req.getUserId().toString());
            usefulUseless.setState(AbstractUsefulUselessDef.STATE_A);
            usefulUselessMapper.insert(usefulUseless);
        }
        // 删除
        if (AbstractUsefulUselessDef.ACTION_D.equals(req.getAction())) {
            usefulUselessMapper.updateStateByObjTypeAndObjId(req);
        }

        // 默认设置10
        if (StringUtils.isEmpty(usefulOrLessMaxNum)) {
            usefulOrLessMaxNum = "10";
        }
        // 踩赞数量 key = 踩or赞 + objtype + objid
        String keyNum = releaseForGetStr(req);
        log.info("UsefulUselessManager action redisCache, getNumber = [{}]", keyNum);
        if (StringUtils.isEmpty(keyNum) || Integer.parseInt(keyNum) < 0) {
            keyNum = "0";
        }
        // 超过设置的阙值,更新数据库,redis计数器重置为0
        int sumNum = Integer.parseInt(keyNum) + 1;
        if (sumNum >= Integer.parseInt(usefulOrLessMaxNum)) {
            // 更新内容表,内容表的赞和踩内容中心根据type处理
            if (AbstractUsefulUselessDef.OBJTYPE_C.equals(req.getObjType())) {
                UpdateContentBaseReq updateContentBaseReq = new UpdateContentBaseReq();
                updateContentBaseReq.setType(req.getType());
                updateContentBaseReq.setContentId(Long.valueOf(req.getObjId()));
                updateContentBaseReq.setUsefulCount(sumNum);
                updateContentBaseReq.setUselessCount(sumNum);
                updateContentBaseReq.setAction(req.getAction());
                updateContentBaseReq.setModifyBy(req.getUserId().toString());
                log.info("UsefulUselessManager action updateUsefulOrUseless, updateContentBaseReq = [{}]",
                    updateContentBaseReq);
                ResultVO resultVO = contentService.updateUsefulOrUseless(updateContentBaseReq);
                ResultVOCheckUtil.checkResultVO(resultVO);
                if (AbstractUsefulUselessDef.TYPE_ONE.equals(req.getType())) {
                    redisCache.setNumber(CacheKeyDef.USEFUL_KEY, req.getObjType() + req.getObjId(), "0");
                }
                else {
                    redisCache.setNumber(CacheKeyDef.USELESS_KEY, req.getObjType() + req.getObjId(), "0");
                }
            }
            // 更新评价表
            if (AbstractUsefulUselessDef.OBJTYPE_E.equals(req.getObjType())) {
                Evaluation evaluation = evaluationMapper.selectById(req.getObjId());
                releaseForUseful(req, sumNum, evaluation);
            }
        }
        else {
            // 阙值redis + 1
            releaseForIncr(req);
        }

        log.info("UsefulUselessManager action end");
    }

    private String releaseForGetStr(UsefulUselessReq req) {
        String keyNum;
        if (AbstractUsefulUselessDef.TYPE_ONE.equals(req.getType())) {
            keyNum = redisCache.getNumber(CacheKeyDef.USEFUL_KEY, req.getObjType() + req.getObjId());
        }
        else {
            keyNum = redisCache.getNumber(CacheKeyDef.USELESS_KEY, req.getObjType() + req.getObjId());
        }
        return keyNum;
    }

    private void releaseForDecr(UsefulUselessReq req) {
        if (AbstractUsefulUselessDef.TYPE_ONE.equals(req.getType())) {
            redisCache.decr(CacheKeyDef.USEFUL_KEY, req.getObjType() + req.getObjId());
        }
        else {
            redisCache.decr(CacheKeyDef.USELESS_KEY, req.getObjType() + req.getObjId());
        }
    }

    private void releaseForIncr(UsefulUselessReq req) {
        if (AbstractUsefulUselessDef.TYPE_ONE.equals(req.getType())) {
            redisCache.incr(CacheKeyDef.USEFUL_KEY, req.getObjType() + req.getObjId());
        }
        else {
            redisCache.incr(CacheKeyDef.USELESS_KEY, req.getObjType() + req.getObjId());
        }
    }

    private void releaseForUseful(UsefulUselessReq req, Integer sumNum, Evaluation evaluation) {
        log.info("UsefulUselessManager releaseForUseful, evaluation = [{}]", evaluation);

        Integer foo = sumNum;
        if (AbstractUsefulUselessDef.TYPE_ONE.equals(req.getType())) {
            Integer usefulCount = evaluation.getUsefulCount();
            if (null != usefulCount) {
                if (AbstractUsefulUselessDef.ACTION_A.equals(req.getAction())) {
                    foo = sumNum + usefulCount;
                }
                else {
                    foo = usefulCount - sumNum;
                }
            }
            log.info("UsefulUselessManager action updateUsefulCountById, foo = [{}]", foo);
            evaluationMapper.updateUsefulCountById(foo, req.getObjId());
            redisCache.setNumber(CacheKeyDef.USEFUL_KEY, req.getObjType() + req.getObjId(), "0");
        }
        else {
            Integer uselessCount = evaluation.getUselessCount();
            if (null != uselessCount) {
                if (AbstractUsefulUselessDef.ACTION_A.equals(req.getAction())) {
                    foo = sumNum + uselessCount;
                }
                else {
                    foo = uselessCount - sumNum;
                }
            }
            log.info("UsefulUselessManager action updateUselessCountById, foo = [{}]", foo);
            evaluationMapper.updateUselessCountById(foo, req.getObjId());
            redisCache.setNumber(CacheKeyDef.USELESS_KEY, req.getObjType() + req.getObjId(), "0");
        }
    }

}
