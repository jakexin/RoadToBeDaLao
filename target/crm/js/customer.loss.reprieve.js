$(function () {
    var lossId = $('#lossId').val();
    $('#dg').edatagrid({
        url: ctx + '/customerReprieve/queryCustomerReprieveByParams?lossId=' + lossId,
        saveUrl: ctx + '/customerReprieve/saveOrUpdateCustomerReprieve?lossId=' + lossId,
        updateUrl: ctx + '/customerReprieve/saveOrUpdateCustomerReprieve?lossId=' + lossId//,
        // destroyUrl: ...
    });
});

function addRow() {
    $('#dg').edatagrid('addRow');
}

function saveOrUpdateCusDevPlan() {
    $('#dg').edatagrid('saveRow');
}

function delCusDevPlan() {
    var rows = $('#dg').edatagrid('getSelections');
    if(rows.length==0){
        $.messager.alert('来自crm', "请选择一条记录进行删除");
        return;
    }else{
        $.messager.confirm('来自crm', '是否要删除'+rows.length+'条数据', function (r) {

            var ids = '';
            for (var i = 0; i < rows.length; i++) {
                var id = rows[i].id;
                ids += 'ids='+id+'&';
            }
            console.log(ids);
            if(r){
                $.ajax({
                    url: ctx + '/customerReprieve/deleteCustomerReprieve',
                    type: 'post',
                    data: ids,
                    success:function (data) {
                        console.log(data);
                        if(data.code == 200){
                            $('#dg').edatagrid('load');
                        }else{
                            $.messager.alert('来自crm', data.msg);
                        }
                    }
                });
            }
        })
    }
}

function updateCustomerLossState() {
    var lossId = $('#lossId').val();
    $.ajax({
        url: ctx + '/customerLoss/updateCustomerLoss',
        type: 'post',
        data: {
            id: lossId,
            state: 1
        },
        success:function (data) {
            console.log(data);
            if(data.code == 200){
                $('#dg').edatagrid('load');
                $('#toolbar').hide();//隐藏工具条
                $('#dg').edatagrid('disableEditing')// 禁用编辑
            }else{
                $.messager.alert('来自crm', data.msg);
            }
        }
    });
}