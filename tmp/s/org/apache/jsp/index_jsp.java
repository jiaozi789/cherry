package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.util.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n\t<meta charset=\"UTF-8\">\r\n\t<title>学生激励积分系统</title>\r\n\t");
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
      out.write("\r\n</head>\r\n<body>\r\n\t\r\n\t<div class=\"easyui-layout\" style=\"width:100%;height:100%;\">\r\n\t\t\t<div data-options=\"region:'north'\" style=\"height:100px\">\r\n\t\t\t<iframe name=\"mainFrame\" src=\"top.jsp\"  width=\"100%\" height=\"100%\"  frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" allowtransparency=\"yes\"></iframe>\r\n\t\t\t</div>\r\n\t\t\t<!-- title=\"用户导航\" -->\r\n\t\t\t<div data-options=\"region:'west',split:true,iconCls:'icon-world'\"  style=\"width:200px;\">\r\n\t\t\t\t<iframe name=\"leftFrame\" src=\"left.jsp\"  width=\"100%\" height=\"100%\"  frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" allowtransparency=\"yes\"></iframe>\r\n\t\t\t</div>\r\n\t\t\t<div id=\"naviTab\" class=\"easyui-tabs\" style=\"border-left:0;border-top:0;border-right:0\" data-options=\"region:'center',iconCls:'icon-ok'\">\r\n\t\t\t    <div id=\"hello\" title=\"欢迎页\" >   \r\n\t\t\t        <iframe name=\"mainFrame\" src=\"main.jsp\"  width=\"100%\" height=\"100%\"  frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" allowtransparency=\"yes\"></iframe>\r\n");
      out.write("\t\t\t    </div>\r\n\t\t\t</div>\r\n\t</div>\r\n \r\n</body>\r\n</html>\r\n");
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
