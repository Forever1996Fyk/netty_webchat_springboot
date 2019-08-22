package com.javaweb.michaelkai.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javaweb.michaelkai.service.FreiendsRequestService;
import com.javaweb.michaelkai.dao.FreiendsRequestMapper;
import com.javaweb.michaelkai.pojo.FreiendsRequest;
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
public class FreiendsRequestServiceImpl implements FreiendsRequestService {

    @Autowired
    private FreiendsRequestMapper freiendsRequestMapper;

    @Override
    public FreiendsRequest addFreiendsRequest(FreiendsRequest freiendsRequest) {
        //freiendsRequest.setId(AppUtil.randomId());
        freiendsRequestMapper.addFreiendsRequest(freiendsRequest);
        return freiendsRequest;
    }

    @Override
    public Map<String, Object> getFreiendsRequestById(String id) {
        return freiendsRequestMapper.getFreiendsRequestById(id);
    }

    @Override
    public void editFreiendsRequestById(FreiendsRequest freiendsRequest) {
        freiendsRequestMapper.editFreiendsRequestById(freiendsRequest);
    }

    @Override
    public void editFreiendsRequestByIds(FreiendsRequest freiendsRequest, List<String> ids) {

    }

    @Override
    public void delFreiendsRequestById(String id) {
        freiendsRequestMapper.delFreiendsRequestById(id);
    }

    @Override
    public void delFreiendsRequestByIds(List<String> ids) {
        freiendsRequestMapper.delFreiendsRequestByIds(ids);
    }

    @Override
    public PageInfo<Map<String, Object>> getFreiendsRequests(int pageNum, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = this.getFreiendsRequests(map);
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public List<Map<String, Object>> getFreiendsRequests(Map<String, Object> map) {
        return freiendsRequestMapper.getFreiendsRequests(map);
    }
}
