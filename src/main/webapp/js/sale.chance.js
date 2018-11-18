// 查询
function querySaleChancesByParams() {
    // 取参数
    // 第二参数设置查询条件
    $('#dg').datagrid('load',{
        customerName: $('#customerName').val(),
        state: $('#state').combobox('getValue'),
        devResult: $('#devResult').combobox('getValue'),
        createDate: $("#time").datebox('getValue')
    });
}

function formatState(value,row,index) {
    if(value == 0){
        return "未分配";
    }else if(value == 1){
        return "已分配";
    }
}

function formatDevResult(value,row,index) {
    /*
     <option value="0">未开发</option>
     <option value="1">开发中</option>
     <option value="2">开发成功</option>
     <option value="3">开发失败</option>
    * */


    if(value == 0){
        return "未开发";
    }else if(value == 1){
        return "开发中";
    }else if(value == 2){
        return "开发成功";
    }else if(value == 3){
        return "开发失败";
    }
}

$(function () {
    // 等到页面加载完成在执行
    $('#dg').datagrid({
        rowStyler: function(index,row){
            // if (row.devResult == 0){
            //     return 'background-color:blue;';
            // }
            // if (row.devResult == 1){
            //     return 'background-color:yellow;';
            // }
            // if (row.devResult == 2){
            //     return 'background-color:green;';
            // }
            // if (row.devResult == 3){
            //     return 'background-color:red;';
            // }
            var devResult = row.devResult;
            if (devResult == 0) {
                return "background-color:#5bc0de;"; // 蓝色
            } else if (devResult == 1) {
                return "background-color:#f0ad4e;"; // 黄色
            } else if (devResult == 2) {
                return "background-color:#5cb85c;"; // 绿色
            } else if (devResult == 3) {
                return "background-color:#d9534f;"; // 红色
            }
        }
    });
});

// 打开添加窗口
function openAddSaleChacneDialog() {
    $('#dlg').dialog('open');
}

// 添加或者更新
function saveOrUpdateSaleChance() {
    $('#fm').form('submit',{
        url: ctx + '/saleChance/saveOrUpdateSaleChance',
        onSubmit:function () {
            return $(this).form('validate');
        },
        success:function (data) {
            data = JSON.parse(data);
            console.log(data);
            if(data.code == 200){
                $.messager.alert('来自crm', "添加成功", 'info',function () {
                    // 关闭弹窗
                    $('#dlg').dialog('close');
                    // 清空数据
                    $('#fm').form('clear');

                    // 刷新表格数据
                    $('#dg').datagrid('load');//重新加载表单数据
                });
            }else{
                $.messager.alert('来自crm', data.msg, 'error');
            }
        }
    })
}

//更新
function openModifySaleChanceDialog() {
    var rows = $('#dg').datagrid('getSelections');// 获取选中行
    console.log(rows);
    if(rows.length == 0){
        $.messager.alert('来自crm', '请选择1条数据进行更新');
        return;
    }else if(rows.length > 1){
        $.messager.alert('来自crm', '只能选择1条数据进行更新');
        return;
    }

    //回显数据到表格中
    $('#fm').form('load', rows[0]);
    $('#dlg').dialog('open');
}

//删除
function deleteSaleChance () {
    // 获取选中记录
    var rows = $('#dg').datagrid('getSelections');// 获取选中行
    if(rows.length <=0 ){
        $.messager.alert('来自crm', '请选择1条数据进行删除');
        return;
    }else{
        $.messager.alert('来自crm', '是否要删除'+rows.length+'条数据?','info',function () {
            // 拼成ids字符串: 形式: ids=95&ids=96
            var ids = '';
            for (var i = 0; i < rows.length; i++) {
                var id = rows[i].id;
                //console.log(id);
                ids += 'ids='+id+'&';
            }
            console.log(ids);
            // 使用ajax删除数据
            $.ajax({
                url: ctx + '/saleChance/deleteSaleChance',
                type: 'post',
                data:ids,
                success:function (data) {
                    //console.log(data);
                    if(data.code == 200){
                        $.messager.alert('来自crm', "删除成功", 'info',function () {
                            // 刷新表格数据
                            $('#dg').datagrid('load');//重新加载表单数据
                        });
                    }else{
                        $.messager.alert('来自crm', "删除失败", 'error');
                    }
                }
            });
        });
    }
}