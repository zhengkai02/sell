package indi.zk.mall.demo.controller;

import indi.zk.mall.demo.entity.User;
import indi.zk.mall.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-03-11 23:59:16
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public User selectOne(Integer id) {
        return this.userService.queryById(id);
    }

    @GetMapping("all")
    public List<User> queryAllByLimit(int offset, int limit){
        return this.userService.queryAllByLimit(offset,limit);
    }

}