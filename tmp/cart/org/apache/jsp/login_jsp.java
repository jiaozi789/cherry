package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

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
      out.write("\">\r\n    \r\n    <title>My JSP 'login.jsp' starting page</title>\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/css.css\" />\r\n\t<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n\t<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n\t<meta http-equiv=\"expires\" content=\"0\">    \r\n\t<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">\r\n\t<meta http-equiv=\"description\" content=\"This is my page\">\r\n\t<!--\r\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\r\n\t-->\r\n\r\n  </head>\r\n  \r\n  <body>\r\n    \r\n\r\n\r\n\r\n<div class=\"header\">\r\n</div>\r\n\r\n\r\n<div style=\"width:1000px;margin: 0 auto;margin-top:10px;\">\r\n<div class=\"linktree\">\r\n\r\n<br>\r\n \r\n  </div>\r\n<div id=\"content\" style='text-align:center'>  \r\n    <div class=\"right\">\r\n      <h2>登录界面</h2>\r\n      <form action=\"");
      out.print(basePath );
      out.write("/system/login.action\" id=\"login\" method=\"post\">\r\n        <div class=\"content\">\r\n          <b>账号:</b>\r\n          <input type=\"text\" name=\"userName\" value=\"\" />\r\n          <br />\r\n          <br />\r\n          <b>密码:</b>\r\n          <input type=\"password\" name=\"password\" value=\"\" />\r\n          <br />\r\n          <br />\r\n          <a href=\"javascript:document.forms[0].submit()\" class=\"button\"><span>Login</span></a>\r\n                  </div>\r\n      </form>\r\n    </div>\r\n\r\n</div>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<div class=\"icart-footer\">\r\n<div class=\"icart-footer-top\">\r\n\t<div style=\"width:1000px; margin:0 auto;\">\t\r\n\t</div>\r\n</div>\r\n\r\n\t\t\r\n\r\n</div></div>\r\n  </body>\r\n</html>\r\n");
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
