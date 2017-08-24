ï»¿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="css/css.css" />
    <title>My JSP 'loginSuc.jsp' starting page</title>
    
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
    <div class="menu">
<ul>
<li style="width:0px;"><a></a></li>
<li class="active"><a href="wishlist.html" class="wishlist">物品列表 (0)</a></li>
<li><a href="cart.html" class="cart">购物车</a></li>
<li style="float:right;"><a href="login.html" >登录账号:jiaozi</a></li>
</ul>
</div>



<div class="header">
</div>


<div style="width:1000px;margin: 0 auto;margin-top:10px;">
<div class="linktree">

<br>
 
  </div>
<div id="content" style='text-align:center'>  
    <div class="right">
      <h2>登录界面</h2>
      <form action="#" id="login">
        <div class="content">
          <b>账号:</b>
          <input type="text" name="email" value="" />
          <br />
          <br />
          <b>密码:</b>
          <input type="password" name="password" value="" />
          <br />
          <br />
          <a onclick="$('#login').submit();" class="button"><span>Login</span></a>
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
