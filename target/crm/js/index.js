// 登陆操作
function login() {
    /***
     * 1. 获取输入框的参数
     * 2. 校验参数是否为空
     * 3. 发送ajax
     * 4. 处理返回结果:(页面跳转还是弹出提示)
     * */
    var userName = $('#username').val();
    var password = $('#password').val();

    if(isEmpty(userName)){
        alert('用户名为空');
        return;
    }
    if(isEmpty(password)){
        alert('密码为空');
        return;
    }

    $.ajax({
        url: ctx + '/user/login',//http://localhost:8989/crm/user/login
        data:{
            userName: userName,
            password: password
        },
        success:function (data) {
            console.log(data);
            if(data.code == 200){
                //alert('登陆成功');

                // 把用户的登陆信息存入cookie
                $.cookie("userIdStr", data.result.userIdStr);
                $.cookie("trueName", data.result.trueName);
                $.cookie("userName", data.result.userName);

                // 跳转主页
                window.location.href = ctx + '/main';
            }else{
                alert(data.msg);
            }
        }
    });
}