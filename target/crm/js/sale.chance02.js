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
    openAddOrUpdateDlg('dlg','添加营销机会');
}

// 添加或者更新
function saveOrUpdateSaleChance() {
    saveOrUpdateData('fm',ctx +'/saleChance/saveOrUpdateSaleChance','dlg',querySaleChancesByParams);
}

//更新
function openModifySaleChanceDialog() {
    openModifyDialog('dg','fm','dlg','更新营销机会');
}

//删除
function deleteSaleChance () {
    deleteData('dg',ctx + '/saleChance/deleteSaleChance',querySaleChancesByParams);
}