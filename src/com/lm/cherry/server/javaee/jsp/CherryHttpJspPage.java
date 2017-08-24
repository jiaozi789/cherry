package com.lm.cherry.server.javaee.jsp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;
/**
 * �������ɵ�jsp����Ҫʵ�������
 * ����:2015-5-30
 * ����:LM
 */
public abstract class CherryHttpJspPage implements HttpJspPage {
	/**
	 * ����ĵ�����
	 * ����:2015-5-30
	 * ����:LM
	 * @param request
	 * @param response
	 */
	public abstract void doService(HttpServletRequest request,
			HttpServletResponse response);
	/**
	 * �����jsp��ʼ����
	 * ����:2015-5-30
	 * ����:LM
	 */
	public abstract void init();
	/**
	 * �����jsp������
	 * ����:2015-5-30
	 * ����:LM
	 */
	public abstract void destroy();
	/**
	 * Ĭ�ϵ�jsp���÷���
	 * ����:2015-5-30
	 * ����:LM
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
	 * Ĭ�ϵ�jsp���ٷ���
	 * ����:2015-5-30
	 * ����:LM
	 */
	public void jspDestroy() {
		destroy();
	}
	/**
	 * Ĭ�ϵ�jsp��ʼ����
	 * ����:2015-5-30
	 * ����:LM
	 */
	public void jspInit() {
		init();
	}


}
