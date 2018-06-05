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
               text: '销售报表-饼状图',
               subtext: ${groupName},
               x:'center'
           },
           tooltip : {
               trigger: 'item',
               formatter: "{a} <br/>{b} : {c} ({d}%)"
           },
           legend: {
               orient: 'vertical',
               left: 'left',
               data: ${groupByTypes}
           },
           toolbox: {
               show : true,
               feature : {
                   dataView : {show: true, readOnly: false},
                   magicType : {show: true, type: []},
                   restore : {show: true},
                   saveAsImage : {show: true}
               }
           },
           series : [
               {
                   name: '销售总金额',
                   type: 'pie',
                   radius : '55%',
                   center: ['50%', '60%'],
                   data: ${totalAmount},
                   itemStyle: {
                       emphasis: {
                           shadowBlur: 10,
                           shadowOffsetX: 0,
                           shadowColor: 'rgba(0, 0, 0, 0.5)'
                       }
                   }
               }
           ]
       };

       myChart.setOption(option);

   }
</script>
<body>
<div id="main" style="width: 980px; height: 500px;"></div>
</body>
</html>
