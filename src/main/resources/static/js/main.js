layui.use(['element', 'form', 'layer', 'upload', 'table', 'layedit'], function () {
    var $ = layui.jquery;
    var element = layui.element; //加载element模块
    var form = layui.form; //加载form模块
    var layer = layui.layer; //加载layer模块
    var upload = layui.upload;  //加载upload模块
    var table = layui.table;//加载表格模块
    var layedit = layui.layedit;//加载富文本模块
    var layeditOption = {
        uploadImage: {
          url: ctxPath + '/api/attachment',
            accept: 'image',
            acceptMime: 'image/*',
            exts: 'jpg|png|gif|bmp|jpeg',
            size: '10240'
        },
        uploadVideo: {
            url: '',
            accpet: 'video',
            accpetMime: 'video/*',
            exts: 'mp4|flv|avi|rm|rmvb',
            size: '20480'
        },
        tool: ['html', 'code', 'strong', 'italic', 'underline', 'del', 'addhr', '|', 'fontFomatt', 'colorpicker', 'face'
            , '|', 'left', 'center', 'right', '|', 'link', 'unlink', 'image_alt', 'video', 'anchors'
            , '|', 'fullScreen'],
        devmode: true,
        codeConfig: {
            hide: true, // 是否显示编码语言选择框
            default: 'javascript' //hide为true时的默认语言格式
        }
    };//富文本通用配置
    layedit.set(layeditOption);

    /**
     * 创建富文本编辑器(只要在需要富文本的地方id=layeditor即可)
     */
    var editor = layedit.build('layeditor');

    /* 侧边栏开关 */
    $(".side-toggle").on("click", function (e) {
        e.preventDefault();
        var to = $(".layui-layout-admin");
        to.toggleClass("layui-side-shrink");
        to.attr("toggle") === 'on' ? to.attr("toggle", "off") : to.attr("toggle", "on");
    });
    $(".layui-side").on("click", function () {
        var to = $(".layui-layout-admin");
        if (to.attr("toggle") === 'on') {
            to.attr("toggle", "off");
            to.removeClass("layui-side-shrink");
        }
    });

    /* 最大化窗口 */
    $(".timo-screen-full").on("click", function (e) {
        e.preventDefault();
        if (!$(this).hasClass("full-on")) {
            var docElm = document.documentElement;
            var full = docElm.requestFullScreen || docElm.webkitRequestFullScreen ||
                docElm.mozRequestFullScreen || docElm.msRequestFullscreen;
            "undefined" !== typeof full && full && full.call(docElm);
        } else {
            document.exitFullscreen ? document.exitFullscreen()
                : document.mozCancelFullScreen ? document.mozCancelFullScreen()
                : document.webkitCancelFullScreen ? document.webkitCancelFullScreen()
                    : document.msExitFullscreen && document.msExitFullscreen()
        }
        $(this).toggleClass("full-on");
    });

    /* 新建或切换标签栏 */
    var tabs = function (url) {
        var item = $('[lay-url="' + url + '"]');
        if (url !== undefined && url !== '#' && item.length > 0) {
            var bootLay = $('[lay-id="' + url + '"]');
            if (bootLay.length === 0) {
                var title = item.attr("lay-icon") === 'true' ? item.html()
                    : item.children(".layui-nav-title").text();
                var iframeUrl = (ctxPath + url).replace('//','/');
                element.tabAdd('iframe-tabs', {
                    title: title
                    , content: '<iframe src="' + iframeUrl + '" frameborder="0" class="layui-layout-iframe"></iframe>'
                    , id: url
                });
            }
            element.tabChange('iframe-tabs', url);
        }
    };

    /* 监听导航栏事件，实现标签页的切换 */
    element.on("nav(layui-nav-side)", function ($this) {
        var url = $this.attr('lay-url');
        tabs(url);
    });

    /* 监听标签栏事件，实现导航栏高亮显示 */
    element.on("tab(iframe-tabs)", function () {
        var layId = $(this).attr("lay-id");
        $(".layui-side .layui-this").removeClass("layui-this");
        $('[lay-url="' + layId + '"]').parent().addClass("layui-this");
        // 改变地址hash值
        location.hash = this.getAttribute('lay-id');
    });

    /* 监听hash来切换选项卡*/
    window.onhashchange = function (e) {
        var url = location.hash.replace(/^#/, '');
        var index = $(".layui-layout-admin .layui-side .layui-nav-item")[0];
        $(index).children("a").attr("lay-icon", "true");
        if (url === "" || url === undefined) {
            url = $(index).children("[lay-url]").attr("lay-url");
        }
        tabs(url);
    };
    window.onhashchange();

    /* 初始化时展开子菜单 */
    $("dd.layui-this").parents(".layui-nav-child").parent()
        .addClass("layui-nav-itemed");

    /* 刷新iframe页面 */
    $(".refresh-btn").click(function () {
        location.reload();
    });

    /* AJAX请求默认选项，处理连接超时问题 */
    $.ajaxSetup({
        //请求完成后
        complete: function (xhr, status) {
            if (xhr.status == 401) {
                layer.confirm('session连接超时，是否重新登录？', {
                    btn: ['是', '否']
                }, function () {
                    if (window.parent.window != window) {
                        window.top.location = ctxPath + '/login';
                    }
                });
            }
        },

        /*//请求前
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type", 'Application/JSON; charset=utf-8');
        }*/

    });

    /*  漂浮消息 */
    $.fn.Messager = function (result) {
        if (result.code === 200) {
            layer.msg(result.message);
            setTimeout(function () {
                if (result.data === null) {
                    window.location.reload();
                } else {
                    parent.location.reload();
                    return;
                }
            }, 2000);
        } else {
            layer.msg(result.message);
        }
    };

    $.fn.MessagerACT = function (result) {
        if (result.code === 200) {
            layer.msg(result.message);
            setTimeout(function () {
                parent.location.reload();
                return;
            }, 2000);
        } else {
            layer.msg(result.message);
        }
    };

    /*
    表单转为json数据
     */
    function serializeObject(formData){
        //输出以数组形式序列化表单值
        var data = formData;
        var obj = {};
        $.each(data,function(i,v){
            if (obj[v.name] !== undefined) {
                obj[v.name] = obj[v.name] + "," + v.value;
            } else {
                obj[v.name] = v.value;
            }
        });
        return obj;
    }

    /* 提交表单数据 */
    $(document).on("click", ".ajax-submit", function (e) {
        e.preventDefault();
        var form = $(this).parents("form");
        var url = form.attr("action");
        var data = serializeObject(form.serializeArray());
        var type = data.id?'PUT':'POST';
        if (editor != undefined) {
            data.content = layedit.getContent(editor);
        }
        $.ajax({
           url: url,
            type: type,
            data: JSON.stringify(data),
            contentType: 'application/json',
            success:function (res) {
                if (res.data === null) {
                    res.data = 'submit[refresh]';
                }
                $.fn.Messager(res);
            }
        });
    });

    /* 提交带文件的表单数据 */
    $(document).on("click", ".ajax-submit-file", function (e) {
        e.preventDefault();
        var form = $(this).parents("form");
        var addUrl = form.attr("action");
        var type = 'POST';
        var editUrl = $(this).attr('data-edit-url');
        var data = serializeObject(form.serializeArray());
        var img = $('#frmCommon [name=classIcon]')[0].files[0];
        var url = data.id?editUrl:addUrl;

        data.img = img;
        if (editor != undefined) {
            data.content = layedit.getContent(editor);
        }

        var b = new FormData, d;
        for (d in data) {
            b.append(d, data[d]);
        }
        console.log(b);
        $.ajax({
            url: url,
            type: type,
            data: b,
            processData: false, //当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
            contentType: false, //ajax 中 contentType 设置为 false 是为了避免 JQuery 对其操作，从而失去分界符，而使服务器不能正常解析文件
            xhrFields: {
                withCredentials: true
            },
            success:function (res) {
                if (res.data === null) {
                    res.data = 'submit[refresh]';
                }
                $.fn.Messager(res);
            }
        });
    });

    //提交数据后，跳转页面
    $(document).on("click", ".ajax-submit-redirect", function (e) {
        e.preventDefault();
        var form = $(this).parents("form");
        var url = form.attr("action");
        var data = serializeObject(form.serializeArray());
        var type = data.id?'PUT':'POST';
        if (editor != undefined) {
            data.content = layedit.getContent(editor);
        }
        $.ajax({
            url: url,
            type: type,
            data: JSON.stringify(data),
            contentType: 'application/json',
            success:function (res) {
                if (res.data === null) {
                    res.data = 'submit[refresh]';
                }
                $.fn.Messager(res);
            }
        });
    });

    /* 删除del方式异步 */
    $(document).on("click", ".ajax-del", function (e) {
        e.preventDefault();
        var msg = $(this).data("msg");
        if (msg !== undefined) {
            layer.confirm(msg + '？', {
                title: '提示',
                btn: ['确认', '取消']
            }, function () {
                $.ajax({
                    url: e.target.href,
                    type: 'DELETE',
                    contentType: 'Application/JSON; charset=utf-8',
                    success:function (res) {
                        $.fn.Messager(res);
                    }
                });
            });
        } else {
            $.get(e.target.href, function (result) {
                $.fn.Messager(result);
            });
        }
    });

    // ajax方式异步-操作状态
    $(".ajax-status").on("click", function (e) {
        e.preventDefault();
        var checkStatus = table.checkStatus('id')//注意这个id不是html中table元素上的id，而是table:render中定义的id
            ,ids = [];
        if (checkStatus.data.length === 0) {
            layer.msg('请选择要数据行');
        } else {
            layer.confirm ('确定操作吗?', function (index) {
                for (var i = 0; i < checkStatus.data.length; i++) {
                    ids.push(checkStatus.data[i].id);
                }
                $.ajax({
                    url: e.target.href + ids,
                    type: 'DELETE',
                    contentType: 'Application/JSON; charset=utf-8',
                    success:function (res) {
                        $.fn.Messager(res);
                    }
                });
            })
        }

    });

    // ajax方式异步-操作状态
    $(".ajax-status-thy").on("click", function (e) {
        e.preventDefault();
        var checked = [];
        var tdcheckbox = $(".timo-table td .timo-checkbox :checkbox:checked");
        if (tdcheckbox.length > 0) {
            tdcheckbox.each(function (key, val) {
                checked.push( $(val).attr("value"));
            });
            $.ajax({
                url: e.target.href + checked,
                type: 'DELETE',
                contentType: 'Application/JSON; charset=utf-8',
                success:function (res) {
                    $.fn.Messager(res);
                }
            });
        } else {
            layer.msg('请选择一条记录');
        }

    });

    /* 工作流重新提交, 取消申请 */
    $(document).on("click", ".ajax-submit-return,.ajax-cancelApply, .ajax-pass, .ajax-not-pass", function (e) {
        e.preventDefault();
        var url = $(this).attr("data-url");
        var taskId = $(this).attr("taskId");
        var flag = $(this).attr("flag");
        var type = $(this).attr("type");//重新提交流程类型
        var form = $(this).parents("form");
        var data = serializeObject(form.serializeArray());

        if (type !== undefined) {
            url = url + '/' + taskId + '/' + type + '/' + flag;
        } else {
            url = url + '/' + taskId + '/' + flag;
        }

        $.ajax({
            url: url,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (res) {
                console.log(res);
                $.fn.MessagerACT(res);
            }
        })


        /*$.ajax({
            url: url,
            type: type,
            data: JSON.stringify(data),
            contentType: 'application/json',
            success:function (res) {
                if (res.data === null) {
                    res.data = 'submit[refresh]';
                }
                $.fn.Messager(res);
            }
        });*/
    });

    /* 添加/修改弹出层 */
    $(document).on("click", ".open-popup, .open-popup-param", function () {
        var title = $(this).data("title");
        var url = $(this).attr("data-url");
        var paramElem = $(this).attr("param");
        if ($(this).hasClass("open-popup-param")) {
            var checkStatus = table.checkStatus('id');//注意这个id不是html中table元素上的id，而是table:render中定义的id
            var data = checkStatus.data
                ,ids = [];
            var param = '';
            if (data.length === 0) {
                layer.msg('请选择一条记录');
                return;
            }
            if (data.length > 1 && $(this).data("type") === 'radio') {
                layer.msg('只允许选中一个');
                return;
            }
            for (var i = 0; i < data.length; i++) {
                ids.push(checkStatus.data[i].id);
            }
            url += "/" + ids;
        } else {
            if (paramElem !== undefined) {
                url += "/" + paramElem;
            }
        }
        var size = $(this).attr("data-size");
        if (size === undefined || size === "auto") {
            size = ['50%', '80%'];
        }else if (size === "max") {
            size = ['100%', '100%'];
        }else if (size.indexOf(',') !== -1) {
            var split = size.split(",");
            size = [split[0] + 'px', split[1] + 'px'];
        }
        window.layerIndex = layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            maxmin: true,
            area: size,
            content: [url, 'on']
        });
    });

    /* 添加/修改弹出层 */
    $(document).on("click", ".open-popup-redirect", function () {
        var title = $(this).data("title");
        var url = $(this).attr("data-url");
        var paramElem = $(this).attr("param");
        if ($(this).hasClass("open-popup-param")) {
            var checkStatus = table.checkStatus('id');//注意这个id不是html中table元素上的id，而是table:render中定义的id
            var data = checkStatus.data
                ,ids = [];
            var param = '';
            if (data.length === 0) {
                layer.msg('请选择一条记录');
                return;
            }
            if (data.length > 1 && $(this).data("type") === 'radio') {
                layer.msg('只允许选中一个');
                return;
            }
            for (var i = 0; i < data.length; i++) {
                ids.push(checkStatus.data[i].id);
            }
            url += "/" + ids;
        } else {
            if (paramElem !== undefined) {
                url += "/" + paramElem;
            }
        }

        window.location.href = ctxPath + url;

    });

    /* 关闭弹出层 */
    $(".close-popup").click(function (e) {
        e.preventDefault();
        parent.layer.close(window.parent.layerIndex);
    });

    /* 下拉按钮组 */
    $(".btn-group").click(function (e) {
        e.stopPropagation();
        $this = $(this);
        $this.toggleClass("show");
        $(document).one("click", function () {
            if ($this.hasClass("show")) {
                $this.removeClass("show");
            }
        });
    });

    // 展示数据列表-多选框
    var thcheckbox = $(".timo-table th .timo-checkbox :checkbox");
    thcheckbox.on("change", function () {
        var tdcheckbox = $(".timo-table td .timo-checkbox :checkbox");
        if (thcheckbox.is(':checked')) {
            tdcheckbox.prop("checked", true);
        } else {
            tdcheckbox.prop("checked", false);
        }
    });

    // 检测列表数据是否为空
    var timoTable = $(".timo-table tbody");
    if (timoTable.length > 0) {
        var children = timoTable.children();
        if (children.length === 0) {
            var length = $(".timo-table thead th").length;
            var trNullInfo = "<tr><td class='timo-table-null' colspan='"
                + length + "'>没有找到匹配的记录</td></tr>";
            timoTable.append(trNullInfo);
        }
    }

    // 携带参数跳转
    var paramSkip = function () {
        var getSearch = "";
        // 搜索框参数
        $('.timo-search-box').find('input').each(function (key, val) {
            if ($(val).val() !== "" && $(val).val() !== undefined) {
                getSearch += $(val).attr("name") + "=" + $(val).val() + "&";
            }
        });

        if (getSearch !== "") {
            getSearch = "?" + getSearch.substr(0, getSearch.length - 1);
        }

        window.location.href = window.location.pathname + getSearch;
    };

    /* 展示列表数据搜索 */
    $(document).on("click", ".timo-search-btn-thy", function () {
        paramSkip();
    });
    /* 改变显示页数 */
    $(document).on("change", ".page-number", function () {
        paramSkip();
    });
    /* 触发字段排序 */
    $(document).on("click", ".sortable", function () {
        $(".sortable").not(this).removeClass("asc").removeClass("desc");
        if($(this).hasClass("asc")){
            $(this).removeClass("asc").addClass("desc");
        }else {
            $(this).removeClass("desc").addClass("asc");
        }
        paramSkip();
    });

    /* 参数化字段排序 */
    var getSearch = function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    };
    var field = getSearch("orderByColumn");
    var isAsc = getSearch("isAsc");
    if(field != null){
        $("[data-field='"+ field +"']").addClass(isAsc);
    }

    /** 上传图片操作 */
    upload.render({
        elem: '.upload-image' //绑定元素
        ,url: $('.upload-image').attr('up-url') //上传接口
        ,field: 'image' //文件域的字段名
        ,acceptMime: 'image/*' //选择文件类型
        ,exts: 'jpg|jpeg|png|gif' //支持的图片格式
        ,multiple: true //开启多文件选择
        ,choose: function (obj) {
            obj.preview(function (index, file, result) {
                var upload = $('.upload-image');
                var name = upload.attr('name');
                var show = upload.parents('.layui-form-item').children('.upload-show');
                show.append("<div class='upload-item'><img src='"+ result +"'/>" +
                    "<input id='"+ index +"' type='hidden' name='"+name+"'/>" +
                    "<i class='upload-item-close layui-icon layui-icon-close'></i></div>");
            });
        }
        ,done: function(res, index, upload){
            var field = $('.upload-image').attr('up-field') || 'id';
            // 解决节点渲染和异步上传不同步问题
            var interval = window.setInterval(function(){
                var hide = $("#"+index);
                if(hide.length > 0){
                    var item = hide.parent('.upload-item');
                    if (res.code === 200) {
                        hide.val(res.data[field]);
                        item.addClass('succeed');
                    }else {
                        hide.remove();
                        item.addClass('error');
                    }
                    clearInterval(interval);
                }
            }, 100);
        }
    });

    // 删除上传图片展示项
    $(document).on("click", ".upload-item-close", function () {
        $(this).parent('.upload-item').remove();
    });

});