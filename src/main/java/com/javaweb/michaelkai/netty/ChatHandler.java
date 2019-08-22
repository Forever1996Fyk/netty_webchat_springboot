package com.javaweb.michaelkai.netty;

import com.javaweb.michaelkai.common.enums.MsgSignFlagEnum;
import com.javaweb.michaelkai.common.utils.JSONUtils;
import com.javaweb.michaelkai.common.utils.SpringContextUtil;
import com.javaweb.michaelkai.common.enums.MsgActionEnum;
import com.javaweb.michaelkai.netty.entity.ChatMsg;
import com.javaweb.michaelkai.netty.entity.DataContent;
import com.javaweb.michaelkai.netty.entity.UserChannelRel;
import com.javaweb.michaelkai.service.ChatMsgService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.util.StringUtils;

/**
 * @ClassName ChatHandler
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/21 22:17
 * @Version 1.0
 **/
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的channel
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * @return
     * @Description 处理消息的handler
     * @Author YuKai Fan
     * @Date 22:19 2019/8/21
     * @Param TextWebSocketFrame: 在netty中，适用于websocket专门处理文本的对象,frames是消息的载体
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的消息
        String content = msg.text();
        Channel currentChannel = ctx.channel();

        //1. 获取客户端传输发来的消息
        DataContent dataContent = JSONUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();
        //2. 判断消息类型，根据不同的类型来处理不同的业务
        if (action == MsgActionEnum.CONNECT.type) {
            //2.1 当webSocket第一次open的时候，初始化channel，把用户的channel和userId关联起来
            String sendUserId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(sendUserId, currentChannel);

            //测试
            for (Channel channel : users) {
                System.out.println(channel.id().asLongText());
            }

            UserChannelRel.output();

        } else if (action == MsgActionEnum.CHAT.type) {
            //2.2 聊天类型的消息, 把聊天记录保存到数据库, 同时标记消息的签收状态【未签收】
            ChatMsg chatMsg = dataContent.getChatMsg();
            String rerceiverId = chatMsg.getRerceiverId();
            String senderId = chatMsg.getSenderId();
            String msgText = chatMsg.getMsg();

            //保存消息到数据库, 并且标记为 未签收
            ChatMsgService chatMsgService = SpringContextUtil.getBean(ChatMsgService.class);
            com.javaweb.michaelkai.pojo.ChatMsg chatMsgDB = new com.javaweb.michaelkai.pojo.ChatMsg();
            chatMsgDB.setMsg(msgText);
            chatMsgDB.setSendUserId(senderId);
            chatMsgDB.setAcceptUserId(rerceiverId);
            chatMsgDB.setSignFlag(MsgSignFlagEnum.unsign.type);
            chatMsgDB = chatMsgService.addChatMsg(chatMsgDB);

            chatMsg.setMsgId(chatMsgDB.getId());

            //发送消息, 从全局用户Channel关系中获取接受方的channel
            Channel receiverChannel = UserChannelRel.get(rerceiverId);
            if (receiverChannel == null) {
                //TODO channel为空表示用户离线, 推送消息(第三方推送)
            } else {
                //当receiverChannel不为空, 从channelGroup去查找对应的channel是否存在
                Channel findChannel = users.find(receiverChannel.id());
                if (findChannel != null) {
                    //查找出来的channel不为空，表示用户在线
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(JSONUtils.objectToJson(chatMsg)));
                } else {
                    //用户离线 TODO 推送消息
                }
            }

        } else if (action == MsgActionEnum.SIGNED.type) {
            //2.3 签收消息类型，针对具体的消息进行签收，修改数据库中对应的消息的签收状态【已签收】, 并非用户已读未读
            ChatMsgService chatMsgService = SpringContextUtil.getBean(ChatMsgService.class);
            //扩展字段在signed类型的消息中，代表需要去签收的消息id，逗号间隔
            String msgIdStr = dataContent.getExtand();
            String[] msgIds = msgIdStr.split(",");
            for (String msgId : msgIds) {
                if (!StringUtils.isEmpty(msgId)) {
                    com.javaweb.michaelkai.pojo.ChatMsg chatMsg = new com.javaweb.michaelkai.pojo.ChatMsg();
                    chatMsg.setId(msgId);
                    chatMsg.setSignFlag(MsgSignFlagEnum.signed.type);
                    chatMsgService.editChatMsgById(chatMsg);
                }
            }

        } else if (action == MsgActionEnum.KEEPLIVE.type) {
            //2.4 心跳类型的消息

        }


    }

    /**
     * @return
     * @Description 在客户端创建完之后，如何客户端加入则触发handlerAdded
     * @Author YuKai Fan
     * @Date 22:22 2019/8/21
     * @Param
     **/
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //获取客户端的channel，并放到ChannelGroup中进行管理
        users.add(ctx.channel());
    }

    /**
     * @return
     * @Description 在客户端创建完之后，如果客户端离开则触发handlerRemoved
     * @Author YuKai Fan
     * @Date 22:22 2019/8/21
     * @Param
     **/
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemove, ChannelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
        //System.out.println("客户端断开，channel对应的长id：" + ctx.channel().id().asLongText());
        //System.out.println("客户端断开，channel对应的短id：" + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生异常之后，关闭连接(关闭channel)，随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
