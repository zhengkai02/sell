package indi.zk.mall.user.controller;

import indi.zk.mall.user.DO.UserInfo;
import indi.zk.mall.user.VO.ResultVO;
import indi.zk.mall.user.constants.CookieConstant;
import indi.zk.mall.user.constants.RedisConstant;
import indi.zk.mall.user.enums.ResultEnum;
import indi.zk.mall.user.enums.RoleEnum;
import indi.zk.mall.user.service.UserService;
import indi.zk.mall.user.utils.CookieUtil;
import indi.zk.mall.user.utils.ResultVOUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhengKai
 * @data 2020-03-22 00:38
 */
@RestController
@RequestMapping("/login")
@Data
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 买家登录
     *
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam String openid, HttpServletResponse response) {

        // 1. openId和数据库里面的数据是否匹配
        // TODO
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        // 2. 判断角色
        if (RoleEnum.BUYER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.PRODUCT_STAOCK_ERROR);
        }
        // 3. cookie里设置openId=abc
        CookieUtil.set(response, CookieConstant.OPENID, openid, CookieConstant.expire);
        return ResultVOUtil.success();
    }

    /**
     * 卖家登录
     *
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam String openid, HttpServletRequest request, HttpServletResponse response) {

        //判断是否已登录
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null &&
                StringUtils.isNotEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
            return ResultVOUtil.success();

        }
        //1. openid和数据库里面的数据是否匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return  ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2. 判断角色
        if (RoleEnum.SELLER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.PRODUCT_STAOCK_ERROR);
        }

        //3. redis设置key=UUID， value=xyz
        String token = UUID.randomUUID().toString();
        int expire = CookieConstant.expire;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE, token),
                openid,
                expire,
                TimeUnit.SECONDS);
        //4. cookie里设置openid=xyz
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.expire);
        // TODO
        return ResultVOUtil.success();
    }
}
