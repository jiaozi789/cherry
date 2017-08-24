package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class wishlist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
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

      out.write("\r\n\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n  <head>\r\n    <base href=\"");
      out.print(basePath);
      out.write("\">\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/css.css\" />\r\n    <title>商品列表页面</title>\r\n    \r\n\t<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n\t<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n\t<meta http-equiv=\"expires\" content=\"0\">    \r\n\t<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">\r\n\t<meta http-equiv=\"description\" content=\"This is my page\">\r\n\t<!--\r\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\r\n\t-->\r\n\r\n  </head>\r\n  \r\n  <body>\r\n    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "common.jsp", out, false);
      out.write("\r\n\r\n\r\n\r\n<div class=\"header\">\r\n</div>\r\n\r\n\r\n<div style=\"width:1000px;margin: 0 auto;margin-top:10px;\">\r\n<div class=\"linktree\">\r\n\r\n<br>\r\n \r\n  </div>\r\n<div id=\"content\">  \r\n    <form action=\"#\" method=\"post\" id=\"wishlist\">\r\n    <div class=\"wishlist-product\">\r\n      <table>\r\n        <thead>\r\n          <tr>\r\n            <td class=\"image\" >产品展示</td>\r\n            <td class=\"name\"  >产品名称</td>\r\n            <td class=\"model\">类别</td>\r\n            <td class=\"stock\">仓库</td>\r\n            <td class=\"price\">单价</td>\r\n            <td class=\"cart\">加入购物车</td>\r\n          </tr>\r\n        </thead>\r\n        <tbody>\r\n          \r\n          \r\n            ");
      //  c:forEach
      org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
      _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fforEach_005f0.setParent(null);
      // /wishlist.jsp(58,12) name = items type = java.lang.Object reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${goodList.list}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
      // /wishlist.jsp(58,12) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setVar("god");
      int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
        if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n                    <tr>\r\n            \r\n            <td class=\"image\"><a href=\"#\"><img src=\"");
            out.print(basePath);
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${god.imagePath }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
            out.write("\" alt=\"iMac\" title=\"iMac\" /></a>\r\n              </td>\r\n            <td class=\"name\"><a href=\"#\"><font size=3>");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${god.name }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
            out.write("</font></a>\r\n            <br/><br/><div style='width:90%'>");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${god.descp }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
            out.write("</div></div>\r\n            </td>\r\n            <td class=\"model\">");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${god.model }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
            out.write("</td>\r\n            <td class=\"stock\">");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${god.stock }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
            out.write("</td>\r\n            <td class=\"price\"><div><font color=red>¥");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${god.price }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
            out.write("</font></div>\r\n              </td>\r\n            <td ><a class=\"button\" href=\"");
            out.print(request.getContextPath() );
            out.write("/carts/addCart.action?goodId=");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${god.id }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
            out.write("\"><span>加入购物车</span></a></td>\r\n          </tr>\r\n          \r\n          ");
            int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
      } catch (Throwable _jspx_exception) {
        while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_c_005fforEach_005f0.doFinally();
        _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
      }
      out.write("\r\n                    \r\n          <tr>\r\n          \t\t\t<td colspan=\"6\" style=\"text-align:right\">\r\n          \t\t\t<a style=\"text-decoration none\" href=\"");
      out.print(basePath);
      out.write("/goods/listGoods.action?curPage=1\">首页</a> \r\n          \t\t\t<a style=\"text-decoration none\" href=\"");
      out.print(basePath);
      out.write("/goods/listGoods.action?curPage=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${goodList.prePage}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\">上一页 </a>\r\n          \t\t\t&nbsp;<input name='curPage' style='width:20px;height:20px' value='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${goodList.curPage}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("'/>\r\n          \t\t\t<a style=\"text-decoration none\" href=\"");
      out.print(basePath);
      out.write("/goods/listGoods.action?curPage=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${goodList.nextPage}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\">下一页</a>\r\n          \t\t\t<a style=\"text-decoration none\" href=\"");
      out.print(basePath);
      out.write("/goods/listGoods.action?curPage=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${goodList.totalPage}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"> 尾页</a>&nbsp;<span style='margin-top:1px'>共");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${goodList.totalPage}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("页</span></td>\r\n          </tr>\r\n                  </tbody>\r\n      </table>\r\n    </div>\r\n  </form>\r\n  <div class=\"buttons\">\r\n    <div class=\"left\"><a href=\"#\" class=\"button\"><span>Back</span></a></div>\r\n    <div class=\"right\"><a class=\"button\"><span>Update</span></a></div>\r\n  </div>\r\n    </div>\r\n</div>\r\n</div>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<div class=\"icart-footer\">\r\n<div class=\"icart-footer-top\">\r\n\t<div style=\"width:1000px; margin:0 auto;\">\t\r\n\t</div>\r\n</div>\r\n\r\n\t\t\r\n\r\n</div></div>\r\n  </body>\r\n</html>\r\n");
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
