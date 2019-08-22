package com.javaweb.michaelkai.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javaweb.michaelkai.service.MyFriendsService;
import com.javaweb.michaelkai.dao.MyFriendsMapper;
import com.javaweb.michaelkai.pojo.MyFriends;
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
public class MyFriendsServiceImpl implements MyFriendsService {

    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Override
    public MyFriends addMyFriends(MyFriends myFriends) {
        //myFriends.setId(AppUtil.randomId());
        myFriendsMapper.addMyFriends(myFriends);
        return myFriends;
    }

    @Override
    public Map<String, Object> getMyFriendsById(String id) {
        return myFriendsMapper.getMyFriendsById(id);
    }

    @Override
    public void editMyFriendsById(MyFriends myFriends) {
        myFriendsMapper.editMyFriendsById(myFriends);
    }

    @Override
    public void editMyFriendsByIds(MyFriends myFriends, List<String> ids) {

    }

    @Override
    public void delMyFriendsById(String id) {
        myFriendsMapper.delMyFriendsById(id);
    }

    @Override
    public void delMyFriendsByIds(List<String> ids) {
        myFriendsMapper.delMyFriendsByIds(ids);
    }

    @Override
    public PageInfo<Map<String, Object>> getMyFriendss(int pageNum, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = this.getMyFriendss(map);
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public List<Map<String, Object>> getMyFriendss(Map<String, Object> map) {
        return myFriendsMapper.getMyFriendss(map);
    }
}
