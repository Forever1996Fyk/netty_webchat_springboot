package com.javaweb.michaelkai.dao;

import java.util.List;
import java.util.Map;
import com.javaweb.michaelkai.pojo.ChatMsg;
import org.apache.ibatis.annotations.Param;


/**
 * 
 * 
 * @author YuKai Fan
 * @date 2019-08-22 09:48:04
 */
public interface ChatMsgMapper {



    /**
     * 新增
     * @param chatMsg 实体
     * @return
     */
    int addChatMsg(ChatMsg chatMsg);

    /**
     * 批量新增
     * @param list 实体集合
     */
    void addChatMsgs(@Param(value = "list") List<ChatMsg> list);

    /**
     * 根据id查询
     * @param id  主键
     * @return
     */
    Map<String, Object> getChatMsgById(String id);

    /**
     * 根据id修改
     * @param chatMsg 实体
     * @return
     */
    int editChatMsgById(ChatMsg chatMsg);

    /**
     * 批量修改
     *
     * @param chatMsg 实体
     * @param ids 主键集合
     */
    void editChatMsgByIds(@Param("map") ChatMsg chatMsg, @Param("list") List<String> ids);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int delChatMsgById(String id);

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    int delChatMsgByIds(List<String> ids);

    /**
     * 真删除
     *
     * @param id 主键
     * @return dao成功失败标志
     */
    int delChatMsgRealById(String id);

    /**
     * 真批量删除
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    int delChatMsgRealByIds(List<String> ids);

    /**
     * 全部真删除
     * @return
     */
    int delChatMsgReals();

    /**
     * 获取所有数据.
     * @param map 页面表单
     * @return 结果集合
     */
    List<Map<String, Object>> getChatMsgs(Map<String, Object> map);

}
