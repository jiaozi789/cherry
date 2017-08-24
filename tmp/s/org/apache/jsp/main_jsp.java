package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.util.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n\t<script type=\"text/javascript\" src=\"");
      out.print(path );
      out.write("/resource/js/jquery.sparkline.js\"></script>\r\n\t<script type=\"text/javascript\" src=\"");
      out.print(path );
      out.write("/resource/js/chart/Chart.js\"></script>\r\n\t<script type=\"text/javascript\">\r\n\t\t$(document).ready(function(){\r\n\t\t\t$(function () {\r\n\t\t\t    //bar图 http://omnipotent.net/jquery.sparkline/#interactive\r\n\t\t\t    //$('.bar').sparkline([ [1,4], [2, 3], [3, 2], [4, 1] ], { type: 'bar',barWidth:200,height:200 });\r\n\t\t\t    var randomScalingFactor = function() {\r\n\t\t\t        return Math.round(Math.random() * 100);\r\n\t\t\t    };\r\n\t\t\t    //http://www.bootcss.com/p/chart.js/docs/\r\n\t\t\t    var ctx = $(\"#myChart\").get(0).getContext(\"2d\");\r\n\t\t\t    var barChartData = {\r\n\t\t            labels: [\"10-01\", \"10-02\", \"10-03\", \"10-04\", \"10-05\", \"10-06\", \"10-07\"],\r\n\t\t            datasets: [{\r\n\t\t                label: '结果1',\r\n\t\t                backgroundColor: \"green\",\r\n\t\t                data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]\r\n\t\t            }]\r\n\t\t\r\n\t\t        };\r\n\t\t        window.myBar = new Chart(ctx, {\r\n\t                type: 'bar',\r\n");
      out.write("\t                data: barChartData,\r\n\t                options: {\r\n\t                    responsive: true,\r\n\t                }\r\n\t            });\r\n\t            \r\n\t            \r\n\t            \r\n\t            \r\n\t            var ctx1 = $(\"#myLineChart\").get(0).getContext(\"2d\");\r\n\t            var lineData={\r\n\t                labels: [\"10-01\", \"10-02\", \"10-03\", \"10-04\", \"10-05\", \"10-06\", \"10-07\"],\r\n\t                datasets: [{\r\n\t\t                    label: \"结果1\",\r\n\t\t                    fill:true,\r\n\t\t                    pointHoverBorderWidth: 2,\r\n\t\t                    backgroundColor:'green',\r\n\t\t                    data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()],\r\n\t\t                }]\r\n\t            };\r\n\t            /**\r\n\t            window.myLine = new Chart(ctx1, {\r\n\t                type: 'line',\r\n\t                data: lineData,\r\n\t                options: {\r\n\t                    responsive: true,\r\n\t                }\r\n");
      out.write("\t            });\r\n\t            **/\r\n\t\t\t});\r\n\t\t});\r\n\t</script>\r\n</head>\r\n<body>\r\n    <div style=\"height:20px\">\r\n    \r\n    </div>\r\n    <div style=\"margin-left:20px;margin-bottom:20px\">\r\n\t</div>\r\n    <div style=\"margin-left:20px\">\r\n\t<div id=\"myDiv\" class=\"easyui-panel\" title=\"学生成绩统计\" style=\"width:400px;\">\r\n\t\t<canvas id=\"myChart\"></canvas>\r\n\t</div>\r\n\t</div>\r\n\t\r\n\t\r\n\t<div style=\"width:500px;height:500px\">\r\n\t<canvas id=\"myLineChart\"></canvas>\r\n\t</div>\r\n</body>\r\n</html>\r\n");
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
