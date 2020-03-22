package indi.zk.mall.user.service;


import indi.zk.mall.user.DO.UserInfo;

/**
 * @author ZhengKai
 * @data 2020-03-22 00:33
 */
public interface UserService  {

    /**
     * 通过openId查询用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);

}
