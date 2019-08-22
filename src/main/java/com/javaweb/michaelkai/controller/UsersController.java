package com.javaweb.michaelkai.controller;

import com.javaweb.michaelkai.common.enums.ResultEnum;
import com.javaweb.michaelkai.common.utils.FormUtil;
import com.javaweb.michaelkai.common.vo.Result;
import com.javaweb.michaelkai.pojo.Users;
import com.javaweb.michaelkai.pojo.vo.UsersVO;
import com.javaweb.michaelkai.service.UsersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import com.javaweb.michaelkai.common.vo.PageResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: project_base
 * @description:
 * @author: YuKai Fan
 * @create: 2019-08-22 09:48:04
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class UsersController {
    @Autowired
    private UsersService usersService;

    /**
     * 添加
     *
     * @param users
     * @return
     */
    @PostMapping("/users")
    public Result addUsers(@RequestBody Users users) {
        return new Result(true, ResultEnum.SUCCESS.getValue(), "新增" + ResultEnum.SUCCESS.getMessage(), usersService.addUsers(users));
    }

    /**
     * 编辑修改
     *
     * @param users
     * @return
     */
    @PutMapping("/users")
    public Result editUsersById(@RequestBody Users users) {
        usersService.editUsersById(users);
        return new Result(true, ResultEnum.SUCCESS.getValue(), "修改" + ResultEnum.SUCCESS.getMessage(), users);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/users")
    public Result delUsersById(@RequestParam String id) {
        usersService.delUsersById(id);
        return new Result(true, ResultEnum.SUCCESS.getValue(), "删除" + ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 根据id批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/userss/{ids}")
    public Result delUsersByIds(@PathVariable("ids") String[] ids) {
        usersService.delUsersByIds(Arrays.asList(ids));
        return new Result(true, ResultEnum.SUCCESS.getValue(), "批量删除" + ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 获取所有(不分页)
     *
     * @param map 参数
     * @return
     */
    @GetMapping("/getUserss/noPage")
    public Result getUserss(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = usersService.getUserss(map);
        return new Result(true, ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getMessage(), list);
    }

    /**
     * 获取所有
     *
     * @param page  开始记录
     * @param limit 分页大小
     * @param map   参数
     * @return
     */
    @GetMapping("/getUserss")
    public Result getUserss(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                            @RequestParam(value = "limit", required = false, defaultValue = "0") int limit,
                            @RequestParam Map<String, Object> map) {
        PageInfo<Map<String, Object>> pageList = usersService.getUserss(page, limit, map);
        return new Result(true, ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getMessage(), new PageResult<>(pageList.getTotal(), pageList.getList()));
    }

    /**
     * 用户注册或登录
     *
     * @param users
     * @return
     */
    @PostMapping("/registOrLogin")
    public Result registOrLogin(@RequestBody Users users) {
        if (!StringUtils.isEmpty(users.getUserName())
                || !StringUtils.isEmpty(users.getPassword())) {

            Map<String, Object> map = new HashMap<>();
            map.put("userName", users.getUserName());
            List<Map<String, Object>> userList = usersService.getUserss(map);
            Users userResult = null;
            if (CollectionUtils.isEmpty(userList)) {
                //注册
                users.setNickName(users.getUserName());
                users.setFaceImage("");
                users.setFaceImageBig("");

                userResult = usersService.addUsers(users);
            } else {
                //登录
                map.put("password", users.getPassword());
                userList = usersService.getUserss(map);
                if (!CollectionUtils.isEmpty(userList)) {
                    Users populate = FormUtil.populate(Users.class, userList.get(0), false);
                }
            }

            UsersVO usersVO = new UsersVO();

            return new Result(false, ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getMessage(), usersVO);

        }

        return new Result(false, ResultEnum.USER_NAME_PWD_NULL.getValue(), ResultEnum.USER_NAME_PWD_NULL.getMessage());
    }


}
