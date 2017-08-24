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

  </head>
  
  <body style="padding:0px">
    <TABLE WIDTH=800 BORDER=0 CELLPADDING=0 CELLSPACING=0>
	<TR>
		<TD>
			<IMG SRC="resource/images/Spacer.gif" WIDTH=78 HEIGHT=1></TD>
		<TD>
			<IMG SRC="resource/images/Spacer.gif" WIDTH=6 HEIGHT=1></TD>
		<TD>
			<IMG SRC="resource/images/Spacer.gif" WIDTH=5 HEIGHT=1></TD>
		<TD>
			<IMG SRC="resource/images/Spacer.gif" WIDTH=5 HEIGHT=1></TD>
		<TD>
			<IMG SRC="resource/images/Spacer.gif" WIDTH=247 HEIGHT=1></TD>
		<TD>
			<IMG SRC="resource/images/Spacer.gif" WIDTH=11 HEIGHT=1></TD>
		<TD>
			<IMG SRC="resource/images/Spacer.gif" WIDTH=448 HEIGHT=1></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="resource/images/logo_01.gif" WIDTH=78 HEIGHT=23></TD>
		<TD COLSPAN=2>
			<IMG SRC="resource/images/logo_02.gif" WIDTH=11 HEIGHT=23></TD>
		<TD COLSPAN=4>
			<IMG SRC="resource/images/logo_03.gif" WIDTH=711 HEIGHT=23></TD>
	</TR>
	<TR>
		<TD COLSPAN=5>
			<IMG SRC="resource/images/logo_04.gif" WIDTH=341 HEIGHT=54></TD>
		<TD>
			<IMG SRC="resource/images/logo_05.gif" WIDTH=11 HEIGHT=54></TD>
		<TD ROWSPAN=2>
			<IMG SRC="resource/images/logo_06.gif" WIDTH=448 HEIGHT=77></TD>
	</TR>
	<TR>
		<TD COLSPAN=2>
			<IMG SRC="resource/images/logo_07.gif" WIDTH=84 HEIGHT=23></TD>
		<TD COLSPAN=2>
			<IMG SRC="resource/images/logo_08.gif" WIDTH=10 HEIGHT=23></TD>
		<TD COLSPAN=2>
			<IMG SRC="resource/images/logo_09.gif" WIDTH=258 HEIGHT=23></TD>
	</TR>
</TABLE>
  </body>
</html>
