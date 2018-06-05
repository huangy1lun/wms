<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/js/plugins/echarts.min.js"></script>
</head>
<script>
   window.onload = function () {
       var myChart = echarts.init(document.getElementById('main'));
       option = {
           title : {
               text: '销售报表-柱状图',
               subtext: ${groupName},
               x:"center"
           },
           tooltip : {
               trigger: 'axis'
           },
           legend: {
               data:["销售总金额"],
               left: 'left'
           },
           toolbox: {
               show : true,
               feature : {
                   dataView : {show: true, readOnly: false},
                   magicType : {show: true, type: ['line', 'bar']},
                   restore : {show: true},
                   saveAsImage : {show: true}
               }
           },
           calculable : true,
           xAxis : [
               {
                   type : 'category',
                   data : ${groupByTypes}
               }
           ],
           yAxis : [
               {
                   type : 'value'
               }
           ],
           series : [
               {
                   name:"销售总金额",
                   type:'bar',
                   data: ${totalAmount},
                   markPoint : {
                       data : [
                           {type : 'max', name: '最大值'},
                           {type : 'min', name: '最小值'}
                       ]
                   },
                   markLine : {
                       data : [
                           {type : 'average', name: '平均值'}
                       ]
                   }
               },

           ]
       };
       myChart.setOption(option);

   }
</script>
<body>
<div id="main" style="width:980px; height: 500px;"></div>
</body>
</html>
