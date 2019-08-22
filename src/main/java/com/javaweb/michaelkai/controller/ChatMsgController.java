package com.javaweb.michaelkai.controller;

import com.javaweb.michaelkai.common.enums.ResultEnum;
import com.javaweb.michaelkai.common.vo.Result;
import com.javaweb.michaelkai.pojo.ChatMsg;
import com.javaweb.michaelkai.service.ChatMsgService;
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
public class ChatMsgController {
    @Autowired
    private ChatMsgService chatMsgService;

    /**
     * 添加
     *
     * @param chatMsg
     * @return
     */
    @PostMapping("/chatMsg")
    public Result addChatMsg(@RequestBody ChatMsg chatMsg) {
        return new Result(true, ResultEnum.SUCCESS.getValue(), "新增" + ResultEnum.SUCCESS.getMessage(), chatMsgService.addChatMsg(chatMsg));
    }

    /**
     * 编辑修改
     *
     * @param chatMsg
     * @return
     */
    @PutMapping("/chatMsg")
    public Result editChatMsgById(@RequestBody ChatMsg chatMsg) {
        chatMsgService.editChatMsgById(chatMsg);
        return new Result(true, ResultEnum.SUCCESS.getValue(), "修改" + ResultEnum.SUCCESS.getMessage(), chatMsg);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/chatMsg")
    public Result delChatMsgById(@RequestParam String id) {
        chatMsgService.delChatMsgById(id);
        return new Result(true, ResultEnum.SUCCESS.getValue(), "删除" + ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 根据id批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/chatMsgs/{ids}")
    public Result delChatMsgByIds(@PathVariable("ids") String[] ids) {
        chatMsgService.delChatMsgByIds(Arrays.asList(ids));
        return new Result(true, ResultEnum.SUCCESS.getValue(), "批量删除" + ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 获取所有(不分页)
     *
     * @param map 参数
     * @return
     */
    @GetMapping("/getChatMsgs/noPage")
    public Result getChatMsgs(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = chatMsgService.getChatMsgs(map);
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
    @GetMapping("/getChatMsgs")
    public Result getChatMsgs(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                              @RequestParam(value = "limit", required = false, defaultValue = "0") int limit,
                              @RequestParam Map<String, Object> map) {
        PageInfo<Map<String, Object>> pageList = chatMsgService.getChatMsgs(page, limit, map);
        return new Result(true, ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getMessage(), new PageResult<>(pageList.getTotal(), pageList.getList()));
    }


}
