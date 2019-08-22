package com.javaweb.michaelkai.dao;

import java.util.List;
import java.util.Map;
import com.javaweb.michaelkai.pojo.MyFriends;
import org.apache.ibatis.annotations.Param;


/**
 * 
 * 
 * @author YuKai Fan
 * @date 2019-08-22 09:48:04
 */
public interface MyFriendsMapper {



    /**
     * 新增
     * @param myFriends 实体
     * @return
     */
    int addMyFriends(MyFriends myFriends);

    /**
     * 批量新增
     * @param list 实体集合
     */
    void addMyFriendss(@Param(value = "list") List<MyFriends> list);

    /**
     * 根据id查询
     * @param id  主键
     * @return
     */
    Map<String, Object> getMyFriendsById(String id);

    /**
     * 根据id修改
     * @param myFriends 实体
     * @return
     */
    int editMyFriendsById(MyFriends myFriends);

    /**
     * 批量修改
     *
     * @param myFriends 实体
     * @param ids 主键集合
     */
    void editMyFriendsByIds(@Param("map") MyFriends myFriends, @Param("list") List<String> ids);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int delMyFriendsById(String id);

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    int delMyFriendsByIds(List<String> ids);

    /**
     * 真删除
     *
     * @param id 主键
     * @return dao成功失败标志
     */
    int delMyFriendsRealById(String id);

    /**
     * 真批量删除
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    int delMyFriendsRealByIds(List<String> ids);

    /**
     * 全部真删除
     * @return
     */
    int delMyFriendsReals();

    /**
     * 获取所有数据.
     * @param map 页面表单
     * @return 结果集合
     */
    List<Map<String, Object>> getMyFriendss(Map<String, Object> map);

}
