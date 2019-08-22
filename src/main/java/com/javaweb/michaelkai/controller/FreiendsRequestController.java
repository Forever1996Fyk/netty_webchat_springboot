package com.javaweb.michaelkai.controller;

import com.javaweb.michaelkai.common.enums.ResultEnum;
import com.javaweb.michaelkai.common.vo.Result;
import com.javaweb.michaelkai.pojo.FreiendsRequest;
import com.javaweb.michaelkai.service.FreiendsRequestService;
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
public class FreiendsRequestController {
    @Autowired
    private FreiendsRequestService freiendsRequestService;

    /**
     * 添加
     *
     * @param freiendsRequest
     * @return
     */
    @PostMapping("/freiendsRequest")
    public Result addFreiendsRequest(@RequestBody FreiendsRequest freiendsRequest) {
        return new Result(true, ResultEnum.SUCCESS.getValue(), "新增" + ResultEnum.SUCCESS.getMessage(), freiendsRequestService.addFreiendsRequest(freiendsRequest));
    }

    /**
     * 编辑修改
     *
     * @param freiendsRequest
     * @return
     */
    @PutMapping("/freiendsRequest")
    public Result editFreiendsRequestById(@RequestBody FreiendsRequest freiendsRequest) {
        freiendsRequestService.editFreiendsRequestById(freiendsRequest);
        return new Result(true, ResultEnum.SUCCESS.getValue(), "修改" + ResultEnum.SUCCESS.getMessage(), freiendsRequest);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/freiendsRequest")
    public Result delFreiendsRequestById(@RequestParam String id) {
        freiendsRequestService.delFreiendsRequestById(id);
        return new Result(true, ResultEnum.SUCCESS.getValue(), "删除" + ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 根据id批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/freiendsRequests/{ids}")
    public Result delFreiendsRequestByIds(@PathVariable("ids") String[] ids) {
        freiendsRequestService.delFreiendsRequestByIds(Arrays.asList(ids));
        return new Result(true, ResultEnum.SUCCESS.getValue(), "批量删除" + ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 获取所有(不分页)
     *
     * @param map 参数
     * @return
     */
    @GetMapping("/getFreiendsRequests/noPage")
    public Result getFreiendsRequests(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = freiendsRequestService.getFreiendsRequests(map);
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
    @GetMapping("/getFreiendsRequests")
    public Result getFreiendsRequests(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                      @RequestParam(value = "limit", required = false, defaultValue = "0") int limit,
                                      @RequestParam Map<String, Object> map) {
        PageInfo<Map<String, Object>> pageList = freiendsRequestService.getFreiendsRequests(page, limit, map);
        return new Result(true, ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getMessage(), new PageResult<>(pageList.getTotal(), pageList.getList()));
    }


}
