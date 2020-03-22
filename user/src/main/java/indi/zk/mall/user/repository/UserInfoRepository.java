package indi.zk.mall.user.repository;

import indi.zk.mall.user.DO.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ZhengKai
 * @data 2020-03-22 00:31
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo findByOpenid(String openid);

}
