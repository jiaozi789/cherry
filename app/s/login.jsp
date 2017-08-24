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
	<title>学生积分系统-登录</title>
	<%@include file="commom.jsp" %>
</head>
	<div style="position: absolute;top:200px;left:500px">
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="用户登陆" style="width:400px">
		<div style="padding:10px 60px 20px 80px"> 
	    <form id="loginForm" method="post" action="index.jsp">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>用户名:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width:100%"></input></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td><input class="easyui-textbox" type="text" name="email" data-options="required:true" style="width:100%"></input></td>
	    		</tr>
	    		
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:document.forms[0].submit()" class="easyui-linkbutton" iconCls="icon-ok" style="width:80px;height:25px">提交</a>
			<a href="javascript:document.forms[0].reset()" class="easyui-linkbutton" iconCls="icon-reload" style="width:80px;height:25px">重置</a>
	    </div>
	    </div>
	</div>
	</div>
</body>
</html>
