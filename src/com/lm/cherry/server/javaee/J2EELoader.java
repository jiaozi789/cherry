package com.lm.cherry.server.javaee;

import java.io.File;

import javax.servlet.ServletContext;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.server.HttpServer;
import com.lm.cherry.server.javaee.servlet.CherryHttpSessionContext;
import com.lm.cherry.server.javaee.servlet.CherryServletContext;
import com.lm.cherry.tool.CherryClassLoader;
/**
 * 通过配置文件的配置加载app
 * 日期:2015-5-19
 * 作者:LM
 */
public class J2EELoader {
	/**
	 * 这里获取的上下文路径 比如/testservlet 通过 http://localhost:8080/testservlet访问
	 */
	private String contextPath;
	/**
	 * 这里获取的实际的后台对应的目录的路径地址
	 */
	private WebParse webParse;
	/**
	 * 这里获取上下文的实际路径
	 */
	private String contextDir;
	/**
	 * serlvet加载器 加载配置的servlet
	 */
	private ServletLoader servletLoader;
	/**
	 * 监听器的加载
	 */
	private ListenerLoader listenerLoader;
	/**
	 * 过滤器的加载
	 */
	private FilterLoader filterLoader;
	/**
	 * 公共的 也就是lib下jar的类加载器 其他项目都公用这个库
	 */
	private ClassLoader pubClassLoader;
	/**
	 * 上下文对象
	 */
	private CherryServletContext servletContext;
	/**
	 * session的上下文
	 */
	private CherryHttpSessionContext sessionContext;
	/**
	 * 当前项目的类加载器
	 */
	private CherryClassLoader cherryLoder;
	/**
	 * http服务器对象
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
		//解析当前项目的web.xml文件
		webParse=new WebParse(this.contextDir,this.contextPath);
		//优先加载当前项目的class目录下的所有类
		cherryLoder=new CherryClassLoader(new File(webParse.getContextDir()+"/WEB-INF/classes").toURL(),pubClassLoader);
		//再加载lib下的所有包和类
		cherryLoder.loadCommonLibrary(webParse.getContextDir()+"/WEB-INF/lib");
		//设置当前线程的类加载器
		Thread.currentThread().setContextClassLoader(cherryLoder);
		//初始化 servlet的创建和加载类
		servletLoader=new ServletLoader(webParse,cherryLoder);
		//初始化监听器
		listenerLoader=new ListenerLoader(this);
		//初始化过滤器
		filterLoader=new FilterLoader(webParse,cherryLoder);
		this.pubClassLoader=pubClassLoader;
		//加载上下文对象
		this.servletContext=new CherryServletContext(this);
		//所有的上下文对象存储到缓存中
		CherryServletContext.contextList.put(contextPath, servletContext);
		//创建session的上下文对象
		sessionContext=new CherryHttpSessionContext();
		this.jspLoader=new JspLoader(ea);
	}
	/**
	 * 加载webxml的资源信息
	 * 日期:2015-5-19
	 * 作者:LM
	 * @throws Exception
	 */
	public void load() throws Exception{
		//加载servlet listeners和filterLoader
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
