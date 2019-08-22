package com.javaweb.michaelkai.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @ClassName ChatHandler
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/21 22:17
 * @Version 1.0
 **/
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的channel
    private static ChannelGroup client = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * @Description 处理消息的handler
     *
     * @Author YuKai Fan
     * @Date 22:19 2019/8/21
     * @Param TextWebSocketFrame: 在netty中，适用于websocket专门处理文本的对象,frames是消息的载体
     * @return
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的消息
        String text = msg.text();
        System.out.println("接收到的数据：" + text);

        for (Channel channel : client) {
            //将消息放进缓存，并刷新到客户端
            channel.writeAndFlush(new TextWebSocketFrame("[服务器在]" + LocalDateTime.now() + " 接收到消息, 消息为：" + text));
        }

        //这个方法与上面的for循环一致
        //client.writeAndFlush(new TextWebSocketFrame("[服务器在]" + LocalDateTime.now() + " 接收到消息, 消息为：" + text));
    }

    /**
     * @Description 在客户端创建完之后，如何客户端加入则触发handlerAdded
     *
     * @Author YuKai Fan
     * @Date 22:22 2019/8/21
     * @Param
     * @return
     **/
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //获取客户端的channel，并放到ChannelGroup中进行管理
        client.add(ctx.channel());
    }

    /**
     * @Description 在客户端创建完之后，如果客户端离开则触发handlerRemoved
     *
     * @Author YuKai Fan
     * @Date 22:22 2019/8/21
     * @Param
     * @return
     **/
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemove, ChannelGroup会自动移除对应客户端的channel
        //client.remove(ctx.channel());
        System.out.println("客户端断开，channel对应的长id：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应的短id：" + ctx.channel().id().asShortText());
    }
}
