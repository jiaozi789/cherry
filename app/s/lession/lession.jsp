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
	课程名称：<input class="easyui-textbox" type="text" name="name" data-options="required:true"></input>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" >查询</a>
	<a href="javascript:$('#w').window('open');" class="easyui-linkbutton" data-options="iconCls:'icon-add'" >新增</a>		
	</form>
	<table class="easyui-datagrid" title="课程查询" style="width:100%;height:100%"
			data-options="singleSelect:true,collapsible:true,url:'',method:'get'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:'15%'">课程名称</th>
				<th data-options="field:'listprice',width:'15%',align:'right'">级别</th>
				<th data-options="field:'listtype',width:'15%',align:'right'">类别</th>
				<th data-options="field:'productid',width:'15%'">创建人</th>
				<th data-options="field:'productdate',width:'15%'">创建时间</th>
				<th data-options="field:'unitcost',width:'15%',align:'right'">课程描述</th>
				<th data-options="field:'control',width:'6%'">操作</th>
				
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>JSP/SERVLET</td>
				<td>中级</td>
				<td>JAVA</td>
				<td>张三</td>
				<td>2015-12-31</td>
				<td>JAVAEE基础课程</td>
				<td>
				<a href="javascript:confirm('确认要删除吗')" class="easyui-linkbutton" data-options="iconCls:'icon-cut'" ></a>
				<a href="javascript:$('#w').window('open')" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" ></a>
				</td>
			</tr>
			<tr>
				<td>ORACLE</td>
				<td>中级</td>
				<td>数据库</td>
				<td>李四</td>
				<td>2015-12-31</td>
				<td>数据库必学课程</td>
				<td>
					<a href="javascript:confirm('确认要删除吗')" class="easyui-linkbutton" data-options="iconCls:'icon-cut'" ></a>
				    <a href="javascript:$('#w').window('open')" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" ></a>
				</td>
			</tr>
		</tbody>
	</table>
	
	<div id="w" class="easyui-window" title="新增课程" data-options="iconCls:'icon-save',modal:true,shadow:true" style="width:500px;height:200px;padding:5px;">
		    <form id="ff" method="post">
		    	<table cellpadding="5">
		    		<tr>
		    			<td>课程名称:</td>
		    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
		    			<td>级别:</td>
		    			<td><input class="easyui-textbox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
		    		</tr>
		    		<tr>
		    			<td>类别:</td>
		    			<td colspan="3"><input class="easyui-textbox" type="text" name="subject" data-options="required:true"></input></td>
		    		</tr>
		    		<tr>
		    			<td>描述:</td>
		    			<td colspan="1">
		    			<textarea rows="5" cols="20" ></textarea>
		    			</td>
		    			<td colspan="2">
		    				<div style="text-align:center;padding:5px">
						    	<a href="javascript:$('#w').window('close')" class="easyui-linkbutton" onclick="submitForm()">保存</a>
						    	<a href="javascript:$('#w').window('close')" class="easyui-linkbutton" onclick="clearForm()">取消</a>
						    </div>
		    			</td>
		    		</tr>
		    	</table>
		    </form>
		    
	</div>
	
</body>
</html>

