window.app = {
    nettyServerUrl: 'ws://192.168.0.108:8088/ws',

    //和后端的枚举对应
    connect: 1,
    chat: 2,
    signed: 3,
    keepalive: 4,

    /**
     * 和后端的ChatMsg聊天模型对象一致
     * @param senderId
     * @param rerceiverId
     * @param msg
     * @param msgId
     * @constructor
     */
    ChatMsg: function (senderId, rerceiverId, msg, msgId) {
        this.senderId = senderId;
        this.rerceiverId = rerceiverId;
        this.msg = msg;
        this.msgId = msgId;
    },

    /**
     * 和后端的DataContent数据内容对象一致
     * @param action
     * @param chatMsg
     * @param extand
     * @constructor
     */
    DataContent: function (action, chatMsg, extand) {
        this.action = action;
        this.chatMsg = chatMsg;
        this.extand = extand;
    }
};


window.Chat = {
    socket: null,
    init: function () {
        if (window.WebSocket) {
            //如果当前的状态已经连接，那就不需要重复初始化webSocket
            if(Chat.socket != null
                && Chat.socket != undefined
                && Chat.socket.readyState == WebSocket.OPEN) {
                return false;
            }
            Chat.socket = new WebSocket(app.nettyServerUrl);
            Chat.socket.onopen = Chat.wsOpen;
            Chat.socket.onclose = Chat.wsClose;
            Chat.socket.onerror = Chat.wsError;
            Chat.socket.onmessage = Chat.wsMessage
        } else {
            alert("手机设备过久");
        }
    },
    chat: function (msg) {

        var userId = $("#userId").val();
        var reciverId = $("#reciverId").val();
        var chatMsg = new app.ChatMsg(userId, reciverId, msg, null);
        var dataContent = new app.DataContent(app.chat, chatMsg, null);

        //如果当前websocket的状态是已经打开，则直接发送，否则重连
        if(Chat.socket != null
            && Chat.socket != undefined
            && Chat.socket.readyState == WebSocket.OPEN) {

            Chat.socket.send(JSON.stringify(dataContent));
        } else {
            //重连webSocket
            Chat.init();
            setTimeout("Chat.reChat('"+ JSON.stringify(dataContent) +"')")
            Chat.socket.send(JSON.stringify(dataContent));
        }
    },
    reChat: function(msg) {
        console.log("消息重新发送。。。");
        Chat.socket.send(msg);
    },
    wsOpen: function () {
        console.log("连接建立。。。");
        var userId = $("#userId").val();
        var chatMsg = new app.ChatMsg(userId, null, null, null);
        var dataContent = new app.DataContent(app.connect, chatMsg, null);
        //发送websocket
        Chat.chat(JSON.stringify(dataContent));
    },
    wsClose: function () {
        console.log("连接关闭。。。");
    },
    wsError: function () {
        console.log("连接出错。。。");
    },
    wsMessage: function (e) {
        var textContent = e.data;
        //var textContent = $(".div-textarea").html().replace(/[\n\r]/g, '<br>')
        if (textContent != "") {
            $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
                "<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
                "<div class=\"right\"> <div class=\"chat-message\"> " + textContent + " </div> " +
                "<div class=\"chat-avatars\"><img src=\"images/img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
            //发送后清空输入框
            $(".div-textarea").html("");
            //聊天框默认最底部
            $(document).ready(function () {
                $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
            });
        }
    }
};

Chat.init();