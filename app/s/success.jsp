<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'success.jsp' starting page</title>
    
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
   注册成功 ：
    <%
        request.setCharacterEncoding("UTF-8");
    	String userName=request.getParameter("userName");
    	String password=request.getParameter("password");
    	String eamil=request.getParameter("userEmail");
    	String nation=request.getParameter("nation");
    	String sex=request.getParameter("sex");
    	String[] hobby=request.getParameterValues("hobby");
    	String intro=request.getParameter("intro");
     %>
      注册信息：<br/>
      用户名:<%=userName %><br/>
      密码:<%=password %><br/>   
      邮箱:<%=eamil %><br/>
      国籍:<%=("1".equals(nation)?"港台":"大陆") %><br/>
      性别:<%=("1".equals(sex)?"女":"男") %><br/>
      爱好:<%   if(hobby!=null)
			for(String tmpStr:hobby){
			     if("0".equals(tmpStr)){
			     	out.println("看书");
			     }else if("1".equals(tmpStr)){
			     	out.println("看电影");
			     }else{
			     	out.println("乒乓球");
			     }
			}
        %><br/>
      自我介绍:<%=intro %>       <br/>
      <%=request.getParameter("comeSource") %>
  </body>
</html>
