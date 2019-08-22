if(window.top!==window.self){window.top.location=window.location};
layui.use(['element', 'layer', 'form'], function () {
    var $ = layui.jquery;
    var form = layui.form;

    //刷新验证码
    $(document).on('click', '.layadmin-user-login-codeimg', function () {
        var src = this.src.split("?")[0];
        this.src = src + "?" + Math.random();
    });

    //用户登录
    form.on('submit(loginSubmit)', function (obj) {
        var data = obj.field;
        console.log(data);
        $.ajax({
            url: ctxPath + '/login',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'Application/JSON; charset=utf-8',
            success: function (res) {
                console.log(res);
                if (res.code === 200) {
                    layer.msg(res.message, {
                        offset: '15px'
                        ,icon: 1
                        ,time: 1000
                    }, function () {
                        window.location.href = ctxPath + '/index';
                    })
                } else {
                    $('.layadmin-user-login-codeimg').click();
                    return layer.msg(res.message);
                }
            }
        })
    });

    //用户注册
    form.on('submit(regSubmit)', function (obj) {
        var data = obj.field;
        //验证两次密码是否一致
        if (!checkPwdEq(data)) {
            return layer.msg('两次输入密码不一致');
        }
        //是否同意用户协议
        if (!data.agreement) {
            return layer.msg('你必须同意用户协议才能注册');
        }

        console.log(data);
        $.ajax({
            url: ctxPath + '/api/reg',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'Application/JSON; charset=utf-8',
            success: function (res) {
                console.log(res);
                if (res.code === 200) {
                    layer.msg(res.message, {
                        offset: '15px'
                        ,icon: 1
                        ,time: 3000
                    }, function () {
                        window.location.href = ctxPath + '/';
                    })
                } else {
                    return layer.msg(res.message);
                }
            }
        })
    });

    //验证密码输入一致
    var checkPwdEq = function (data) {
        if (data.password === data.repass) {
            return true;
        } else {
            return false;
        }
    };


    //用户忘记密码
    form.on('submit(forgetSubmit)', function (obj) {
        var data = obj.field;
        console.log(data);
        $.ajax({
            url: ctxPath + '/api/forget',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'Application/JSON; charset=utf-8',
            success: function (res) {
                console.log(res);
                if (res.code === 200) {
                    layer.msg(res.message, {
                        offset: '15px'
                        ,icon: 1
                        ,time: 1000
                    },function () {
                        window.location.href = ctxPath + '/system/toResetpass?phone=' + data.phone;
                    })
                } else {
                    return layer.msg(res.message);
                }
            }
        })
    });

    //重置密码
    form.on('submit(forgetResetpass)', function (obj) {
        var data = obj.field;
        console.log(data);
        //验证两次密码是否一致
        if (!checkPwdEq(data)) {
            return layer.msg('两次输入密码不一致');
        }
        $.ajax({
            url: ctxPath + '/api/resetPassword',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'Application/JSON; charset=utf-8',
            success: function (res) {
                console.log(res);
                if (res.code === 200) {
                    layer.msg(res.message, {
                        offset: '15px'
                        ,icon: 1
                        ,time: 3000
                    }, function () {
                        window.location.href = ctxPath + '/';
                    })
                } else {
                    return layer.msg(res.message);
                }
            }
        })
    });

    //发送验证码
    $("#sendSmsCode").click(function (e) {
        $.ajax({
           url: ctxPath + '/api/sendSmsCode?phone=' + '17856941755' ,
            type: 'get',
            success: function (res) {
                if (res.code === 200) {
                    //倒计时操作 todo
                } else {
                    return layer.msg(res.message);
                }
            }
        });
    })
    
});