package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.util.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n\t<meta charset=\"UTF-8\">\r\n\t<title>学生积分系统-登录</title>\r\n\t");
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
      out.write("\r\n</head>\r\n\t<div style=\"position: absolute;top:200px;left:500px\">\r\n\t<div style=\"margin:20px 0;\"></div>\r\n\t<div class=\"easyui-panel\" title=\"用户登陆\" style=\"width:400px\">\r\n\t\t<div style=\"padding:10px 60px 20px 80px\"> \r\n\t    <form id=\"loginForm\" method=\"post\" action=\"index.jsp\">\r\n\t    \t<table cellpadding=\"5\">\r\n\t    \t\t<tr>\r\n\t    \t\t\t<td>用户名:</td>\r\n\t    \t\t\t<td><input class=\"easyui-textbox\" type=\"text\" name=\"name\" data-options=\"required:true\" style=\"width:100%\"></input></td>\r\n\t    \t\t</tr>\r\n\t    \t\t<tr>\r\n\t    \t\t\t<td>密码:</td>\r\n\t    \t\t\t<td><input class=\"easyui-textbox\" type=\"text\" name=\"email\" data-options=\"required:true\" style=\"width:100%\"></input></td>\r\n\t    \t\t</tr>\r\n\t    \t\t\r\n\t    \t</table>\r\n\t    </form>\r\n\t    <div style=\"text-align:center;padding:5px\">\r\n\t    \t<a href=\"javascript:document.forms[0].submit()\" class=\"easyui-linkbutton\" iconCls=\"icon-ok\" style=\"width:80px;height:25px\">提交</a>\r\n\t\t\t<a href=\"javascript:document.forms[0].reset()\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\" style=\"width:80px;height:25px\">重置</a>\r\n");
      out.write("\t    </div>\r\n\t    </div>\r\n\t</div>\r\n\t</div>\r\n</body>\r\n</html>\r\n");
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
