package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.util.*;

public final class top_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n  <head>\r\n    <base href=\"");
      out.print(basePath);
      out.write("\">\r\n    \r\n    <title>My JSP 'top.jsp' starting page</title>\r\n    \r\n\t<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n\t<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n\t<meta http-equiv=\"expires\" content=\"0\">    \r\n\t<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">\r\n\t<meta http-equiv=\"description\" content=\"This is my page\">\r\n\t<!--\r\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\r\n\t-->\r\n\t");
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
      out.write("\r\n  </head>\r\n  \r\n  <body style=\"padding:0px\">\r\n    <div style=\"height:23px;width:100%;background-image:url(");
      out.print(path);
      out.write("/resource/images/logo_02.gif)\"></div>\r\n    <div>\r\n    \t<div style=\"position:absolute;top:23px;left:0px;width:341px;height:54px;background-image:url(");
      out.print(path);
      out.write("/resource/images/logo_04.gif);background-repeat: no-repeat\"></div>\r\n    \t<div style=\"margin-left:341px;height:54px;background-image:url(");
      out.print(path);
      out.write("/resource/images/logo_05.gif)\"></div>\r\n    </div>\r\n     <div style=\"height:23px;width:100%;background-image:url(");
      out.print(path);
      out.write("/resource/images/logo_08.gif)\"></div>\r\n    <div></div>\r\n    <div style=\"position:absolute;top:70%;left:80%\">\r\n       \t<font color='green'> \r\n       \t欢迎&nbsp;<b>JIAOZI</b> &nbsp;&nbsp;<a href=\"#\">修改密码</a>&nbsp;&nbsp;<a href=\"#\">安全退出</a>\r\n       \t</font> \r\n    </div>\r\n  </body>\r\n</html>\r\n");
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
