<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    <link rel="stylesheet" type="text/css" href="css/css.css" />
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
    



<div class="header">
</div>


<div style="width:1000px;margin: 0 auto;margin-top:10px;">
<div class="linktree">

<br>
 
  </div>
<div id="content" style='text-align:center'>  
    <div class="right">
      <h2>登录界面</h2>
      <form action="<%=basePath %>/system/login.action" id="login" method="post">
        <div class="content">
          <b>账号:</b>
          <input type="text" name="userName" value="" />
          <br />
          <br />
          <b>密码:</b>
          <input type="password" name="password" value="" />
          <br />
          <br />
          <a href="javascript:document.forms[0].submit()" class="button"><span>Login</span></a>
                  </div>
      </form>
    </div>

</div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<div class="icart-footer">
<div class="icart-footer-top">
	<div style="width:1000px; margin:0 auto;">	
	</div>
</div>

		

</div></div>
  </body>
</html>
