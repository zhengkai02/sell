package indi.zk.mall.user.service.impl;

import indi.zk.mall.user.DO.UserInfo;
import indi.zk.mall.user.repository.UserInfoRepository;
import indi.zk.mall.user.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhengKai
 * @data 2020-03-22 00:36
 */
@Service
@Data
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 通过openId查询用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}
