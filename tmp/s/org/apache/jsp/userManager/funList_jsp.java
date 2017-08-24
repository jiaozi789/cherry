package org.apache.jsp.userManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.util.*;

public final class funList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/userManager/../commom.jsp");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

      out.write("\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n\t<meta charset=\"UTF-8\">\r\n\t<title>主页</title>\r\n\t");
      out.write("\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(path );
      out.write("/resource/css/themes/default/easyui.css\">\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(path );
      out.write("/resource/css/themes/icon.css\">\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(path );
      out.write("/resource/css/demo/demo.css\">\r\n<script type=\"text/javascript\" src=\"");
      out.print(path );
      out.write("/resource/js/jquery.min.js\"></script>\r\n<script type=\"text/javascript\" src=\"");
      out.print(path );
      out.write("/resource/js/jquery.easyui.min.js\"></script>\r\n<script type=\"text/javascript\" src=\"");
      out.print(path );
      out.write("/resource/js/common.js\"></script>\r\n<style>\r\nbody{\r\n\tpadding:0px;\r\n\tbackground-color: rgb(233,242,255);\r\n}\r\n</style>");
      out.write("\r\n</head>\r\n<body style=\"margin-top:20px\" onload=\"$('#w').window('close')\";>\r\n    <form id=\"ff\" method=\"post\" style=\"margin-left:20px\">&nbsp;&nbsp; \r\n\t权限名称：<input class=\"easyui-textbox\" type=\"text\" name=\"name\" data-options=\"required:true\"></input>\r\n\t<a href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\" >查询</a>\r\n\t<a href=\"javascript:$('#w').window('open');\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add'\" >新增</a>\t\t\r\n\t</form>\r\n\t<table class=\"easyui-datagrid\" title=\"权限查询\" style=\"width:100%;height:100%\"\r\n\t\t\tdata-options=\"singleSelect:true,collapsible:true,url:'',method:'get'\">\r\n\t\t<thead>\r\n\t\t\t<tr>\r\n\t\t\t\t<th data-options=\"field:'itemid',width:'15%'\">权限账号</th>\r\n\t\t\t\t<th data-options=\"field:'productid',width:'15%'\">权限名称</th>\r\n\t\t\t\t<th data-options=\"field:'productid',width:'15%'\">权限邮件</th>\r\n\t\t\t\t<th data-options=\"field:'listprice',width:'15%',align:'right'\">权限手机号码</th>\r\n\t\t\t\t<th data-options=\"field:'unitcost',width:'15%',align:'right'\">所属班级</th>\r\n\t\t\t\t<th data-options=\"field:'control',width:'6%'\">操作</th>\r\n");
      out.write("\t\t\t\t\r\n\t\t\t</tr>\r\n\t\t</thead>\r\n\t\t<tbody>\r\n\t\t\t<tr>\r\n\t\t\t\t<td>jiaozi</td>\r\n\t\t\t\t<td>张三</td>\r\n\t\t\t\t<td>lixin111200@125.com</td>\r\n\t\t\t\t<td>135346526325</td>\r\n\t\t\t\t<td>1402</td>\r\n\t\t\t\t<td>\r\n\t\t\t\t<a href=\"javascript:confirm('确认要删除吗')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-cut'\" ></a>\r\n\t\t\t\t<a href=\"javascript:$('#w').window('open')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-edit'\" ></a>\r\n\t\t\t\t\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t\t<tr>\r\n\t\t\t\t<td>test</td>\r\n\t\t\t\t<td>李四</td>\r\n\t\t\t\t<td>lixin111200@125.com</td>\r\n\t\t\t\t<td>135346526325</td>\r\n\t\t\t\t<td>1402</td>\r\n\t\t\t\t<td>\r\n\t\t\t\t\t<a href=\"javascript:confirm('确认要删除吗')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-cut'\" ></a>\r\n\t\t\t\t    <a href=\"javascript:$('#w').window('open')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-edit'\" ></a>\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t</tbody>\r\n\t</table>\r\n\t\r\n\t<div id=\"w\" class=\"easyui-window\" title=\"新增权限\" data-options=\"iconCls:'icon-save',modal:true,shadow:true\" style=\"width:500px;height:200px;padding:5px;\">\r\n\t\t    <form id=\"ff\" method=\"post\">\r\n");
      out.write("\t\t    \t<table cellpadding=\"5\">\r\n\t\t    \t\t<tr>\r\n\t\t    \t\t\t<td>权限账号:</td>\r\n\t\t    \t\t\t<td><input class=\"easyui-textbox\" type=\"text\" name=\"name\" data-options=\"required:true\"></input></td>\r\n\t\t    \t\t\t<td>权限名称:</td>\r\n\t\t    \t\t\t<td><input class=\"easyui-textbox\" type=\"text\" name=\"email\" data-options=\"required:true,validType:'email'\"></input></td>\r\n\t\t    \t\t</tr>\r\n\t\t    \t\t<tr>\r\n\t\t    \t\t\t<td>权限密码:</td>\r\n\t\t    \t\t\t<td><input class=\"easyui-textbox\" type=\"text\" name=\"subject\" data-options=\"required:true\"></input></td>\r\n\t\t    \t\t\t<td>权限邮件:</td>\r\n\t\t    \t\t\t<td><input class=\"easyui-textbox\" name=\"message\"  ></input></td>\r\n\t\t    \t\t</tr>\r\n\t\t    \t\t<tr>\r\n\t\t    \t\t\t<td>权限手机号码:</td>\r\n\t\t    \t\t\t<td>\r\n\t\t    \t\t\t<input class=\"easyui-textbox\" name=\"message\"  ></input>\r\n\t\t    \t\t\t</td>\r\n\t\t    \t\t\t<td>所属班级:</td>\r\n\t\t    \t\t\t<td>\r\n\t\t    \t\t\t<input class=\"easyui-textbox\" name=\"message\"  ></input>\r\n\t\t    \t\t\t</td>\r\n\t\t    \t\t</tr>\r\n\t\t    \t</table>\r\n\t\t    </form>\r\n\t\t    <div style=\"text-align:center;padding:5px\">\r\n\t\t    \t<a href=\"javascript:$('#w').window('close')\" class=\"easyui-linkbutton\" onclick=\"submitForm()\">保存</a>\r\n");
      out.write("\t\t    \t<a href=\"javascript:$('#w').window('close')\" class=\"easyui-linkbutton\" onclick=\"clearForm()\">取消</a>\r\n\t\t    </div>\r\n\t</div>\r\n\t\r\n</body>\r\n</html>\r\n\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
