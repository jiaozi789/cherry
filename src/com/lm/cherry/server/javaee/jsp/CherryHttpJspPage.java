package com.lm.cherry.server.javaee.jsp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;
/**
 * 所有生成的jsp都需要实现这个类
 * 日期:2015-5-30
 * 作者:LM
 */
public abstract class CherryHttpJspPage implements HttpJspPage {
	/**
	 * 抽象的调用类
	 * 日期:2015-5-30
	 * 作者:LM
	 * @param request
	 * @param response
	 */
	public abstract void doService(HttpServletRequest request,
			HttpServletResponse response);
	/**
	 * 抽象的jsp初始化类
	 * 日期:2015-5-30
	 * 作者:LM
	 */
	public abstract void init();
	/**
	 * 抽象的jsp销毁类
	 * 日期:2015-5-30
	 * 作者:LM
	 */
	public abstract void destroy();
	/**
	 * 默认的jsp调用方法
	 * 日期:2015-5-30
	 * 作者:LM
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void _jspService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doService(request,response);
	}
	/**
	 * 默认的jsp销毁方法
	 * 日期:2015-5-30
	 * 作者:LM
	 */
	public void jspDestroy() {
		destroy();
	}
	/**
	 * 默认的jsp初始化类
	 * 日期:2015-5-30
	 * 作者:LM
	 */
	public void jspInit() {
		init();
	}


}
