package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.util.*;

public final class left_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/commom.jsp");
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

      out.write("\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n\t<meta charset=\"UTF-8\">\r\n\t<title>Basic Panel - jQuery EasyUI Demo</title>\r\n\t");
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
      out.write("\r\n\t<script type=\"text/javascript\">\r\n\t\tfunction toAddTab(text,url){\r\n\t\t\tparent.$('#naviTab').tabs('add',{    \r\n\t\t\t\t\t\t\t    title:text,    \r\n\t\t\t\t\t\t\t    content:'<iframe name=\"mainFrame\" src=\"");
      out.print(request.getContextPath());
      out.write("'+url+'\"  width=\"100%\" height=\"100%\"  frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" allowtransparency=\"yes\"></iframe>',  \r\n\t\t\t\t\t\t\t    fit: true,\r\n\t\t\t\t\t\t\t\tborder:false,  \r\n\t\t\t\t\t\t\t    closable:true,  \r\n\t\t\t\t\t\t\t    iconCls:'icon-add',\r\n\t\t\t\t\t\t\t    toolPosition:'left'  \r\n\t\t\t}); \r\n\t\t}\r\n\t</script>\r\n\t<style type=\"text/css\">\r\n\t\ta{\r\n\t\t\ttext-decoration: none;\r\n\t\t\tcolor:green;\r\n\t\t}\r\n\t</style>\r\n\r\n</head>\r\n<body>\r\n\t<div class=\"easyui-accordion\">\r\n\t\t<div title=\"权限管理\" data-options=\"iconCls:'icon-dperson'\" style=\"overflow:auto;padding:10px;background-color: rgb(233,242,255)\">\r\n\t\t\t<p>\r\n\t\t\t    <div  style=\"background-color: rgb(233,242,255)\">\r\n\t\t\t        <a href=\"javascript:toAddTab('用户管理','/userManager/user.jsp')\" style=\"border:1\">\r\n\t\t\t\t\t<div style=\"margin-left:40%;\">\r\n\t\t\t\t\t\t<img  src=\"");
      out.print(path );
      out.write("/resource/css/themes/icons/extra/16prof.ico\">\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div  style=\"margin-left:30%\">\r\n\t\t\t\t\t用户管理\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t\t<a href=\"javascript:toAddTab('角色管理','/userManager/role.jsp')\">\r\n\t\t\t\t\t<div style=\"margin-left:40%;margin-top:10px\">\r\n\t\t\t\t\t\t<img  src=\"");
      out.print(path );
      out.write("/resource/css/themes/icons/extra/16prof.ico\">\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div  style=\"margin-left:30%\">\r\n\t\t\t\t\t角色管理\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t\t<a href=\"javascript:toAddTab('权限管理','/userManager/funList.jsp')\">\r\n\t\t\t\t\t<div style=\"margin-left:40%;margin-top:10px\">\r\n\t\t\t\t\t\t<img  src=\"");
      out.print(path );
      out.write("/resource/css/themes/icons/extra/16prof.ico\">\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div  style=\"margin-left:30%\">\r\n\t\t\t\t\t权限管理\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t</div>\r\n\t\t\t</p>\r\n\t\t</div>\r\n\t\t<div title=\"学生信息管理\" data-options=\"iconCls:'icon-manager'\" style=\"overflow:auto;padding:10px;background-color: rgb(233,242,255)\">\r\n\t\t\t<p>\r\n\t\t\t\t <div  style=\"background-color: rgb(233,242,255)\">\r\n\t\t\t        <a href=\"#\" style=\"border:1\">\r\n\t\t\t\t\t<div style=\"margin-left:40%;\">\r\n\t\t\t\t\t\t<img  src=\"");
      out.print(path );
      out.write("/resource/css/themes/icons/extra/16prof.ico\">\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div  style=\"margin-left:25%\">\r\n\t\t\t\t\t学生信息维护\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t\t<a href=\"#\">\r\n\t\t\t\t\t<div style=\"margin-left:40%;margin-top:10px\">\r\n\t\t\t\t\t\t<img  src=\"");
      out.print(path );
      out.write("/resource/css/themes/icons/extra/16prof.ico\">\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div  style=\"margin-left:25%\">\r\n\t\t\t\t\t班级考试维护\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t\t<a href=\"#\">\r\n\t\t\t\t\t<div style=\"margin-left:40%;margin-top:10px\">\r\n\t\t\t\t\t\t<img  src=\"");
      out.print(path );
      out.write("/resource/css/themes/icons/extra/16prof.ico\">\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div  style=\"margin-left:25%\">\r\n\t\t\t\t\t学生成绩维护\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t\t<a href=\"#\">\r\n\t\t\t\t\t<div style=\"margin-left:40%;margin-top:10px\">\r\n\t\t\t\t\t\t<img  src=\"");
      out.print(path );
      out.write("/resource/css/themes/icons/extra/16prof.ico\">\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div  style=\"margin-left:25%\">\r\n\t\t\t\t\t学生考勤维护\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t</div>\r\n\t\t\t</p>\r\n\t\t</div>\r\n\t\t<div title=\"课程信息管理\" data-options=\"iconCls:'icon-calc'\" style=\"overflow:auto;padding:10px;\">\r\n\t\t\t<p>\r\n\t\t\t\t<a href=\"javascript:toAddTab('课程维护','/lession/lession.jsp')\">\r\n\t\t\t\t\t<div style=\"margin-left:40%;margin-top:10px\">\r\n\t\t\t\t\t\t<img  src=\"");
      out.print(path );
      out.write("/resource/css/themes/icons/extra/16prof.ico\">\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div  style=\"margin-left:30%\">\r\n\t\t\t\t\t课程维护\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</a>\r\n\t\t\t\t\r\n\t\t\t</p>\r\n\t\t</div>\r\n\t\t<div title=\"学生信息统计\" data-options=\"iconCls:'icon-calc'\" style=\"overflow:auto;padding:10px;\">\r\n\t\t\t<p>\r\n\t\t\t\t<a href=\"#\">\r\n\t\t\t\t\t<div style=\"margin-left:40%;margin-top:10px\">\r\n\t\t\t\t\t\t<img  src=\"");
      out.print(path );
      out.write("/resource/css/themes/icons/extra/16prof.ico\">\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div  style=\"margin-left:30%\">\r\n\t\t\t\t\t成绩合格率\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</a>\r\n\t\t\t\t<a href=\"#\">\r\n\t\t\t\t\t<div style=\"margin-left:40%;margin-top:10px\">\r\n\t\t\t\t\t\t<img  src=\"");
      out.print(path );
      out.write("/resource/css/themes/icons/extra/16prof.ico\">\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div  style=\"margin-left:30%\">\r\n\t\t\t\t\t成绩特评\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</a>\r\n\t\t\t</p>\r\n\t\t</div>\r\n\t</div>\r\n \r\n</body>\r\n</html>\r\n");
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
