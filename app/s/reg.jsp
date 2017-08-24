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
		function submitMyForm(){
			//等价于javascript的document.getElementById("loginForm").submit();
			$("#loginForm").submit();
		}
	</script>
	<style type="text/css">
	
	.panel-title {
		  font-size: 12px;
		  font-weight: bold;
		  color: #0E2D5F;
		  height: 16px;
		  line-height: 16px;
		}
	</style>
</head>
	<div style="position: absolute;top:10px;left:500px">
	<form action="success.jsp" id="loginForm" method="post">
	<div class="easyui-panel" title="用户注册" style="width:400px;padding:30px 60px;">
		<div style="margin-bottom:20px">
			<div>用户名<font color=red>*</font>:</div>
			<input name="userName" class="easyui-textbox" data-options="prompt:'请输入用户名...'" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>密码<font color=red>*</font>:</div>
			<input name="password" type="password" class="easyui-textbox" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>邮箱<font color=red>*</font>:</div>
			<input name="userEmail" class="easyui-textbox"  style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>国籍:</div>
			<select name="nation" class="easyui-combobox" name="state" style="width:100%;height:32px">
				<option value="0" selected="selected">大陆</option>
				<option value="1">港台</option>
			</select>
		</div>
		<div style="margin-bottom:20px">
			<div id="sex_p" class="easyui-panel" title="性别:" style="width:100%;height:80px;padding:10px;">
			<input  name="sex" type="radio" value="0" >男 &nbsp;&nbsp;&nbsp;&nbsp;<input name="sex"  type="radio" value="1" >女
			</div>
		</div>
		<div style="margin-bottom:20px">
		     <div id="hobby_p" class="easyui-panel" title="爱好:" style="width:100%;height:80px;padding:10px;">
		     <input  name="hobby" type="checkbox" value="0" >读书 &nbsp;&nbsp;&nbsp;&nbsp;
		     <input name="hobby"  type="checkbox" value="1" >看电影&nbsp;&nbsp;&nbsp;&nbsp;
		     <input name="hobby"  type="checkbox" value="2" >乒乓球&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</div>
		<div style="margin-bottom:20px">
		     <div>自我介绍:</div>
		     <textarea rows="5" cols="20" name="intro"   style="width:100%;height:80px;border: 1px solid #95B8E7;"></textarea>
		     <input type="hidden" name="comeSource" value="etop"/>
		</div>
		<div>
			<a href="javascript:submitMyForm()" class="easyui-linkbutton" iconCls="icon-ok" style="width:80px;height:32px">提交</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" style="width:80px;height:32px">重置</a>
		</div>
	</div>
	</form>
	</div>
</body>
</html>
