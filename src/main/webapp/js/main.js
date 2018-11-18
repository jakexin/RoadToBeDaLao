function openTab(text, url, iconCls) {
    if ($("#tabs").tabs("exists", text)) {
        $("#tabs").tabs("select", text);
    } else {
        var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add", {
            title: text,
            iconCls: iconCls,
            closable: true,
            content: content
        });
    }
}


// 显示修改密码弹窗
function openPasswordModifyDialog() {
    $('#dlg').dialog('open');
}

//修改密码
function modifyPassword() {
    $('#fm').form('submit',{
        url: ctx + "/user/updateUserPwd",
        onSubmit: function () {
            var isValid = $(this).form('validate');
            if (!isValid){
                $.messager.alert('来自crm', "数据不完整");
            }
            return isValid;	// 返回false终止表单提交
        },
        success: function (data) {
            data = JSON.parse(data); // 解析json字符串
            console.log(data);
            if(data.code == 200){

                // 1. 清除cookie信息
                // 2. 系统退出到登陆页
                $.messager.alert('来自crm', "密码修改成功!", 'info',function(){

                    // $.removeCookie("userIdStr");
                    // $.removeCookie("trueName");
                    // $.removeCookie("userName");
                    //
                    // window.location.href = ctx + '/index';
                    logout();
                });
            }else{
                $.messager.alert('来自crm', data.msg, 'error');
            }
        }
    });
}

// 安全退出
function logout() {
    $.removeCookie("userIdStr");
    $.removeCookie("trueName");
    $.removeCookie("userName");

    window.location.href = ctx + '/index';
}