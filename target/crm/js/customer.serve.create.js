//创建
function saveCustomerServe() {

    $("#fm").form("submit",{
        url:ctx+'/customerServe/saveOrUpdateCustomerServe',
        onSubmit:function(){
            return $("#fm").form("validate");
        },
        success:function (data) {
            /**
             * data 为原始的json 字符串
             *   需要转换为js 对象
             */
            data=JSON.parse(data);
            if(data.code==200){
                $("#fm").form("clear");// 清空表单数据
                $.messager.alert("来自crm",data.msg,"info");
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}

//刷新
function refresh() {
    $('#dg').datagrid('load');
}
//分配
function openCustomerServeAssignDialog() {
    openModifyDialog('dg','fm','dlg','分配');
}
//保存
function addCustomerServeAssign() {
    saveOrUpdateData('fm',ctx+'/customerServe/saveOrUpdateCustomerServe','dlg',refresh);
}
//处理
function openCustomerServeProceDialog() {
    openModifyDialog('dg','fm','dlg','处理');
}
function addCustomerServeProce() {
    saveOrUpdateData('fm',ctx+'/customerServe/saveOrUpdateCustomerServe','dlg',refresh);
}

//反馈
function openCustomerServeFeedBackDialog() {
    openModifyDialog('dg','fm','dlg','反馈');
}
function addCustomerServeFeedBack() {
    saveOrUpdateData('fm',ctx+'/customerServe/saveOrUpdateCustomerServe','dlg',refresh);
}

//查询
function queryCustomerServesByParams() {
    $('#dg').datagrid('load',{
        customer: $('#cusName').val(),
        myd: $('#myd').combobox('getValue'),
        createDate: $('#time').datebox('getValue')
    })
}