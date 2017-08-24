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
	<table class="easyui-datagrid" title="角色查询" style="width:100%;height:100%"
			data-options="singleSelect:true,collapsible:true,url:'',method:'get'">
		<thead>
			<tr>
				<th data-options="field:'productid',width:'15%'">角色名称</th>
				<th data-options="field:'productid',width:'15%'">创建人</th>
				<th data-options="field:'listprice',width:'15%',align:'right'">创建时间</th>
				<th data-options="field:'unitcost',width:'15%',align:'right'">角色描述</th>
				<th data-options="field:'control',width:'10%'">操作</th>
				
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>老师</td>
				<td>王五</td>
				<td>2015-10-13</td>
				<td>上课老师 任课老师</td>
				<td>
				<a href="javascript:confirm('确认要删除吗')" class="easyui-linkbutton" data-options="iconCls:'icon-cut'" ></a>
				<a href="javascript:$('#w').window('open')" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" ></a>
				<a href="javascript:openWindowUrl('<%=path%>/userManager/fun.jsp','选择权限')" class="easyui-linkbutton" data-options="iconCls:'icon-mini-add'" ></a>
				
				</td>
			</tr>
			<tr>
				<td>学生</td>
				<td>李四</td>
				<td>2015-10-13</td>
				<td>上课老师 任课老师</td>
				<td>
					<a href="javascript:confirm('确认要删除吗')" class="easyui-linkbutton" data-options="iconCls:'icon-cut'" ></a>
				    <a href="javascript:$('#w').window('open')" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" ></a>
					<a href="javascript:$('#w').window('open')" class="easyui-linkbutton" data-options="iconCls:'icon-mini-add'" ></a>
				
				</td>
			</tr>
		</tbody>
	</table>
	
	<div id="w" class="easyui-window" title="新增课程" data-options="iconCls:'icon-save',modal:true,shadow:true" style="width:500px;height:220px;padding:5px;">
		    <form id="ff" method="post">
		    	<table cellpadding="5">
		    		<tr>
		    			<td>角色名称:</td>
		    			<td colspan="1"><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
		    					    		</tr>
		    		<tr>
		    			<td>描述:</td>
		    			<td colspan="1">
		    			<textarea rows="5" cols="20" ></textarea>
		    			</td>
		    		</tr>
		    	</table>
		    </form>
		    <div style="text-align:left;margin-left:25px">
		    	<a href="javascript:$('#w').window('close')" class="easyui-linkbutton" onclick="submitForm()">保存</a>
		    	<a href="javascript:$('#w').window('close')" class="easyui-linkbutton" onclick="clearForm()">取消</a>
		    </div>
	</div>
	
</body>
</html>

