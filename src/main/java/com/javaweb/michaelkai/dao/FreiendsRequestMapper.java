package com.javaweb.michaelkai.dao;

import java.util.List;
import java.util.Map;
import com.javaweb.michaelkai.pojo.FreiendsRequest;
import org.apache.ibatis.annotations.Param;


/**
 * 
 * 
 * @author YuKai Fan
 * @date 2019-08-22 09:48:04
 */
public interface FreiendsRequestMapper {



    /**
     * 新增
     * @param freiendsRequest 实体
     * @return
     */
    int addFreiendsRequest(FreiendsRequest freiendsRequest);

    /**
     * 批量新增
     * @param list 实体集合
     */
    void addFreiendsRequests(@Param(value = "list") List<FreiendsRequest> list);

    /**
     * 根据id查询
     * @param id  主键
     * @return
     */
    Map<String, Object> getFreiendsRequestById(String id);

    /**
     * 根据id修改
     * @param freiendsRequest 实体
     * @return
     */
    int editFreiendsRequestById(FreiendsRequest freiendsRequest);

    /**
     * 批量修改
     *
     * @param freiendsRequest 实体
     * @param ids 主键集合
     */
    void editFreiendsRequestByIds(@Param("map") FreiendsRequest freiendsRequest, @Param("list") List<String> ids);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int delFreiendsRequestById(String id);

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    int delFreiendsRequestByIds(List<String> ids);

    /**
     * 真删除
     *
     * @param id 主键
     * @return dao成功失败标志
     */
    int delFreiendsRequestRealById(String id);

    /**
     * 真批量删除
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    int delFreiendsRequestRealByIds(List<String> ids);

    /**
     * 全部真删除
     * @return
     */
    int delFreiendsRequestReals();

    /**
     * 获取所有数据.
     * @param map 页面表单
     * @return 结果集合
     */
    List<Map<String, Object>> getFreiendsRequests(Map<String, Object> map);

}
