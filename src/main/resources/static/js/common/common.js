/**
 * 通用配置js
 * Created by Administrator on 2019/6/10.
 */
layui.config({
    base: ctxPath + '/lib/dist/'
}).extend({
    //消息提示框插件
    notice: 'notice'
});

var curUserId = $("#curUserId").val()
    , socket;

layui.use(['element', 'layer', 'form', 'notice'], function () {
    var layer = layui.layer
        , notice = layui.notice;
    socket = io.connect('http://localhost:9090?userId=' + curUserId);
    socket.on('connect', function () {
        notice.success("客户端已连接");
    });

    socket.on('chatEvent', function (data) {
        notice.info(data);
    });

    socket.on('disconnect', function () {
        notice.warning("连接已关闭");
    });

});

