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
	<title>Basic Panel - jQuery EasyUI Demo</title>
	<%@include file="commom.jsp" %>
	<script type="text/javascript">
		function loadBody(){
			$("#authTree").tree({
				onClick: function(node){
					parent.$('#naviTab').tabs('add',{    
							    title:node.text,    
							    content:'Tab Body',  
							    fit: true,
								border:false,  
							    closable:true,  
							    iconCls:'icon-add',
							    toolPosition:'left'  
					});  
					
				}
			});
		}
	</script>
</head>
<body onload="loadBody()">
	<ul class="easyui-tree" id="authTree">
		<li>
			<span>学生评分系统</span>
			<ul>
				<li data-options="state:'open'">
					<span>权限管理</span>
					<ul>
						<li>
							<span>用户管理</span>
						</li>
						<li>
							<span>角色管理</span>
						</li>
						<li>
							<span>权限管理</span>
						</li>
					</ul>
				</li>
				<li>
					<span>学生信息管理</span>
					<ul>
						<li>学生信息维护</li>
						<li>班级考试维护</li>
						<li>学生成绩维护</li>
						<li>学生考勤维护</li>
					</ul>
				</li>
				<li>
					<span>学生信息统计</span>
					<ul>
						<li>成绩合格率</li>
						<li>成绩特评</li>
					</ul>
				</li>
			</ul>
		</li>
	</ul>
 
</body>
</html>
