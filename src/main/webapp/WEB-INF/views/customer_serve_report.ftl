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
    myChart.title = '客户服务分析';

    option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:['咨询','建议','投诉']
        },
        series: [
            {
                name:'数据来源',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[
//                    {value:335, name:'咨询'},
//                    {value:310, name:'建议'},
//                    {value:234, name:'投诉'}
                ]
            }
        ]
    };

    $(function () {
        $.ajax({
            url: ctx+'/report/queryCustomerServePer',
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
