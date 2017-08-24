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
	<title>学生激励积分系统</title>
	<%@include file="commom.jsp" %>
</head>
<body>
	
	<div class="easyui-layout" style="width:100%;height:100%;">
			<div data-options="region:'north'" style="height:100px">
			<iframe name="mainFrame" src="top.jsp"  width="100%" height="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</div>
			<!-- title="用户导航" -->
			<div data-options="region:'west',split:true,iconCls:'icon-world'"  style="width:200px;">
				<iframe name="leftFrame" src="left.jsp"  width="100%" height="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</div>
			<div id="naviTab" class="easyui-tabs" style="border-left:0;border-top:0;border-right:0" data-options="region:'center',iconCls:'icon-ok'">
			    <div id="hello" title="欢迎页" >   
			        <iframe name="mainFrame" src="main.jsp"  width="100%" height="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			    </div>
			</div>
	</div>
 
</body>
</html>
