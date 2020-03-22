package com.iwhalecloud.retail.offer.repository;

import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.common.consts.SystemParamMaskDef;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.dto.client.req.QueryUserReq;
import com.iwhalecloud.retail.offer.dto.client.resp.QueryUserRsp;
import com.iwhalecloud.retail.offer.entity.SystemParam;
import com.iwhalecloud.retail.offer.entity.TmpUser;
import com.iwhalecloud.retail.offer.mapper.SystemParamMapper;
import com.iwhalecloud.retail.offer.mapper.TmpUserMapper;
import com.iwhalecloud.retail.offer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by xh on 2019/7/23.
 */
@Slf4j
@Repository
public class UserRepository {

    @Autowired
    private UserService userService;

    @Autowired
    private ICache redisCache;

    @Autowired
    private SystemParamMapper systemParamMapper;

    @Autowired
    private TmpUserMapper tmpUserMapper;

    public QueryUserRsp queryUser(String userId) throws BaseException {
        QueryUserRsp queryUserResp = (QueryUserRsp) redisCache.get(CacheKeyDef.USER_INFO, userId);
        if (queryUserResp == null) {

            //默认为调用户中心的接口
            SystemParam systemParam = systemParamMapper.selectSystemParamByMask(SystemParamMaskDef.USER_CENTER_FLAG);
            if (systemParam != null && "N".equalsIgnoreCase(systemParam.getCurrentValue())) {
                TmpUser user = tmpUserMapper.selectById(userId);
                if (user != null) {
                    queryUserResp = new QueryUserRsp();
                    queryUserResp.setUserId(userId);
                    queryUserResp.setUsername(user.getUserName());
                }
            }
            else {
                QueryUserReq queryUserReq = new QueryUserReq();
                queryUserReq.setUserId(userId);
                queryUserReq.setRequestId(UidGeneator.getUID());
                ResultVO<QueryUserRsp> respResultVO = userService.queryUserDetail(queryUserReq);
                ResultVOCheckUtil.checkResultVO(respResultVO);
                queryUserResp = respResultVO.getData();
            }
            redisCache.set(CacheKeyDef.USER_INFO, userId, queryUserResp);

        }
        return queryUserResp;
    }
}
