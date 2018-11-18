//查询
function queryRolesByParams() {
    $('#dg').datagrid('load', {
        roleName: $('#roleName').val(),
        createDate: $('#time').datebox('getValue')
    })
}

//添加
function openAddRoleDailog() {
    openAddOrUpdateDlg('dlg', '添加角色');
}
function saveOrUpdateRole() {
    saveOrUpdateData('fm', ctx + '/role/saveOrUpdateRole', 'dlg', queryRolesByParams);
}

//更新
function openModifyRoleDialog() {
    openModifyDialog('dg', 'fm', 'dlg', '修改角色');
}

//删除
function deleteRole() {
    deleteData('dg', ctx + '/role/deleteRole', queryRolesByParams);
}

function openRelationPermissionDialog() {

    var rows = $("#dg").datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert("来自crm", "请选择一条角色记录进行授权!");
        return;
    }

    if (rows.length > 1) {
        $.messager.alert("来自crm", "只能选择一条角色记录进行授权!");
        return;
    }

    loadModulePermission(rows[0].id);

    /***
     * 记录角色ID到隐藏域中
     * */
    $('#roleId').val(rows[0].id);

    $('#permissionDlg').dialog('open');
}


/**---------------初始化zTree----------------**/
var zTreeObj;

function loadModulePermission(roleId) {
    $.ajax({
        url: ctx + '/role/queryModulePermissionByRoleId',
        type: 'post',
        data: {
            roleId: roleId
        },
        success: function (data) {
            // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                view: {
                    showLine: false
                    // showIcon: false
                },
                check: {
                    enable: true,
                    chkboxType: {"Y": "ps", "N": "ps"}
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            var zNodes = data;
            zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        }
    });
}

// 获取所有勾选的节点
function zTreeOnCheck() {
    var nodes = zTreeObj.getCheckedNodes(true);
    //console.log(nodes);
    var moduleIds = '';
    for (var i = 0; i < nodes.length; i++) {
        var moduleId = nodes[i].id;
        moduleIds += 'moduleIds='+moduleId+'&';
    }
    console.log(moduleIds);
    $('#moduleIds').val(moduleIds);
}

// 授权
function doGrant() {
    var roleId = $('#roleId').val();
    var moduleIds = $('#moduleIds').val();

    $.ajax({
        url:ctx + '/role/doRoleGrant',
        type:'post',
        data:'roleId='+roleId+'&'+moduleIds,
        success:function (data) {
            //console.log(data);
            if(data.code == 200){
                $.messager.alert('来自crm', '角色授权成功','info', function () {
                    //关闭弹窗
                    closeDlgData('permissionDlg');
                    //清空隐藏域的值
                    $('#roleId, #moduleIds').val('');
                });
            }else{
                $.messager.alert('来自crm', data.msg,'error')
            }
        }
    });
}