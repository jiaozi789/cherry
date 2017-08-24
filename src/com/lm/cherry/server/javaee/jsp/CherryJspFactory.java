package com.lm.cherry.server.javaee.jsp;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspEngineInfo;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
/**
 * 实现的Jsp的工厂类
 * 日期:2015-5-30
 * 作者:LM
 */
public class CherryJspFactory extends JspFactory {
	CherryJspEngineInfo engine;
	public CherryJspFactory(){
		engine=new CherryJspEngineInfo();
	}
	@Override
	public JspEngineInfo getEngineInfo() {
		return engine;
	}

	@Override
	public JspApplicationContext getJspApplicationContext(ServletContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageContext getPageContext(Servlet servlet, ServletRequest request,
			ServletResponse response, String errorPageURL,
			boolean needsSession, int buffer, boolean autoflush) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void releasePageContext(PageContext pc) {
		// TODO Auto-generated method stub

	}

}
