package com.javaweb.michaelkai.service.impl;

import com.github.pagehelper.PageHelper;
import com.javaweb.michaelkai.common.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javaweb.michaelkai.service.UsersService;
import com.javaweb.michaelkai.dao.UsersMapper;
import com.javaweb.michaelkai.pojo.Users;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


/**
 * @program: project_base
 * @description:
 * @author: YuKai Fan
 * @create: 2019-08-22 09:48:04
 **/
@Service
@Transactional
@SuppressWarnings("SpringJavaAutowiringInspection")
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users addUsers(Users users) {
        users.setId(AppUtil.randomId());
        users.setQrcode("");
        usersMapper.addUsers(users);
        return users;
    }

    @Override
    public Map<String, Object> getUsersById(String id) {
        return usersMapper.getUsersById(id);
    }

    @Override
    public void editUsersById(Users users) {
        usersMapper.editUsersById(users);
    }

    @Override
    public void editUsersByIds(Users users, List<String> ids) {

    }

    @Override
    public void delUsersById(String id) {
        usersMapper.delUsersById(id);
    }

    @Override
    public void delUsersByIds(List<String> ids) {
        usersMapper.delUsersByIds(ids);
    }

    @Override
    public PageInfo<Map<String, Object>> getUserss(int pageNum, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = this.getUserss(map);
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public List<Map<String, Object>> getUserss(Map<String, Object> map) {
        return usersMapper.getUserss(map);
    }
}
