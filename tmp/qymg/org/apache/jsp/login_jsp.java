package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import cn.net.tc.yjj.core.web.TcContext;
import cn.net.tc.yjj.ent.dao.impl.ProductEntDaoImpl;
import cn.net.tc.yjj.core.service.InitList;
import cn.net.tc.yjj.ent.dto.EnterPriceInfo;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/WEB-INF/c.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fs_005fif_0026_005ftest;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fs_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.release();
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
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

String path = request.getContextPath();
request.setAttribute("bpath",path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

      out.write("\r\n<HTML lang=cn xml:lang=\"cn\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:a4j = \r\n\"http://ajax4jsf.dev.java.net/ajax\"><HEAD><TITLE>广东省食品药品监督管理系统企业网上办事平台</TITLE>\r\n<META content=\"text/html; charset=gb2312\" http-equiv=Content-Type>\r\n<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/js/Common.js\"></script>\r\n<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/js/dialog.js\"></script>\r\n<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/js/secuinter.js\"></script>\r\n<SCRIPT type=\"text/javascript\">\r\nfunction onloadToShow(){\r\ntry{\r\n\tif(isSecuInterInstalled()){\r\n\t\r\n\t\tvar ttstr=signPKCS7(\"11\",false,false);\r\n\t\tvar rt=verifyPKCS7(\"11\",ttstr);\r\n\t   \r\n\t   if(rt==-1){\r\n\t   \talert(\"安装不成功!\");\r\n\t   \treturn;\r\n\t   }\r\n\t   else if(rt==-2){\r\n\t   \talert(\"签名信息不对，验证不通过\");\r\n\t   \treturn;\r\n\t   }\r\n\t   else if(rt==-3){\r\n\t   \talert(\"签名信息和原文不一致，验证不通过\");\r\n\t   \treturn;\r\n\t   }\r\n\t   else if(rt==0){\r\n\t   \talert(\"验证通过\");\r\n\t   }\r\n\t\tvar prints=getCertThumbprintW();\r\n\t\tif(typeof(prints)!='undefined' &&prints!=null && prints!=''){\r\n\t\t\t//window.location=\"");
      out.print(path);
      out.write("/system/toPrintLogin.do?loginName=\"+prints;\r\n\t\t    var strencode=encodeStr(ttstr);\r\n\t\t\tdocument.getElementById(\"userPrint\").value=strencode;\r\n\t\t    document.forms[0].action=\"");
      out.print(path);
      out.write("/system/togetLogin.do\";\r\n\t\t\tdocument.forms[0].submit();\r\n\t\t\r\n\t\t}else{\r\n\t\t\talert(\"请插入您的usbkey设备\");\r\n\t\t}\r\n\t}else{\r\n\t \talert(\"控件检验失败！请安装驱动\");\r\n\t}\r\n}catch(e){\r\n\talert(\"控件检验失败！请安装驱动\");\r\n}\r\n}\r\nfunction isInstall() {\r\n\tif(isSecuInterInstalled()){\r\n\t\talert(\"控件安装成功！\");\r\n\t\t\r\n        }\r\n        else{\r\n\t   \talert(\"控件安装失败！\");\r\n\t}\r\n\treturn ;\r\n}\r\nfunction checkNetCA() {\r\n\tif(isHasNetCACert()){\r\n\t\talert(\"存在！\");\r\n\t\t\r\n        }\r\n        else{\r\n\t   \talert(\"不存在！\");\r\n\t}\r\n\treturn ;\r\n}\r\nfunction getCertByThumbprintW() {//输入姆印，选择证书\r\n   try{\r\n\t var cert=getCertByThumbprint(text1.value);\r\n\t if(cert!=null){\r\n\t   \tcert.Display();\r\n\t }\r\n\t else{\r\n\t  \talert(\"找不到对应证书！\");\r\n\t }\r\n   }\r\n   catch (e)\r\n   {\r\n\talert(\"测试不成功!\"+e.description);\r\n\treturn ;\r\n   }   \r\n}\r\n\r\n//选择证书姆印\r\nfunction getCertThumbprintW() {\r\n   try{\r\n   \tvar cert=selectCertW();\r\n\treturn getX509CertificateThumbprint(cert);\r\n   }\r\n   catch (e)\r\n   {\r\n\talert(\"测试不成功!\"+e.description);\r\n\treturn ;\r\n   }   \r\n}\r\n\r\nfunction selectCertW() {\r\n   try{\r\n\tvar cert=getX509Certificate(1,0,1,1);\r\n\t//if(cert!=null){\r\n");
      out.write("\t// \tcert.Display();\r\n\t//}\r\n\treturn cert;\r\n    }\r\n   catch (e)\r\n   {\r\n\talert(\"测试不成功!\"+e.description);\r\n\treturn null;\r\n   }  \r\n}\r\n\r\nfunction certInfo() {\r\n   var cert=selectCertW();\r\n   getCertInfo(cert);\r\n}\r\n///////////////////////////////////////////////////////////////////////////////////\r\n//////////////   签名功能 /////////////////////////////////////////////////////////\r\n///////////////////////////////////////////////////////////////////////////////////\r\n/*************** 纯签名  *****************/\r\nfunction signPKCS1EX() {\r\n   try{\r\n   \ttextData.value=signPKCS1(textOld.value);\r\n   }\r\n   catch (e)\r\n   {\r\n\talert(\"测试不成功!\"+e.description);\r\n\treturn null;\r\n   }\r\n}\r\nfunction verifyPKCS1EX() {\r\n   try{\r\n\t   var rt=verifyPKCS1(textOld.value,textData.value);\r\n\t   \r\n\t   if(rt==-1){\r\n\t   \talert(\"安装不成功!\");\r\n\t   }\r\n\t   else if(rt==-2){\r\n\t   \talert(\"签名信息不对，验证不通过\");\r\n\t   }\r\n\t   else if(rt==-3){\r\n\t   \talert(\"签名信息和原文不一致，验证不通过\");\r\n\t   }\r\n\t   else if(rt==0){\r\n\t   \talert(\"验证通过\");\r\n\t   }\r\n   }\r\n   catch (e)\r\n   {\r\n\t\talert(\"测试不成功!\"+e.description);\r\n");
      out.write("\t\treturn null;\r\n   }\r\n   \r\n}\r\n/************  pkcs7签名            ******/\r\nfunction signPKCS7EX() {\r\n   try{\r\n   \ttextData.value=signPKCS7(textOld.value,false,false);\r\n   \talert(textData.value.length);\r\n   }\r\n   catch (e)\r\n   {\r\n\talert(\"测试不成功!\"+e.description);\r\n\treturn null;\r\n   }\r\n}\r\nfunction signPKCS7ByPwdEX() {\r\n   try{\r\n   \ttextData.value=signPKCS7ByPwd(textOld.value,false,pinpwd.value);\r\n   \talert(textData.value.length);\r\n   }\r\n   catch (e)\r\n   {\r\n\talert(\"测试不成功!\"+e.description);\r\n\treturn null;\r\n   }\r\n}\r\n\r\nfunction coSignPKCS7EX() {\r\n   try{\r\n   \t\r\n   \ttextData.value=coSignPKCS7(textOld.value,textData.value,false);\r\n   \talert(textData.value.length);\r\n   }\r\n   catch (e)\r\n   {\r\n\talert(\"测试不成功!\"+e.description);\r\n\treturn null;\r\n   }\r\n}\r\nfunction signPKCS7WithTSAEX() {\r\n   try{\r\n        //tsaUrls=https://classatsa.cnca.net/tsa.asp;https://dts.cnca.net/tsa.asp;\r\n        //http://dts.cnca.net/mstsa.asp 时间戳代码签名用,此处无效\r\n   \ttextData.value=signPKCS7WithTSA(textOld.value,\"https://classatsa.cnca.net/tsa.asp\",false);\r\n   }\r\n");
      out.write("   catch (e)\r\n   {\r\n\t\talert(\"测试不成功!\"+e.description);\r\n\t\treturn null;\r\n   }\r\n}\r\nfunction verifyPKCS7EX() {\r\n   try{\r\n\t   var rt=verifyPKCS7(textOld.value,textData.value);\r\n\t   \r\n\t   if(rt==-1){\r\n\t   \talert(\"安装不成功!\");\r\n\t   }\r\n\t   else if(rt==-2){\r\n\t   \talert(\"签名信息不对，验证不通过\");\r\n\t   }\r\n\t   else if(rt==-3){\r\n\t   \talert(\"签名信息和原文不一致，验证不通过\");\r\n\t   }\r\n\t   else if(rt==0){\r\n\t   \talert(\"验证通过\");\r\n\t   }\r\n\t   \r\n   }\r\n   catch (e)\r\n   {\r\n\t\talert(\"测试不成功!\"+e.description);\r\n\t\treturn null;\r\n   }\r\n   \r\n}\r\nfunction multiSignPKCS7EX(){\r\n   multiSignPKCS7(textOld.value,textMax.value);\r\n}\r\n/////////////////////////////////////////////\r\n//////////////   加密  //////////////////////\r\n///////////////////////////////////////////\r\n\r\nfunction envV() {\r\n   try{\r\n\t   var oCert=getX509Certificate(SECUINTER_CURRENT_USER_STORE,SECUINTER_MY_STORE,SECUINTER_CERTTYPE_ENV,SECUINTER_NETCA_YES);\r\n\t   if(oCert==null){\r\n\t\t\talert(\"未选择证书!\");\r\n\t\t\treturn null;\r\n\t   }\r\n\t   textData1.value=envPKCS7(textOld1.value,oCert);\r\n   }\r\n   catch (e)\r\n   {\r\n\t\talert(\"测试不成功!\" +e.description);\r\n");
      out.write("\t\treturn null;\r\n   }\r\n}\r\nfunction devV() {\r\n   textOld1.value=\"\";\r\n   try{\r\n\t   var rt=devPKCS7(textData1.value);\r\n\t   \r\n\t   if(rt==null){\r\n\t   \talert(\"安装不成功!\");\r\n\t   }\r\n\t   else {\r\n\t        alert(\"解密通过！\");\r\n\t   \ttextOld2.value=rt;\r\n\t   }\r\n   }\r\n   catch (e)\r\n   {\r\n\t\talert(\"测试不成功!\"+e.description );\r\n\t\treturn null;\r\n   }\r\n}\r\nfunction devMutiV() {\r\n\ttry{\r\n\t   \tvar oCert=getX509Certificate(SECUINTER_CURRENT_USER_STORE,SECUINTER_MY_STORE,SECUINTER_CERTTYPE_ENV,SECUINTER_NETCA_YES);\r\n\t\t\r\n                if(oCert==null){\r\n\t\t\talert(\"未选择证书!\");\r\n\t\t\treturn null;\r\n\t        }\r\n\t   \t\r\n\t\tfor(icount=0;icount<textMax1.value;icount++)\r\n\t   \t{\r\n\t\t   textData1.value=envPKCS7(textOld1.value,oCert);\r\n\t\t   \r\n\t\t   textOld2.value=devPKCS7(textData1.value);\r\n\t\t  \r\n\t\t   if(textOld2.value!=textOld1.value){\r\n\t\t    \t\talert(\"加密解密没通过！\"+rt);\r\n\t\t    \t\treturn null;\r\n\t\t    \t\t}\r\n\t   \t}\r\n\t   \talert(\"测试通过\");\r\n   \t}\r\n   \tcatch (e)\r\n\t{\r\n\t\talert(\"测试不成功!\"+e.description);\r\n\t\treturn null;\r\n\t}\r\n}\r\n\r\n \r\n</SCRIPT>\r\n\r\n<STYLE>BODY {\r\n\tBORDER-RIGHT-WIDTH: 0px; MARGIN: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; HEIGHT: 100%; OVERFLOW: hidden; BORDER-LEFT-WIDTH: 0px\r\n");
      out.write("}\r\n.logo {\r\n\tBORDER-BOTTOM: #819ebe 1px solid; PADDING-LEFT: 20px; WIDTH: 100%; BACKGROUND: #9dbad8; HEIGHT: 60px; PADDING-TOP: 6px\r\n}\r\nP {\r\n\tPADDING-LEFT: 20px\r\n}\r\nDIV {\r\n\tBORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; FONT-SIZE: 12px; BORDER-LEFT-WIDTH: 0px\r\n}\r\n.outer {\r\n\tPOSITION: relative; BACKGROUND-COLOR: #e0ebf1; DISPLAY: table; HEIGHT: 98%; OVERFLOW: hidden\r\n}\r\n.middle {\r\n\tPOSITION: absolute; DISPLAY: table-cell; HEIGHT: 88%; VERTICAL-ALIGN: middle; TOP: 50%; LEFT: 50%\r\n}\r\n.inner {\r\n\tPOSITION: relative; MARGIN-BOTTOM: -300px; TOP: -50%; LEFT: -50%\r\n}\r\n.inner1 {\r\n\tPOSITION: relative; TOP: -50%; LEFT: -15%\r\n}\r\n \r\n.border {\r\n\tBACKGROUND: url(");
      out.print(path);
      out.write("/images/body_bg.jpg); BORDER-TOP: #fff 1px solid\r\n}\r\n.text {\r\n\tLINE-HEIGHT: 10px; VERTICAL-ALIGN: top; PADDING-TOP: 5px\r\n}\r\nA:link {\r\n\tCOLOR: #415cf5; TEXT-DECORATION: none\r\n}\r\nA:visited {\r\n\tCOLOR: red; TEXT-DECORATION: none\r\n}\r\nA:hover {\r\n\tCOLOR: #ff3300; TEXT-DECORATION: underline\r\n}\r\n.font12 {\r\n\tCOLOR: #000; FONT-SIZE: 12px\r\n}\r\n.font12_red {\r\n\tPADDING-RIGHT: 4px; FONT-FAMILY: \"宋体\"; COLOR: #9f0038; FONT-SIZE: 12px\r\n}\r\n.input_lookup {\r\n\tBORDER-BOTTOM: #bdbdbd 1px solid; BORDER-LEFT: #bdbdbd 1px solid; BACKGROUND-COLOR: #fff; PADDING-LEFT: 4px; WIDTH: 170px; FONT-FAMILY: \"宋体\"; HEIGHT: 16px; FONT-SIZE: 12px; BORDER-TOP: #bdbdbd 1px solid; BORDER-RIGHT: #bdbdbd 1px solid\r\n}\r\n.input_validate {\r\n\tBORDER-BOTTOM: #bdbdbd 1px solid; BORDER-LEFT: #bdbdbd 1px solid; BACKGROUND-COLOR: #fff; PADDING-LEFT: 4px; WIDTH: 85px; FONT-FAMILY: \"宋体\"; HEIGHT: 16px; FONT-SIZE: 12px; BORDER-TOP: #bdbdbd 1px solid; BORDER-RIGHT: #bdbdbd 1px solid\r\n}\r\n.input_but {\r\n\tPADDING-RIGHT: 6px\r\n}\r\n</STYLE>\r\n\r\n <script type=\"text/javascript\">\r\n");
      out.write("   function registerLogin(){\r\n  \r\n        var name= document.getElementsByName(\"registerUser\")[0].value;\r\n        var pwd=  document.getElementsByName(\"registerPwd\")[0].value;\r\n        if(name==\"\"||pwd==\"\"){\r\n         alert(\"文本框不能为空\");\r\n         return ;\r\n        }\r\n        document.forms[0].action=\"");
      out.print(path);
      out.write("/system/togetRegisterLogin.do\";\r\n\t    document.forms[0].submit();\r\n\t     \r\n   }\r\n \r\n </script>\r\n\r\n<META name=GENERATOR content=\"MSHTML 8.00.6001.19222\"></HEAD>\r\n<BODY style=\"BACKGROUND: url(");
      out.print(path);
      out.write("/images/body_bg.jpg)\" >\r\n <OBJECT ID=\"oSecuInter\"\r\nCLASSID=\"CLSID:11E27019-33D7-4B76-B73C-11DB7F496813\" \r\nCODEBASE=\"SecuInter.dll#version=4,1,0,1\" style=\"width: 5px; height: 5px\" border=\"1\" visible=\"1\">\r\n</OBJECT>\r\n<FORM id=loginForm method=post name=loginForm  >\r\n<input type=\"hidden\" id=\"userPrint\" name=\"userPrint\" value=\"\"/>\r\n<DIV class=border,outer>\r\n<DIV class=middle>\r\n<DIV class=inner><IMG src=\"");
      out.print(path);
      out.write("/images/job.jpg\" width=701 height=485> </DIV>\r\n<div class=\"inner1\">\r\n   <TABLE cellSpacing=0 cellPadding=0>\r\n        <TBODY>\r\n        <TR>\r\n        <TD class=font12 height=25 align=middle>用户名：<input class=\"text\" type=\"text\"  name=\"registerUser\"   value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.registerUser}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"></td>\r\n          </tr>\r\n          <tr height=\"1px\"><td></td>\r\n         </tr>\r\n          <tr>\r\n         <TD class=font12 height=25 align=middle>密&nbsp;&nbsp;码：<input class=\"text\" type=\"text\"  name=\"registerPwd\"   value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.registerPwd}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"></td>\r\n         </tr>\r\n          <tr align=\"center\">\r\n           <TD class=font12 height=25 align=\"center\"><input class=\"button\" type=\"button\"   name=\"loginIn\"   value=\"登录\" onclick=\"registerLogin()\">\r\n         <input class=\"button\" type=\"button\" onclick=\"onloadToShow()\" name=\"loginIn\"   value=\"CA登录\">\r\n   \t\t   </TD>\r\n          </tr>\r\n          <tr>\r\n       \r\n            \r\n         \r\n    \r\n        <TR>\r\n          <TD class=font12 height=25 align=middle>\r\n\t\t  <A  href=\"applyDataManage.htm\">申请数字证书说明</A> </TD></TR>\r\n        <TR>\r\n          <TD class=font12 height=25 align=middle><A  href=\"#\">安装和使用数字证书说明</A> </TD></TR>\r\n        <TR>\r\n          <TD class=font12 height=25 align=middle>\r\n\t\t  <A  href=\"#\"><IMG border=0 src=\"");
      out.print(path);
      out.write("/images/driver_old.jpg\"> 企业证书驱动</A> </TD></TR>\r\n        <TR>\r\n          <TD class=font12 height=25 align=left>\r\n\t\t  <A  href=\"#\">\r\n\t\t  <IMG border=0  src=\"");
      out.print(path);
      out.write("/images/driver_new.jpg\">办公证书驱动for WinXP</A> </TD></TR>\r\n        <TR>\r\n          <TD class=font12 align=left>\r\n\t\t  <A href=\"#\">\r\n\t\t  <IMG  border=0 src=\"");
      out.print(path);
      out.write("/images/driver_new.jpg\">办公证书驱动for Win7</A> </TD></TR>\r\n\t\t</TBODY></TABLE>\r\n\t\t\r\n\t\t </DIV></DIV> \r\n</div>\r\n\r\n \r\n </FORM> </BODY></HTML>\r\n ");
      if (_jspx_meth_s_005fif_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      out.write(' ');
      if (_jspx_meth_s_005fif_005f1(_jspx_page_context))
        return;
      out.write("\r\n  ");
      if (_jspx_meth_s_005fif_005f2(_jspx_page_context))
        return;
      out.write("\r\n  ");
      if (_jspx_meth_s_005fif_005f3(_jspx_page_context))
        return;
      out.write("\r\n   ");
      if (_jspx_meth_s_005fif_005f4(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
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

  private boolean _jspx_meth_s_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_005fif_005f0 = (org.apache.struts2.views.jsp.IfTag) _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_s_005fif_005f0.setParent(null);
    // /login.jsp(444,1) name = test type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_s_005fif_005f0.setTest("#request.isLogin==1");
    int _jspx_eval_s_005fif_005f0 = _jspx_th_s_005fif_005f0.doStartTag();
    if (_jspx_eval_s_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_005fif_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_005fif_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_005fif_005f0.doInitBody();
      }
      do {
        out.write("\r\n <script>\r\n \talert(\"登录失败 验证签名失败\");\r\n </script>\r\n ");
        int evalDoAfterBody = _jspx_th_s_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_005fif_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_s_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.reuse(_jspx_th_s_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.reuse(_jspx_th_s_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_s_005fif_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_005fif_005f1 = (org.apache.struts2.views.jsp.IfTag) _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_s_005fif_005f1.setParent(null);
    // /login.jsp(449,1) name = test type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_s_005fif_005f1.setTest("#request.isLogin==2");
    int _jspx_eval_s_005fif_005f1 = _jspx_th_s_005fif_005f1.doStartTag();
    if (_jspx_eval_s_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_005fif_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_005fif_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_005fif_005f1.doInitBody();
      }
      do {
        out.write("\r\n <script>\r\n \talert(\"登录失败 出现异常\");\r\n </script>\r\n ");
        int evalDoAfterBody = _jspx_th_s_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_005fif_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_s_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.reuse(_jspx_th_s_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.reuse(_jspx_th_s_005fif_005f1);
    return false;
  }

  private boolean _jspx_meth_s_005fif_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_005fif_005f2 = (org.apache.struts2.views.jsp.IfTag) _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_005fif_005f2.setPageContext(_jspx_page_context);
    _jspx_th_s_005fif_005f2.setParent(null);
    // /login.jsp(454,2) name = test type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_s_005fif_005f2.setTest("#request.isLogin==3");
    int _jspx_eval_s_005fif_005f2 = _jspx_th_s_005fif_005f2.doStartTag();
    if (_jspx_eval_s_005fif_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_005fif_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_005fif_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_005fif_005f2.doInitBody();
      }
      do {
        out.write("\r\n <script>\r\n \talert(\"登录失败无法获取母印信息\");\r\n </script>\r\n ");
        int evalDoAfterBody = _jspx_th_s_005fif_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_005fif_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_s_005fif_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.reuse(_jspx_th_s_005fif_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.reuse(_jspx_th_s_005fif_005f2);
    return false;
  }

  private boolean _jspx_meth_s_005fif_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_005fif_005f3 = (org.apache.struts2.views.jsp.IfTag) _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_005fif_005f3.setPageContext(_jspx_page_context);
    _jspx_th_s_005fif_005f3.setParent(null);
    // /login.jsp(459,2) name = test type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_s_005fif_005f3.setTest("#request.isLogin==4");
    int _jspx_eval_s_005fif_005f3 = _jspx_th_s_005fif_005f3.doStartTag();
    if (_jspx_eval_s_005fif_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_005fif_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_005fif_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_005fif_005f3.doInitBody();
      }
      do {
        out.write("\r\n <script>\r\n \talert(\"登录失败 该key 无法找到对应的用户信息\");\r\n </script>\r\n ");
        int evalDoAfterBody = _jspx_th_s_005fif_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_005fif_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_s_005fif_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.reuse(_jspx_th_s_005fif_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.reuse(_jspx_th_s_005fif_005f3);
    return false;
  }

  private boolean _jspx_meth_s_005fif_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_005fif_005f4 = (org.apache.struts2.views.jsp.IfTag) _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_005fif_005f4.setPageContext(_jspx_page_context);
    _jspx_th_s_005fif_005f4.setParent(null);
    // /login.jsp(464,3) name = test type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_s_005fif_005f4.setTest("#request.isLogin==5");
    int _jspx_eval_s_005fif_005f4 = _jspx_th_s_005fif_005f4.doStartTag();
    if (_jspx_eval_s_005fif_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_005fif_005f4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_005fif_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_005fif_005f4.doInitBody();
      }
      do {
        out.write("\r\n <script>\r\n \talert(\"您所绑定的用户没有绑定企业\");\r\n </script>\r\n ");
        int evalDoAfterBody = _jspx_th_s_005fif_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_005fif_005f4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_s_005fif_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.reuse(_jspx_th_s_005fif_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fif_0026_005ftest.reuse(_jspx_th_s_005fif_005f4);
    return false;
  }
}
