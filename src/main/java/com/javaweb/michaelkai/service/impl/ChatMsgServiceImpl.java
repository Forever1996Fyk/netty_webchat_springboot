package com.javaweb.michaelkai.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javaweb.michaelkai.service.ChatMsgService;
import com.javaweb.michaelkai.dao.ChatMsgMapper;
import com.javaweb.michaelkai.pojo.ChatMsg;
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
public class ChatMsgServiceImpl implements  ChatMsgService {

	@Autowired
	private ChatMsgMapper chatMsgMapper;

    @Override
    public ChatMsg addChatMsg(ChatMsg chatMsg) {
        //chatMsg.setId(AppUtil.randomId());
                                                                                                                                        chatMsgMapper.addChatMsg(chatMsg);
        return chatMsg;
    }

    @Override
    public Map<String, Object> getChatMsgById(String id) {
        return chatMsgMapper.getChatMsgById(id);
    }

    @Override
    public void editChatMsgById(ChatMsg chatMsg) {
        chatMsgMapper.editChatMsgById(chatMsg);
    }

    @Override
    public void editChatMsgByIds(ChatMsg chatMsg, List<String> ids) {

    }

    @Override
    public void delChatMsgById(String id) {
        chatMsgMapper.delChatMsgById(id);
    }

    @Override
    public void delChatMsgByIds(List<String> ids) {
        chatMsgMapper.delChatMsgByIds(ids);
    }

    @Override
    public PageInfo<Map<String, Object>> getChatMsgs(int pageNum, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = this.getChatMsgs(map);
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public List<Map<String, Object>> getChatMsgs(Map<String, Object> map) {
        return chatMsgMapper.getChatMsgs(map);
    }
}
