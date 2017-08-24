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
		function toAddTab(text,url){
			parent.$('#naviTab').tabs('add',{    
							    title:text,    
							    content:'<iframe name="mainFrame" src="<%=request.getContextPath()%>'+url+'"  width="100%" height="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>',  
							    fit: true,
								border:false,  
							    closable:true,  
							    iconCls:'icon-add',
							    toolPosition:'left'  
			}); 
		}
	</script>
	<style type="text/css">
		a{
			text-decoration: none;
			color:green;
		}
	</style>

</head>
<body>
	<div class="easyui-accordion">
		<div title="权限管理" data-options="iconCls:'icon-dperson'" style="overflow:auto;padding:10px;background-color: rgb(233,242,255)">
			<p>
			    <div  style="background-color: rgb(233,242,255)">
			        <a href="javascript:toAddTab('用户管理','/userManager/user.jsp')" style="border:1">
					<div style="margin-left:40%;">
						<img  src="<%=path %>/resource/css/themes/icons/extra/16prof.ico">
					</div>
					<div  style="margin-left:30%">
					用户管理
					</div>
					</a>
					<a href="javascript:toAddTab('角色管理','/userManager/role.jsp')">
					<div style="margin-left:40%;margin-top:10px">
						<img  src="<%=path %>/resource/css/themes/icons/extra/16prof.ico">
					</div>
					<div  style="margin-left:30%">
					角色管理
					</div>
					</a>
					<a href="javascript:toAddTab('权限管理','/userManager/funList.jsp')">
					<div style="margin-left:40%;margin-top:10px">
						<img  src="<%=path %>/resource/css/themes/icons/extra/16prof.ico">
					</div>
					<div  style="margin-left:30%">
					权限管理
					</div>
					</a>
				</div>
			</p>
		</div>
		<div title="学生信息管理" data-options="iconCls:'icon-manager'" style="overflow:auto;padding:10px;background-color: rgb(233,242,255)">
			<p>
				 <div  style="background-color: rgb(233,242,255)">
			        <a href="#" style="border:1">
					<div style="margin-left:40%;">
						<img  src="<%=path %>/resource/css/themes/icons/extra/16prof.ico">
					</div>
					<div  style="margin-left:25%">
					学生信息维护
					</div>
					</a>
					<a href="#">
					<div style="margin-left:40%;margin-top:10px">
						<img  src="<%=path %>/resource/css/themes/icons/extra/16prof.ico">
					</div>
					<div  style="margin-left:25%">
					班级考试维护
					</div>
					</a>
					<a href="#">
					<div style="margin-left:40%;margin-top:10px">
						<img  src="<%=path %>/resource/css/themes/icons/extra/16prof.ico">
					</div>
					<div  style="margin-left:25%">
					学生成绩维护
					</div>
					</a>
					<a href="#">
					<div style="margin-left:40%;margin-top:10px">
						<img  src="<%=path %>/resource/css/themes/icons/extra/16prof.ico">
					</div>
					<div  style="margin-left:25%">
					学生考勤维护
					</div>
					</a>
				</div>
			</p>
		</div>
		<div title="课程信息管理" data-options="iconCls:'icon-calc'" style="overflow:auto;padding:10px;">
			<p>
				<a href="javascript:toAddTab('课程维护','/lession/lession.jsp')">
					<div style="margin-left:40%;margin-top:10px">
						<img  src="<%=path %>/resource/css/themes/icons/extra/16prof.ico">
					</div>
					<div  style="margin-left:30%">
					课程维护
					</div>
				</a>
				
			</p>
		</div>
		<div title="学生信息统计" data-options="iconCls:'icon-calc'" style="overflow:auto;padding:10px;">
			<p>
				<a href="#">
					<div style="margin-left:40%;margin-top:10px">
						<img  src="<%=path %>/resource/css/themes/icons/extra/16prof.ico">
					</div>
					<div  style="margin-left:30%">
					成绩合格率
					</div>
				</a>
				<a href="#">
					<div style="margin-left:40%;margin-top:10px">
						<img  src="<%=path %>/resource/css/themes/icons/extra/16prof.ico">
					</div>
					<div  style="margin-left:30%">
					成绩特评
					</div>
				</a>
			</p>
		</div>
	</div>
 
</body>
</html>
