function formateGrade(val) {
    if(val == 0){
        return "根级菜单"
    }
    if(val == 1){
        return "一级菜单"
    }
    if(val == 2){
        return "二级菜单"
    }
}

//查询
function queryModulesByParams() {
    $('#dg').datagrid('load',{
        moduleName: $('#moduleName').val(),
        parentId: $('#pid').val(),
        grade: $('#grade').combobox('getValue'),
        optValue: $('#optValue').val()
    })
}

//打开添加弹出框
function openAddModuleDailog() {
    openAddOrUpdateDlg('dlg','添加模块');
}

// 通过pId查询模块
function loadParentModule(grade) {
    $('#parentId02').combobox({
        url:ctx +'/module/queryParentModuleByGrade?grade='+grade,
        valueField:'id',
        textField:'moduleName'
    });
}

$(function () {
    $('#parentMenu').hide();// 初始化隐藏该选项

    // 监听下拉选择
    $('#grade02').combobox({
        onSelect: function (record) {
            //console.log(val);
            var grade = record.value;
            if(grade!=0){
                loadParentModule(record.value-1);
                $('#parentMenu').show()
            }else{
                $('#parentMenu').hide();
            }
        }
    })

    // 当弹窗关闭时自动清除表单数据
    $('#dlg').dialog({
        'onClose':function () {
            $('#fm').form('clear');
        }
    })
});


//保存
function saveOrUpdateModule() {
    saveOrUpdateData('fm', ctx+'/module/saveOrUpdateModule','dlg',queryModulesByParams);
}

//更新
function openModifyModuleDialog() {
    openModifyDialog('dg','fm','dlg','更新模块');
}

//删除
function deleteModule () {
    //deleteData('dg',ctx+'/module/deleteModulesByOptValue',searchByParams)

    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请至少选中一条记录进行删除!");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选中一条记录进行删除!");
        return;
    }
    $.messager.confirm("来自crm","确定删除选中的"+rows.length+"条记录?",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+'/module/deleteModulesByOptValue',
                data:{
                    optValue: rows[0].optValue
                },
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        // 刷新datagrid
                        queryModulesByParams();
                    }else{
                        $.messager.alert("来自crm",data.msg,"error");
                    }
                }
            })
        }
    })
}
















