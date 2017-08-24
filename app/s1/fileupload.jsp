<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form action="fileUploadServlet" method="post" enctype="multipart/form-data">
    	用户&nbsp;名:  <input name="uname" type="text"/><br/>
    	密&nbsp;码:  <input name="password" type="text"/><br/>
    	形象照  <input type="file" name="myIntro"/><br/>
    	薪水条  <input type="file" name="salary"/>
    	<input type="submit" value="提交"/>
    </form>
    <a href="fileDownServlet?fileName=NSI_DriverInstall好.log">下载文件</a>
  </body>
</html>
