<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>主页</title>
	<%@include file="commom.jsp" %>
	<script type="text/javascript" src="<%=path %>/resource/js/jquery.sparkline.js"></script>
	<script type="text/javascript" src="<%=path %>/resource/js/chart/Chart.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(function () {
			    //bar图 http://omnipotent.net/jquery.sparkline/#interactive
			    //$('.bar').sparkline([ [1,4], [2, 3], [3, 2], [4, 1] ], { type: 'bar',barWidth:200,height:200 });
			    var randomScalingFactor = function() {
			        return Math.round(Math.random() * 100);
			    };
			    //http://www.bootcss.com/p/chart.js/docs/
			    var ctx = $("#myChart").get(0).getContext("2d");
			    var barChartData = {
		            labels: ["10-01", "10-02", "10-03", "10-04", "10-05", "10-06", "10-07"],
		            datasets: [{
		                label: '结果1',
		                backgroundColor: "green",
		                data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
		            }]
		
		        };
		        window.myBar = new Chart(ctx, {
	                type: 'bar',
	                data: barChartData,
	                options: {
	                    responsive: true,
	                }
	            });
	            
	            
	            
	            
	            var ctx1 = $("#myLineChart").get(0).getContext("2d");
	            var lineData={
	                labels: ["10-01", "10-02", "10-03", "10-04", "10-05", "10-06", "10-07"],
	                datasets: [{
		                    label: "结果1",
		                    fill:true,
		                    pointHoverBorderWidth: 2,
		                    backgroundColor:'green',
		                    data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()],
		                }]
	            };
	            /**
	            window.myLine = new Chart(ctx1, {
	                type: 'line',
	                data: lineData,
	                options: {
	                    responsive: true,
	                }
	            });
	            **/
			});
		});
	</script>
</head>
<body>
    <div style="height:20px">
    
    </div>
    <div style="margin-left:20px;margin-bottom:20px">
	</div>
    <div style="margin-left:20px">
	<div id="myDiv" class="easyui-panel" title="学生成绩统计" style="width:400px;">
		<canvas id="myChart"></canvas>
	</div>
	</div>
	
	
	<div style="width:500px;height:500px">
	<canvas id="myLineChart"></canvas>
	</div>
</body>
</html>
