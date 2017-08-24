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
	<title>主页</title>
	<%@include file="../commom.jsp" %>
</head>
<body style="margin-top:20px" onload="$('#w').window('close')";>
    <form id="ff" method="post" style="margin-left:20px">&nbsp;&nbsp; 
	用户名称：<input class="easyui-textbox" type="text" name="name" data-options="required:true"></input>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" >查询</a>
	<a href="javascript:$('#w').window('open');" class="easyui-linkbutton" data-options="iconCls:'icon-add'" >新增</a>		
	</form>
	<table class="easyui-datagrid" title="用户查询" style="width:100%;height:100%"
			data-options="singleSelect:true,collapsible:true,url:'',method:'get'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:'15%'">用户账号</th>
				<th data-options="field:'productid',width:'15%'">用户名称</th>
				<th data-options="field:'productid',width:'15%'">用户邮件</th>
				<th data-options="field:'listprice',width:'15%',align:'right'">用户手机号码</th>
				<th data-options="field:'unitcost',width:'15%',align:'right'">所属班级</th>
				<th data-options="field:'control',width:'6%'">操作</th>
				
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>jiaozi</td>
				<td>张三</td>
				<td>lixin111200@125.com</td>
				<td>135346526325</td>
				<td>1402</td>
				<td>
				<a href="javascript:confirm('确认要删除吗')" class="easyui-linkbutton" data-options="iconCls:'icon-cut'" ></a>
				<a href="javascript:$('#w').window('open')" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" ></a>
				
				</td>
			</tr>
			<tr>
				<td>test</td>
				<td>李四</td>
				<td>lixin111200@125.com</td>
				<td>135346526325</td>
				<td>1402</td>
				<td>
					<a href="javascript:confirm('确认要删除吗')" class="easyui-linkbutton" data-options="iconCls:'icon-cut'" ></a>
				    <a href="javascript:$('#w').window('open')" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" ></a>
				</td>
			</tr>
		</tbody>
	</table>
	
	<div id="w" class="easyui-window" title="新增用户" data-options="iconCls:'icon-save',modal:true,shadow:true" style="width:500px;height:200px;padding:5px;">
		    <form id="ff" method="post">
		    	<table cellpadding="5">
		    		<tr>
		    			<td>用户账号:</td>
		    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
		    			<td>用户名称:</td>
		    			<td><input class="easyui-textbox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
		    		</tr>
		    		<tr>
		    			<td>用户密码:</td>
		    			<td><input class="easyui-textbox" type="text" name="subject" data-options="required:true"></input></td>
		    			<td>用户邮件:</td>
		    			<td><input class="easyui-textbox" name="message"  ></input></td>
		    		</tr>
		    		<tr>
		    			<td>用户手机号码:</td>
		    			<td>
		    			<input class="easyui-textbox" name="message"  ></input>
		    			</td>
		    			<td>所属班级:</td>
		    			<td>
		    			<input class="easyui-textbox" name="message"  ></input>
		    			</td>
		    		</tr>
		    	</table>
		    </form>
		    <div style="text-align:center;padding:5px">
		    	<a href="javascript:$('#w').window('close')" class="easyui-linkbutton" onclick="submitForm()">保存</a>
		    	<a href="javascript:$('#w').window('close')" class="easyui-linkbutton" onclick="clearForm()">取消</a>
		    </div>
	</div>
	
</body>
</html>

