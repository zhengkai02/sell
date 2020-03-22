package indi.zk.mall.user.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author ZhengKai
 * @data 2020-03-22 00:29
 */
@Data
@Entity
public class UserInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

    private Integer role;

}
