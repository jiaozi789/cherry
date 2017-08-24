package com.lm.cherry.server.javaee.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.server.javaee.J2EELoader;
import com.lm.cherry.server.javaee.ListenersManager;
import com.lm.cherry.server.javaee.WebParse;
import com.lm.cherry.tool.ContextTypeMap;
import com.lm.cherry.tool.Log;
import com.lm.cherry.tool.SystemUtils;
/**
 * ʵ��ȫ�ֵ�servletcontext���� ��Ŀ֮���໥����
 * ����:2015-5-19
 * ����:LM
 */
public class CherryServletContext implements ServletContext {
	Log log=null;
	public static Map<String,ServletContext> contextList=new HashMap();
	/**
	 * servletcontext�������õļ�ֵ��
	 */
	private Map attributeMap=new HashMap();
	/**
	 * �����ĵ�·��
	 */
	private String contextPath=null;
	/**
	 * webxml������
	 */
	private WebParse webp=null;
	/**
	 * j2ee������
	 */
	private J2EELoader j2eeLoader=null;
	private ListenersManager listenerManager=null;
	/**
	 * ���췽�� ����webxml������Ϣ
	 * @param webp
	 */
	public CherryServletContext(J2EELoader j2eeLoader){
		this.webp=j2eeLoader.getWebParse();
		this.j2eeLoader=j2eeLoader;
		listenerManager=new ListenersManager(j2eeLoader.getListenerLoader());
		this.contextInitialized();
		
	}
	public void contextInitialized(){
		try {
			listenerManager.attachEvent(ServletContextListener.class, "contextInitialized", new Object[]{new ServletContextEvent(this)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ע������
	 * ����:2015-5-27
	 * ����:LM
	 */
	public void contextDestroy(){
		try {
			listenerManager.attachEvent(ServletContextListener.class, "contextDestroyed", new Object[]{new ServletContextEvent(this)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ʵ�ֻ�ȡĳ������ֵ�ķ���
	 * ����:2015-5-19
	 * ����:LM
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name) {
		return attributeMap.get(name);
	}
	/**
	 * ʵ�ֻ�ȡ���е��������Ƶļ���
	 * ����:2015-5-19
	 * ����:LM
	 * @return
	 */
	public Enumeration getAttributeNames() {
		Vector vector=new Vector();
		vector.addAll(attributeMap.keySet());
		return vector.elements();
	}
	/**
	 * ʵ�� ͨ��·����ȡ��servletContext���� ���� /aaa��Ŀ���Ի�ȡ�� /bbb��������
	 * ����:2015-5-19
	 * ����:LM
	 * @param uripath
	 * @return
	 */
	public ServletContext getContext(String uripath) {
		return contextList.get(uripath);
	}
	/**
	 * ʵ�ֻ�ȡ��ǰ�����ĵ�·��
	 * ����:2015-5-19
	 * ����:LM
	 * @return
	 */
	public String getContextPath() {
		return contextPath;
	}
	/**
	 * ʵ�ֻ�ȡ�����ĵĳ�ʼ������
	 * ����:2015-5-19
	 * ����:LM
	 * @param name
	 * @return
	 */
	public String getInitParameter(String name) {
		return webp.getContextParam().get(name);
	}
	/**
	 * ʵ�ֲ���ȡ���е������ĵĳ�ʼ�����������Ƽ���
	 * ����:2015-5-19
	 * ����:LM
	 * @return
	 */
	public Enumeration getInitParameterNames() {
		Vector vector=new Vector();
		vector.addAll(webp.getContextParam().keySet());
		return vector.elements();
	}
	/**
	 * ʵ�ֲ���ȡservlet�İ汾 ���� 2.5 MajorVersion��2  MinorVersion��5
	 * ����:2015-5-19
	 * ����:LM
	 * @return
	 */
	public int getMajorVersion() {
		return 2;
	}
	/**
	 * ʵ�ֲ�ͨ���ļ���ȡ��mimetype
	 * ����:2015-5-19
	 * ����:LM
	 * @param file
	 * @return
	 */
	public String getMimeType(String file) {
		return ContextTypeMap.getFileType(file);
	}
	/**
	 * ʵ�ֲ���ȡservlet�İ汾 ���� 2.5 MajorVersion��2  MinorVersion��5
	 * ����:2015-5-19
	 * ����:LM
	 * @return
	 */
	public int getMinorVersion() {
		return 5;
	}
	/**
	 * ͨ�����ƻ�ȡserlvet
	 * ����:2015-5-27
	 * ����:LM
	 * @param name
	 * @return
	 */
	public RequestDispatcher getNamedDispatcher(String name) {
		Map serlvetMap=webp.getServlets().get(name);
		String url=serlvetMap.get("url-pattern").toString();
		return new CherryRequestDispatcher(contextPath+url);
	}
	/**
	 * ͨ��·����ȡ��·����ʵ��·��
	 * ����:2015-5-19
	 * ����:LM
	 * @param path
	 * @return
	 */
	public String getRealPath(String path) {
		String realPath=webp.getContextDir()+path;
		realPath=realPath.replaceAll("\\\\", "/");
		return webp.getContextDir()+path;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return new CherryRequestDispatcher(path);
	}
	/**
	 * ��ȡĳ����Դ��URL
	 * ����:2015-5-24
	 * ����:LM
	 * @param path
	 * @return
	 * @throws MalformedURLException
	 */
	public URL getResource(String path) throws MalformedURLException {
		String path1=getRealPath(path);
		return new File(path1).toURL();
	}
	/**
	 * ��ȡĳ��·���µ���
	 * ����:2015-5-24
	 * ����:LM
	 * @param path
	 * @return
	 */
	public InputStream getResourceAsStream(String path) {
		try {
			return new FileInputStream(getRealPath(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ȡ��ĳ��Ŀ¼�µ�����path ����Ǹ��ļ�ֱ�ӷ��ص�ǰ�ļ� �ο�ServletContextע��
	 * ����path: /welcome.html realPath:c:/app/welcome.html prePath:c:/app
	 * ����ע��һ�� ·��һ��Ҫ�� /  ��ȡwindow���� Ĭ���� \ ������Щ���� ������ʽƥ����� /�ᱨ��  httpЭ���·������  /
	 * ����:2015-5-24
	 * ����:LM
	 * @param path
	 * @return
	 */
	public Set getResourcePaths(String path) {
		String realPath=getRealPath(path);
		String prePath=realPath.substring(0,realPath.lastIndexOf(path));
		File file=new File(realPath);
		Set set=new HashSet();
		if(file.isDirectory()){
			File[] listFiles=file.listFiles();
			for(int i=0;i<listFiles.length;i++){
				String absPath=listFiles[i].getAbsolutePath();
				absPath=absPath.replaceAll("\\\\", "/");
				set.add(absPath.substring(prePath.length()));
			}
		}else{
			set.add(path);
		}
		return set;
	}
	/**
	 * ��ȡ��������Ϣ
	 * ����:2015-5-24
	 * ����:LM
	 * @return
	 */
	public String getServerInfo() {
		return Http11Protocal.server;
	}
	/**
	 * ͨ�����ƻ�ȡ��servlet
	 * ����:2015-5-24
	 * ����:LM
	 * @param name
	 * @return
	 * @throws ServletException
	 */
	public Servlet getServlet(String name) throws ServletException {
		HttpServlet servlet=null;
		try {
			servlet = j2eeLoader.getServletLoader().getServletByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return servlet;
	}
	/**
	 * ��ȡ����������
	 * ����:2015-5-24
	 * ����:LM
	 * @return
	 */
	public String getServletContextName() {
		return j2eeLoader.getContextPath().split("/")[1];
	}
	/**
	 * ��ȡ�����е�servlet�����Ƶĺϼ�
	 * ����:2015-5-24
	 * ����:LM
	 * @return
	 */
	public Enumeration getServletNames() {
		Vector ve=new Vector();
		ve.addAll(j2eeLoader.getWebParse().getServlets().keySet());
		return ve.elements();
	}
	/**
	 * �÷����Ѿ����� ���ڷ��ؿ�
	 * ����:2015-5-24
	 * ����:LM
	 * @return
	 */
	public Enumeration getServlets() {
		Vector ve=new Vector();
		ve.addAll(j2eeLoader.getServletLoader().getLoadedServlet().values());
		return ve.elements();
	}
	/**
	 * д����־��Ϣ
	 * ����:2015-5-24
	 * ����:LM
	 * @param msg
	 */
	public void log(String msg) {
		j2eeLoader.getHttpServer().getLog().info(msg);
	}
	/**
	 * д����־��Ϣ
	 * ����:2015-5-24
	 * ����:LM
	 * @param exception
	 * @param msg
	 */
	public void log(Exception exception, String msg) {
		j2eeLoader.getHttpServer().getLog().info(msg);
		j2eeLoader.getHttpServer().getLog().info(SystemUtils.getExceptionMsg(exception));

	}
	/**
	 * д����־��Ϣ
	 * ����:2015-5-24
	 * ����:LM
	 * @param exception
	 * @param msg
	 */
	public void log(String message, Throwable throwable) {
		j2eeLoader.getHttpServer().getLog().info(message);
		j2eeLoader.getHttpServer().getLog().info(SystemUtils.getExceptionMsg(throwable));
	}
	/**
	 * ɾ������
	 * ����:2015-5-24
	 * ����:LM
	 * @param exception
	 * @param msg
	 */
	public void removeAttribute(String name) {
		Object value=attributeMap.get(name);
		attributeMap.remove(name);
		try {
			listenerManager.attachEvent(ServletContextAttributeListener.class,"attributeRemoved", new Object[]{new ServletContextAttributeEvent(this,name,value)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * д��������Ϣ
	 * ����:2015-5-24
	 * ����:LM
	 * @param exception
	 * @param msg
	 */
	public void setAttribute(String name, Object value) {
		String method="attributeAdded";
		if(attributeMap.containsKey(name)){
			method="attributeReplaced";
		}
		attributeMap.put(name,value);
		try {
			listenerManager.attachEvent(ServletContextAttributeListener.class,method, new Object[]{new ServletContextAttributeEvent(this,name,value)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
	}

}
