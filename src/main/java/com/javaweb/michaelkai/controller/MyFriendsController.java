package com.javaweb.michaelkai.controller;

import com.javaweb.michaelkai.common.enums.ResultEnum;
import com.javaweb.michaelkai.common.vo.Result;
import com.javaweb.michaelkai.pojo.MyFriends;
import com.javaweb.michaelkai.service.MyFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import com.javaweb.michaelkai.common.vo.PageResult;

import java.util.Arrays;
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
public class MyFriendsController {
    @Autowired
    private MyFriendsService myFriendsService;

    /**
     * 添加
     *
     * @param myFriends
     * @return
     */
    @PostMapping("/myFriends")
    public Result addMyFriends(@RequestBody MyFriends myFriends) {
        return new Result(true, ResultEnum.SUCCESS.getValue(), "新增" + ResultEnum.SUCCESS.getMessage(), myFriendsService.addMyFriends(myFriends));
    }

    /**
     * 编辑修改
     *
     * @param myFriends
     * @return
     */
    @PutMapping("/myFriends")
    public Result editMyFriendsById(@RequestBody MyFriends myFriends) {
        myFriendsService.editMyFriendsById(myFriends);
        return new Result(true, ResultEnum.SUCCESS.getValue(), "修改" + ResultEnum.SUCCESS.getMessage(), myFriends);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/myFriends")
    public Result delMyFriendsById(@RequestParam String id) {
        myFriendsService.delMyFriendsById(id);
        return new Result(true, ResultEnum.SUCCESS.getValue(), "删除" + ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 根据id批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/myFriendss/{ids}")
    public Result delMyFriendsByIds(@PathVariable("ids") String[] ids) {
        myFriendsService.delMyFriendsByIds(Arrays.asList(ids));
        return new Result(true, ResultEnum.SUCCESS.getValue(), "批量删除" + ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 获取所有(不分页)
     *
     * @param map 参数
     * @return
     */
    @GetMapping("/getMyFriendss/noPage")
    public Result getMyFriendss(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = myFriendsService.getMyFriendss(map);
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
    @GetMapping("/getMyFriendss")
    public Result getMyFriendss(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "limit", required = false, defaultValue = "0") int limit,
                                @RequestParam Map<String, Object> map) {
        PageInfo<Map<String, Object>> pageList = myFriendsService.getMyFriendss(page, limit, map);
        return new Result(true, ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getMessage(), new PageResult<>(pageList.getTotal(), pageList.getList()));
    }


}
