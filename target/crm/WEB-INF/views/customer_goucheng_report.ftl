<html>
<head>
<#include "common.ftl" >
<script type="text/javascript" src="${ctx}/js/echarts.min.js"></script>
</head>
<body style="margin: 1px">
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    option = {
        title: {
            text: '客户构成分析',
            subtext: '实时更新'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['客户等级']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            data: ['普通客户','重点开发客户','大客户','合作伙伴','战略合作伙伴']
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value} V'
            }
        },
        series: [
            {
                name:'客户等级',
                type:'line',
                data:[]
            }
        ]
    };
    //myChart.setOption(option);

    $(function () {
        $.ajax({
            url: ctx+'/report/queryCustomerGouCheng',
            type:'post',
            success:function (data) {
                option.series[0].data = data;//赋值
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        })
    });

</script>

</body>
</html>
