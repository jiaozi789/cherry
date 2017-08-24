package com.lm.cherry.server.javaee.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
/**
 * 用户获取servlet的一些配置信息
 * 日期:2015-5-19
 * 作者:LM
 */
public class CherryServletConfig implements ServletConfig {
	private HttpServlet hs=null;
	private Map servletOther=null;
	private Map initParam=null;
	private ServletContext sc;
	public CherryServletConfig(HttpServlet hs,Map servletOther,ServletContext sc){
		this.hs=hs;
		this.servletOther=servletOther;
		this.sc=sc;
		this.initParam=(servletOther.containsKey("init-param")?(Map)servletOther.get("init-param"):new HashMap());
	}
	public CherryServletConfig(ServletContext sc){
		this.sc=sc;
	}
	public String getInitParameter(String name) { 
		if(hs!=null){
			return initParam.containsKey(name)?initParam.get(name).toString().trim():null;
		}
		return null;
	}
	public Enumeration getInitParameterNames() {
		if(hs!=null){
			Vector v=new Vector();
			v.addAll(initParam.keySet());
			return v.elements();
		}
		return null;
	}

	public ServletContext getServletContext() {
		return sc;
	}
	public String getServletName() {
		if(hs!=null){
			String servletName=servletOther.get("servlet-name").toString();
			return servletName;
		}
		return null;
	}

}
