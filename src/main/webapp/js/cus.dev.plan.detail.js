$(function () {
    var sid = $('#saleChanceId').val();// 获取id
    $('#dg').edatagrid({
        url: ctx + '/cusDevPlan/queryCusDevPlans?sid=' + sid,
        saveUrl: ctx + '/cusDevPlan/saveOrUpdateCusDevPlan?sid=' + sid,
        updateUrl: ctx + '/cusDevPlan/saveOrUpdateCusDevPlan?sid=' + sid
    });

    /***
     * 判断开发状态是否为 2 和 3
     * */
    var devResult = $('#devResult').val();
    if(devResult==2 || devResult==3){
        $('#toolbar').hide();// 隐藏操作按钮
        $('#dg').edatagrid('disableEditing');//禁用数据表格编辑。
    }

});

//添加
function addRow () {
    $('#dg').edatagrid('addRow');
}

//保存
function saveOrUpdateCusDevPlan () {
    $('#dg').edatagrid('saveRow');
}

//删除
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
                    url: ctx + '/cusDevPlan/deleteBatchCusDevPlan',
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

//更改开发状态
function updateSaleChanceDevResult (devResult) {
    var sid = $('#saleChanceId').val();// 获取id
    $.ajax({
        url: ctx + '/saleChance/saveOrUpdateSaleChance',
        data:{
            id: sid,
            devResult: devResult
        },
        success:function (data) {
            console.log(data);
            if(data.code == 200){
                $('#toolbar').hide();// 隐藏操作按钮
                $('#dg').edatagrid('disableEditing');//禁用数据表格编辑。
            }else{
                $.messager.alert('来自crm', data.msg);
            }
        }
    });
}