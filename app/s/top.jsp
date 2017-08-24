<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'top.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@include file="commom.jsp" %>
  </head>
  
  <body style="padding:0px">
    <div style="height:23px;width:100%;background-image:url(<%=path%>/resource/images/logo_02.gif)"></div>
    <div>
    	<div style="position:absolute;top:23px;left:0px;width:341px;height:54px;background-image:url(<%=path%>/resource/images/logo_04.gif);background-repeat: no-repeat"></div>
    	<div style="margin-left:341px;height:54px;background-image:url(<%=path%>/resource/images/logo_05.gif)"></div>
    </div>
     <div style="height:23px;width:100%;background-image:url(<%=path%>/resource/images/logo_08.gif)"></div>
    <div></div>
    <div style="position:absolute;top:70%;left:80%">
       	<font color='green'> 
       	欢迎&nbsp;<b>JIAOZI</b> &nbsp;&nbsp;<a href="#">修改密码</a>&nbsp;&nbsp;<a href="#">安全退出</a>
       	</font> 
    </div>
  </body>
</html>
