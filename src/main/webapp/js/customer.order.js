function formateState(val) {
    if(val==1){
        return "已支付";
    }else{
        return "待支付";
    }
}

function formateOp(val,row) {
    var orderId=row.id;
    var href="javascript:openOrderDeatilDialog("+orderId+")";
    return "<a href="+href+">查看详情</a>";
}

function openOrderDeatilDialog(orderId, row) {
    //console.log(orderId, row);
    //$('#dlg').dialog('open');
    $.ajax({
        url: ctx + '/customerOrder/queryCustomerOrderById',
        data:{
            id: orderId
        },
        success:function (data) {
            console.log(data);
            if(data.state==0){
                data.status="未支付"
            }else{
                data.status="已支付"
            }
            $('#fm').form('load',data);
            $('#dlg').dialog('open');

            // 加载订单详情
            $('#dg2').datagrid('load',{
                orderId: orderId
            })
        }
    })
}