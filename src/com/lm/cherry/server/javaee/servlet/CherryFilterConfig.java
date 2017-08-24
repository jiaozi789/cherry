package com.lm.cherry.server.javaee.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import com.lm.cherry.tool.SystemUtils;
/**
 * 实现filter的配置
 * 日期:2015-5-27
 * 作者:LM
 */
public class CherryFilterConfig implements FilterConfig {
	private Map filterOther=null;
	private Map initParam=null;
	private ServletContext sc;
	public CherryFilterConfig(Map filterOther,ServletContext sc){
		this.filterOther=filterOther;
		this.sc=sc;
		this.initParam=(filterOther.containsKey("init-param")?(Map)filterOther.get("init-param"):new HashMap());
	}
	public String getFilterName() {
		return SystemUtils.trim(filterOther.get("filter-name"));
	}

	public String getInitParameter(String name) {
		return initParam.containsKey(name)?initParam.get(name).toString():null;
	}

	public Enumeration getInitParameterNames() {
		Vector v=new Vector();
		v.addAll(initParam.keySet());
		return v.elements();
	}

	public ServletContext getServletContext() {
		return sc;
	}

}
