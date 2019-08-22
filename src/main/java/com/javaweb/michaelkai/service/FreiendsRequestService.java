package com.javaweb.michaelkai.service;

import com.javaweb.michaelkai.pojo.FreiendsRequest;
import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

 /**
  * @program: project_base
  * @description: 
  * @author: YuKai Fan
  * @create: 2019-08-22 09:48:04
  **/
public interface FreiendsRequestService {
	

   /**
     * 新增
     * @param freiendsRequest 实体
     * @return
     */
    FreiendsRequest addFreiendsRequest(FreiendsRequest freiendsRequest);

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
    void editFreiendsRequestById(FreiendsRequest freiendsRequest);

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
    void delFreiendsRequestById(String id);

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    void delFreiendsRequestByIds(List<String> ids);

    /**
     * 获取所有(分页)
     * @param start 开始记录
     * @param pageSize 分页大小
     * @param map 参数
     * @return
     */
    PageInfo<Map<String, Object>> getFreiendsRequests(int start, int pageSize, Map<String, Object> map);

    /**
     * 获取所有数据.
     * @param map 页面表单
     * @return 结果集合
     */
    List<Map<String, Object>> getFreiendsRequests(Map<String, Object> map);
}
