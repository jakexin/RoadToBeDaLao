// function formateState(val) {
//     if(val==0){
//         return "未流失"
//     }else{
//         return "已流失"
//     }
// }
//
// function formateOp() {
//
// }

function formateState(val) {
    if(val==0){
        return "暂缓流失";
    }else{
        return "确认流失";
    }
}

function formateOp(val,row) {
    var state=row.state;
    if(state==0){
        var  href="javascript:openAddReprieveDataTab("+row.id+")";
        return "<a href="+href+">添加暂缓<a>";
    }else{
        return "确认流失";
    }
}

function openAddReprieveDataTab(lossId) {
    window.parent.openTab("添加暂缓措施_"+lossId,ctx+"/customerReprieve/index?lossId="+lossId);
}


function queryCustomerLossByParams() {
    $('#dg').datagrid('load',{
        cusName:$('#cusName').val(),
        cusNo:$('#cusNo').val(),
        createDate:$('#time').datebox('getValue')
    })
}