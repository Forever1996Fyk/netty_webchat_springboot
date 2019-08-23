window.app = {
    nettyServerUrl: 'ws://localhost:8088/ws',

    //和后端的枚举对应
    connect: 1,
    chat: 2,
    signed: 3,
    keepalive: 4,

    //是否自己发送的消息
    isMe: 1,
    isFriend: 2,

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
    },

    /**
     * 单个聊天记录的对象
     * @param myId
     * @param friendId
     * @param msg
     * @param flag
     * @constructor
     */
    ChatHistory: function(myId, friendId, msg, flag) {
        this.myId = myId;
        this.friendId = friendId;
        this.msg = msg;
        this.flag = flag;
    },

    /**
     * 聊天快照的对象
     * @param myId
     * @param friendId
     * @param msg
     * @param isRead 判断消息是否已读
     * @constructor
     */
    ChatSnapShot: function(myId, friendId, msg, isRead) {
        this.myId = myId;
        this.friendId = friendId;
        this.msg = msg;
        this.isRead = isRead;
    },

    /**
     * 保存用户的聊天记录
     * @param myId
     * @param friendId
     * @param msg
     * @param flag 判断本条消息是我发送的，还是朋友发送的 1：我，2：朋友
     */
    saveUserChatHistory: function (myId, friendId, msg, flag) {
        var me = this;
        var chatKey = "chat-" + myId + "-" + friendId;

        //从本地缓存获取聊天记录是否存在
        var chatHistoryListStr = localStorage.getItem(chatKey);
        var chatHistoryList;
        if (chatHistoryListStr != null && chatHistoryListStr != ""
            && chatHistoryListStr != undefined) {

            //如果不为空
            chatHistoryList = JSON.parse(chatHistoryListStr);
        } else {
            //如果为空
            chatHistoryList = [];
        }

        //构建聊天记录对象
        var singleMsg = new me.ChatHistory(myId, friendId, msg, flag);

        //向list中追加msg对象
        chatHistoryList.push(singleMsg);

        //存入缓存
        localStorage.setItem(chatKey, JSON.stringify(chatHistoryList));
    },

    /**
     * 获取用户本地缓存历史聊天内容
     * @param myId
     * @param friendId
     * @param msg
     * @param flag
     */
    getUserChatHistory: function (myId, friendId) {
        var me = this;
        var chatKey = "chat-" + myId + "-" + friendId;
        var chatHistoryListStr = localStorage.getItem(chatKey);
        var chatHistoryList;
        if (chatHistoryListStr != null && chatHistoryListStr != ""
            && chatHistoryListStr != undefined) {

            //如果不为空
            chatHistoryList = JSON.parse(chatHistoryListStr);
        } else {
            //如果为空
            chatHistoryList = [];
        }

        return chatHistoryList;
    },

    /**
     * 聊天记录的快照，仅仅保存每次和朋友聊天的最后一条消息
     * @param myId
     * @param friendId
     * @param msg
     * @param isRead true:已读, false:未读
     */
    saveUserChatSnapShot: function (myId, friendId, msg, isRead) {
        var me = this;
        var chatKey = "chat-snapshot" + myId;

        //从本地缓存获取聊天快照的list
        var chatSnapShotListStr = localStorage.getItem(chatKey);
        var chatSnapShotList;
        if (chatSnapShotListStr != null && chatSnapShotListStr != ""
            && chatSnapShotListStr != undefined) {

            //如果不为空
            chatSnapShotList = JSON.parse(chatSnapShotListStr);
            //循环快照list, 并且判断每个元素是否包含(匹配)friendId, 如果匹配，则删除
            for (var i = 0; i < chatSnapShotList.length; i++) {
                if (chatSnapShotList[i].friendId === friendId) {
                    //删除已经存在的friendId所对应的快照对象
                    chatSnapShotList.splice(i, 1);
                    break;
                }
            }
        } else {
            //如果为空
            chatSnapShotList = [];
        }

        //构建聊天记录对象
        var singleMsg = new me.ChatSnapShot(myId, friendId, msg, isRead);

        chatSnapShotList.unshift(singleMsg);

        //存入缓存
        localStorage.setItem(chatKey, JSON.stringify(chatSnapShotList));
    },

    /**
     * 获取用户本地缓存的聊天快照列表
     * @param myId
     * @param friendId
     * @param msg
     * @param flag
     */
    getUserChatSnapShot: function (myId) {
        var me = this;
        var chatKey = "chat-snapshot" + myId;
        var chatSnapShotListStr = localStorage.getItem(chatKey);
        var chatSnapShotList;
        if (chatSnapShotListStr != null && chatSnapShotListStr != ""
            && chatSnapShotListStr != undefined) {

            //如果不为空
            chatSnapShotList = JSON.parse(chatSnapShotListStr);
        } else {
            //如果为空
            chatSnapShotList = [];
        }

        return chatSnapShotList;
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

        // 保存聊天历史记录到本地缓存
        app.saveUserChatHistory(userId, reciverId, msg, app.isMe);

        // 保存聊天快照记录到本地缓存
        app.saveUserChatSnapShot(userId, reciverId, msg, true);

        Chat.initChatSnapShot();
    },
    chat2: function (msg) {

        //如果当前websocket的状态是已经打开，则直接发送，否则重连
        if(Chat.socket != null
            && Chat.socket != undefined
            && Chat.socket.readyState == WebSocket.OPEN) {

            Chat.socket.send(msg);
        } else {
            //重连webSocket
            Chat.init();
            setTimeout("Chat.reChat('"+ msg +"')")
            Chat.socket.send(msg);
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
        console.log(JSON.stringify(dataContent));
        //发送websocket
        Chat.chat2(JSON.stringify(dataContent));
    },
    wsClose: function () {
        console.log("连接关闭。。。");
    },
    wsError: function () {
        console.log("连接出错。。。");
    },
    wsMessage: function (e) {
        var chatMsg = JSON.parse(e.data);
        var myId = chatMsg.rerceiverId;
        var friendUserId = chatMsg.senderId;
        var msg = chatMsg.msg;
        //var textContent = $(".div-textarea").html().replace(/[\n\r]/g, '<br>')
        if (chatMsg != "") {
            $(".chatBox-content-demo").append(/*"<div class=\"clearfloat\">" +
                "<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
                "<div class=\"left\"> <div class=\"chat-message\"> " + textContent + " </div> " +
                "<div class=\"chat-avatars\"><img src=\"images/img/icon01.png\" alt=\"头像\" /></div> </div> </div>"*/
            `<div class="clearfloat">
                <div class="author-name">
                    <small class="chat-date">2017-12-02 14:26:58</small>
                </div>
                <div class="left">
                    <div class="chat-avatars"><img src="images/img/icon01.png" alt="头像"/></div>
                    <div class="chat-message">
                        ` + msg + `
                    </div>
                </div>
            </div>`);
            //发送后清空输入框
            $(".div-textarea").html("");
            //聊天框默认最底部
            $(document).ready(function () {
                $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
            });

            //接收消息后，对消息进行签收
            var dataContentSign = new app.DataContent(app.signed, null, chatMsg.msgId);
            Chat.chat2(JSON.stringify(dataContentSign));

            // 保存聊天历史记录到本地缓存
            app.saveUserChatHistory(myId, friendUserId, msg, app.isFriend);
            // 保存聊天历史记录到本地缓存
            app.saveUserChatSnapShot(myId, friendUserId, msg, false);
            Chat.initChatSnapShot();
        }
    },
    
    initChatSnapShot: function () {
        var userId = $("#userId").val();
        $.ajax({
            url: '/getIndex/' + userId,
            type: 'GET',
            async: false,
            success: function (res) {
                var data = res.data;
                var chatSnapShotList = app.getUserChatSnapShot(userId);
                var chatItemHtml = "";
                for(var i = 0; i < chatSnapShotList.length; i++) {
                    var chatItem = chatSnapShotList[i];
                    var friendId = chatItem.friendId;
                    var friend;

                    for(var j = 0; j < data.length; j++) {
                        if (friendId === data[j].id) {
                            friend = data[j];
                        }
                    }

                    chatItemHtml += `<div class="chat-list-people">
                                        <div><img src="images/img/icon01.png" alt="头像"/></div>
                                        <input type="hidden" name="friendId" class="friendId" value="`+ friendId +`"/>
                                        <div class="chat-name">
                                            <p>` + friend.nickName + "   " + chatItem.msg + `</p>
                                        </div>
                                        <div class="message-num">10</div>
                                    </div>`;
                }

                $(".chatBox-list").append(chatItemHtml);
                console.log(data);
            }
        });
        /*var userId = $("#userId").val();
        var chatSnapShotList = app.getUserChatSnapShot(userId);
        var chatItemHtml = "";
        for(var i = 0; i < chatSnapShotList.length; i++) {
            var chatItem = chatSnapShotList[i];
            var friendId = chatItem.friendId;
        }*/
    }
};

Chat.init();

Chat.initChatSnapShot();