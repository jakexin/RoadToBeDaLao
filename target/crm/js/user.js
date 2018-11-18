
function queryUsersByParams() {
    $('#dg').datagrid('load',{
        userName: $('#userName').val(),
        email: $('#email').val(),
        phone: $('#phone').val()
    });
}

//添加
function openAddUserDailog() {
    openAddOrUpdateDlg('dlg',"添加用户");
}

//添加或者更新操作
function saveOrUpdateUser() {
    saveOrUpdateData('fm',ctx+'/user/saveOrUpdateUser','dlg',queryUsersByParams);
}

function openModifyUserDialog() {
    openModifyDialog('dg','fm','dlg','修改用户');
}

//删除
function deleteUser() {
    deleteData('dg',ctx+'/user/deleteUser',queryUsersByParams);
}