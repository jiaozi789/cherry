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
 * 实现全局的servletcontext对象 项目之间相互访问
 * 日期:2015-5-19
 * 作者:LM
 */
public class CherryServletContext implements ServletContext {
	Log log=null;
	public static Map<String,ServletContext> contextList=new HashMap();
	/**
	 * servletcontext所有设置的键值对
	 */
	private Map attributeMap=new HashMap();
	/**
	 * 上下文的路径
	 */
	private String contextPath=null;
	/**
	 * webxml解析类
	 */
	private WebParse webp=null;
	/**
	 * j2ee加载类
	 */
	private J2EELoader j2eeLoader=null;
	private ListenersManager listenerManager=null;
	/**
	 * 构造方法 传入webxml配置信息
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
	 * 注销容器
	 * 日期:2015-5-27
	 * 作者:LM
	 */
	public void contextDestroy(){
		try {
			listenerManager.attachEvent(ServletContextListener.class, "contextDestroyed", new Object[]{new ServletContextEvent(this)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 实现获取某个属性值的方法
	 * 日期:2015-5-19
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name) {
		return attributeMap.get(name);
	}
	/**
	 * 实现获取所有的属性名称的集合
	 * 日期:2015-5-19
	 * 作者:LM
	 * @return
	 */
	public Enumeration getAttributeNames() {
		Vector vector=new Vector();
		vector.addAll(attributeMap.keySet());
		return vector.elements();
	}
	/**
	 * 实现 通过路径获取到servletContext对象 比如 /aaa项目可以获取到 /bbb的上下文
	 * 日期:2015-5-19
	 * 作者:LM
	 * @param uripath
	 * @return
	 */
	public ServletContext getContext(String uripath) {
		return contextList.get(uripath);
	}
	/**
	 * 实现获取当前上下文的路径
	 * 日期:2015-5-19
	 * 作者:LM
	 * @return
	 */
	public String getContextPath() {
		return contextPath;
	}
	/**
	 * 实现获取上下文的初始化参数
	 * 日期:2015-5-19
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public String getInitParameter(String name) {
		return webp.getContextParam().get(name);
	}
	/**
	 * 实现并获取所有的上下文的初始化参数的名称集合
	 * 日期:2015-5-19
	 * 作者:LM
	 * @return
	 */
	public Enumeration getInitParameterNames() {
		Vector vector=new Vector();
		vector.addAll(webp.getContextParam().keySet());
		return vector.elements();
	}
	/**
	 * 实现并获取servlet的版本 比如 2.5 MajorVersion是2  MinorVersion是5
	 * 日期:2015-5-19
	 * 作者:LM
	 * @return
	 */
	public int getMajorVersion() {
		return 2;
	}
	/**
	 * 实现并通过文件获取到mimetype
	 * 日期:2015-5-19
	 * 作者:LM
	 * @param file
	 * @return
	 */
	public String getMimeType(String file) {
		return ContextTypeMap.getFileType(file);
	}
	/**
	 * 实现并获取servlet的版本 比如 2.5 MajorVersion是2  MinorVersion是5
	 * 日期:2015-5-19
	 * 作者:LM
	 * @return
	 */
	public int getMinorVersion() {
		return 5;
	}
	/**
	 * 通过名称获取serlvet
	 * 日期:2015-5-27
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public RequestDispatcher getNamedDispatcher(String name) {
		Map serlvetMap=webp.getServlets().get(name);
		String url=serlvetMap.get("url-pattern").toString();
		return new CherryRequestDispatcher(contextPath+url);
	}
	/**
	 * 通过路径获取到路径的实际路径
	 * 日期:2015-5-19
	 * 作者:LM
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
	 * 获取某个资源的URL
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param path
	 * @return
	 * @throws MalformedURLException
	 */
	public URL getResource(String path) throws MalformedURLException {
		String path1=getRealPath(path);
		return new File(path1).toURL();
	}
	/**
	 * 获取某个路径下的流
	 * 日期:2015-5-24
	 * 作者:LM
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
	 * 取得某个目录下的所有path 如果是个文件直接返回当前文件 参考ServletContext注释
	 * 比如path: /welcome.html realPath:c:/app/welcome.html prePath:c:/app
	 * 这里注意一点 路径一定要用 /  读取window本地 默认是 \ 所以有些程序 正则表达式匹配的是 /会报错  http协议的路径都是  /
	 * 日期:2015-5-24
	 * 作者:LM
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
	 * 获取服务器信息
	 * 日期:2015-5-24
	 * 作者:LM
	 * @return
	 */
	public String getServerInfo() {
		return Http11Protocal.server;
	}
	/**
	 * 通过名称获取到servlet
	 * 日期:2015-5-24
	 * 作者:LM
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
	 * 获取发布的名称
	 * 日期:2015-5-24
	 * 作者:LM
	 * @return
	 */
	public String getServletContextName() {
		return j2eeLoader.getContextPath().split("/")[1];
	}
	/**
	 * 获取到所有的servlet的名称的合集
	 * 日期:2015-5-24
	 * 作者:LM
	 * @return
	 */
	public Enumeration getServletNames() {
		Vector ve=new Vector();
		ve.addAll(j2eeLoader.getWebParse().getServlets().keySet());
		return ve.elements();
	}
	/**
	 * 该方法已经过期 用于返回空
	 * 日期:2015-5-24
	 * 作者:LM
	 * @return
	 */
	public Enumeration getServlets() {
		Vector ve=new Vector();
		ve.addAll(j2eeLoader.getServletLoader().getLoadedServlet().values());
		return ve.elements();
	}
	/**
	 * 写入日志信息
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param msg
	 */
	public void log(String msg) {
		j2eeLoader.getHttpServer().getLog().info(msg);
	}
	/**
	 * 写入日志信息
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param exception
	 * @param msg
	 */
	public void log(Exception exception, String msg) {
		j2eeLoader.getHttpServer().getLog().info(msg);
		j2eeLoader.getHttpServer().getLog().info(SystemUtils.getExceptionMsg(exception));

	}
	/**
	 * 写入日志信息
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param exception
	 * @param msg
	 */
	public void log(String message, Throwable throwable) {
		j2eeLoader.getHttpServer().getLog().info(message);
		j2eeLoader.getHttpServer().getLog().info(SystemUtils.getExceptionMsg(throwable));
	}
	/**
	 * 删除属性
	 * 日期:2015-5-24
	 * 作者:LM
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
	 * 写入属性信息
	 * 日期:2015-5-24
	 * 作者:LM
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
