package com.lm.cherry.server.javaee;

import java.io.File;

import javax.servlet.ServletContext;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.server.HttpServer;
import com.lm.cherry.server.javaee.servlet.CherryHttpSessionContext;
import com.lm.cherry.server.javaee.servlet.CherryServletContext;
import com.lm.cherry.tool.CherryClassLoader;
/**
 * ͨ�������ļ������ü���app
 * ����:2015-5-19
 * ����:LM
 */
public class J2EELoader {
	/**
	 * �����ȡ��������·�� ����/testservlet ͨ�� http://localhost:8080/testservlet����
	 */
	private String contextPath;
	/**
	 * �����ȡ��ʵ�ʵĺ�̨��Ӧ��Ŀ¼��·����ַ
	 */
	private WebParse webParse;
	/**
	 * �����ȡ�����ĵ�ʵ��·��
	 */
	private String contextDir;
	/**
	 * serlvet������ �������õ�servlet
	 */
	private ServletLoader servletLoader;
	/**
	 * �������ļ���
	 */
	private ListenerLoader listenerLoader;
	/**
	 * �������ļ���
	 */
	private FilterLoader filterLoader;
	/**
	 * ������ Ҳ����lib��jar��������� ������Ŀ�����������
	 */
	private ClassLoader pubClassLoader;
	/**
	 * �����Ķ���
	 */
	private CherryServletContext servletContext;
	/**
	 * session��������
	 */
	private CherryHttpSessionContext sessionContext;
	/**
	 * ��ǰ��Ŀ���������
	 */
	private CherryClassLoader cherryLoder;
	/**
	 * http����������
	 */
	private HttpServer httpServer;
	private JspLoader jspLoader;
	public JspLoader getJspLoader() {
		return jspLoader;
	}
	public void setJspLoader(JspLoader jspLoader) {
		this.jspLoader = jspLoader;
	}
	public HttpServer getHttpServer() {
		return httpServer;
	}
	public void setHttpServer(HttpServer httpServer) {
		this.httpServer = httpServer;
	}
	public J2EELoader(Entry.J2EEApp ea,ClassLoader pubClassLoader,HttpServer server) throws Exception{
		this.contextPath=ea.getPath();
		this.contextDir=ea.getDir();
		this.httpServer=server;
		//������ǰ��Ŀ��web.xml�ļ�
		webParse=new WebParse(this.contextDir,this.contextPath);
		//���ȼ��ص�ǰ��Ŀ��classĿ¼�µ�������
		cherryLoder=new CherryClassLoader(new File(webParse.getContextDir()+"/WEB-INF/classes").toURL(),pubClassLoader);
		//�ټ���lib�µ����а�����
		cherryLoder.loadCommonLibrary(webParse.getContextDir()+"/WEB-INF/lib");
		//���õ�ǰ�̵߳��������
		Thread.currentThread().setContextClassLoader(cherryLoder);
		//��ʼ�� servlet�Ĵ����ͼ�����
		servletLoader=new ServletLoader(webParse,cherryLoder);
		//��ʼ��������
		listenerLoader=new ListenerLoader(this);
		//��ʼ��������
		filterLoader=new FilterLoader(webParse,cherryLoder);
		this.pubClassLoader=pubClassLoader;
		//���������Ķ���
		this.servletContext=new CherryServletContext(this);
		//���е������Ķ���洢��������
		CherryServletContext.contextList.put(contextPath, servletContext);
		//����session�������Ķ���
		sessionContext=new CherryHttpSessionContext();
		this.jspLoader=new JspLoader(ea);
	}
	/**
	 * ����webxml����Դ��Ϣ
	 * ����:2015-5-19
	 * ����:LM
	 * @throws Exception
	 */
	public void load() throws Exception{
		//����servlet listeners��filterLoader
		listenerLoader.load();
		filterLoader.load();
		servletLoader.load();
		
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public WebParse getWebParse() {
		return webParse;
	}
	public void setWebParse(WebParse webParse) {
		this.webParse = webParse;
	}
	public String getContextDir() {
		return contextDir;
	}
	public void setContextDir(String contextDir) {
		this.contextDir = contextDir;
	}
	public ServletLoader getServletLoader() {
		return servletLoader;
	}
	public void setServletLoader(ServletLoader servletLoader) {
		this.servletLoader = servletLoader;
	}
	public ClassLoader getPubClassLoader() {
		return pubClassLoader;
	}
	public void setPubClassLoader(ClassLoader pubClassLoader) {
		this.pubClassLoader = pubClassLoader;
	}
	public CherryClassLoader getCherryLoder() {
		return cherryLoder;
	}
	public void setCherryLoder(CherryClassLoader cherryLoder) {
		this.cherryLoder = cherryLoder;
	}
	public CherryServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(CherryServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public CherryHttpSessionContext getSessionContext() {
		return sessionContext;
	}
	public void setSessionContext(CherryHttpSessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
	public ListenerLoader getListenerLoader() {
		return listenerLoader;
	}
	public void setListenerLoader(ListenerLoader listenerLoader) {
		this.listenerLoader = listenerLoader;
	}
	public FilterLoader getFilterLoader() {
		return filterLoader;
	}
	public void setFilterLoader(FilterLoader filterLoader) {
		this.filterLoader = filterLoader;
	}
}
