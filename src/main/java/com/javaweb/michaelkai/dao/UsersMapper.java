package com.javaweb.michaelkai.dao;

import java.util.List;
import java.util.Map;
import com.javaweb.michaelkai.pojo.Users;
import org.apache.ibatis.annotations.Param;


/**
 * 
 * 
 * @author YuKai Fan
 * @date 2019-08-22 09:48:04
 */
public interface UsersMapper {



    /**
     * 新增
     * @param users 实体
     * @return
     */
    int addUsers(Users users);

    /**
     * 批量新增
     * @param list 实体集合
     */
    void addUserss(@Param(value = "list") List<Users> list);

    /**
     * 根据id查询
     * @param id  主键
     * @return
     */
    Map<String, Object> getUsersById(String id);

    /**
     * 根据id修改
     * @param users 实体
     * @return
     */
    int editUsersById(Users users);

    /**
     * 批量修改
     *
     * @param users 实体
     * @param ids 主键集合
     */
    void editUsersByIds(@Param("map") Users users, @Param("list") List<String> ids);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int delUsersById(String id);

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    int delUsersByIds(List<String> ids);

    /**
     * 真删除
     *
     * @param id 主键
     * @return dao成功失败标志
     */
    int delUsersRealById(String id);

    /**
     * 真批量删除
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    int delUsersRealByIds(List<String> ids);

    /**
     * 全部真删除
     * @return
     */
    int delUsersReals();

    /**
     * 获取所有数据.
     * @param map 页面表单
     * @return 结果集合
     */
    List<Map<String, Object>> getUserss(Map<String, Object> map);

}
