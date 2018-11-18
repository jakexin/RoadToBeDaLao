function formatDevResult(value,row,index) {
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

function formateOp(value,row,index) {
    // if(row.devResult == 2 || row.devResult == 3){
    //     return '<a href="javascript:openSaleChanceInfoDialog('+'"详情机会数据"'+','+row.id+');">查看详情</a>';
    // }else{
    //     return '<a href="javascript:openSaleChanceInfoDialog('+'"开发机会数据"'+','+row.id+');">开发</a>';
    // }
    var devResult=row.devResult;
    if(devResult==0||devResult==1){
        var href="javascript:openSaleChanceInfoDialog("+'"开发机会数据"'+","+row.id+")";
        return "<a href='"+href+"'>开发</a>";
    }
    if(devResult==2||devResult==3){
        var href="javascript:openSaleChanceInfoDialog("+'"详情机会数据"'+","+row.id+")";
        return "<a href='"+href+"'>查看详情</a>";
    }
}

// 查询
function querySaleChancesByParams() {
    // 取参数
    // 第二参数设置查询条件
    $('#dg').datagrid('load',{
        customerName: $('#customerName').val(),
        devResult: $('#devResult').combobox('getValue'),
        createDate: $("#time").datebox('getValue')
    });
}

function openSaleChanceInfoDialog(title,id) {
    /**
     * 打开新的选项卡
     */
    window.parent.openTab(title+"_"+id,ctx+"/saleChance/cusDevDetail?sid="+id);
}