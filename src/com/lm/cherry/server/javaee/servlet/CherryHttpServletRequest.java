package com.lm.cherry.server.javaee.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.server.DeployProcessFactory;
import com.lm.cherry.server.javaee.J2EELoader;
import com.lm.cherry.server.javaee.ListenersManager;
import com.lm.cherry.tool.SystemUtils;
/**
 * 自己实现HttpServletRequest
 * 日期:2015-5-19
 * 作者:LM
 */
public class CherryHttpServletRequest implements HttpServletRequest {
	private Http11Protocal hp;
	private J2EELoader jl;
	private CherryHttpSession session=null;
	/**
	 * session存储的键值对
	 */
	private Map atrs=new HashMap();
	private String characterEncoding=null;
	private ListenersManager listenerManager=null;
	public CherryHttpServletRequest(Http11Protocal hp,J2EELoader jl){
		this.hp=hp;
		this.jl=jl;
		listenerManager=new ListenersManager(jl.getListenerLoader());
		try {
			session=(CherryHttpSession)getSession(true);
			session.setLastAccessDate(new Date());
			listenerManager.attachEvent(ServletRequestListener.class, "requestInitialized", new Object[]{new ServletRequestEvent(jl.getServletContext(),this)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 注销容器
	 * 日期:2015-5-27
	 * 作者:LM
	 */
	public void requestDestroy(){
		try {
			listenerManager.attachEvent(ServletRequestListener.class, "requestDestroyed", new Object[]{new ServletRequestEvent(jl.getServletContext(),this)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	public String getAuthType() {
		return null;
	}
	/**
	 * 获取上下文路径
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public String getContextPath() {
		return jl.getContextPath();
	}
	/**
	 * 获取所有的cookie
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public Cookie[] getCookies() {
		String cookie=hp.getRequestHead().get("Cookie");
		String[] fenhao=cookie.split(";");
		List<Cookie> list=new ArrayList<Cookie>();
		for(String child:fenhao){
			String[] cookieMap=child.split("=");
			Cookie ckie=new Cookie(cookieMap[0],cookieMap[1]);
			list.add(ckie);
		}
		Cookie[] cks=new Cookie[list.size()];
		list.toArray(cks);
		return cks;
	}
	/**
	 * 暂未实现
	 * 日期:2015-5-20
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public long getDateHeader(String name) {
		return 0;
	}
	/**
	 * 获取某个请求头信息
	 * 日期:2015-5-20
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public String getHeader(String name) {
		return hp.getRequestHead().get(name);
	}
	/**
	 * 获取所有请求头的名称
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public Enumeration getHeaderNames() {
		Vector ve=new Vector();
		ve.addAll(hp.getRequestHead().keySet());
		return ve.elements();
	}
	/**
	 * 通过名称获取请求集合
	 * 日期:2015-5-20
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public Enumeration getHeaders(String name) {
		Vector ve=new Vector();
		ve.add(getHeader(name));
		return ve.elements();
	}
	/**
	 * 无需实现 可能用于一些初始化的头 自定义的头
	 * 日期:2015-5-20
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public int getIntHeader(String name) {
		return -1;
	}
	/**
	 * 获取请求的方法 get或者post
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public String getMethod() {
		return hp.getRequestMethod();
	}
	/**
	 * 暂时不需要实现
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public String getPathInfo() {
		return null;
	}
	/**
	 * 暂时不需要实现
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public String getPathTranslated() {
		return null;
	}
	/**
	 * 暂时不需要实现
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public String getQueryString() {
		return null;
	}
	/**
	 * 暂时不需要实现
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public String getRemoteUser() {
		return null;
	}
	/**
	 * 获取请求的url
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public String getRequestURI() {
		return hp.getRequestUrlNoParam();
	}
	/**
	 * 获取请求的url
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public StringBuffer getRequestURL() {
		return new StringBuffer(getRequestURI());
	}
	/**
	 * cookie中获取到sessionid
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public String getRequestedSessionId() {
		Entry.J2EEApp ej=(Entry.J2EEApp)DeployProcessFactory.searchAppByContextPath(this.getContextPath());
		String cookieValue=hp.getSomeValueFromAnyWhere(ej.getCookieName());
		return cookieValue;
	}
	/**
	 * 获取servletpath
	 * 日期:2015-5-20
	 * 作者:LM
	 * @return
	 */
	public String getServletPath() {
		String servletPath=hp.getRequestUrlNoParam().substring(getContextPath().length());
		return servletPath;
	}
	/**
	 * 获取到session 如果存在返回当前session create=true不存在就创建session
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param create
	 * @return
	 */
	public HttpSession getSession(boolean create) {
		//获取到当前sessionid对应的值
		String sessionId=getRequestedSessionId();
		//如果create=true 表示不存在就创建session 如果存在 第二次获取就不是新的
		if(create){
			if(session!=null && session.isIfValidate()){
				if(sessionId!=null && sessionId==session.getId()){
					session.setIfNew(false);
					session.setLastAccessDate(new Date());
				}
				return session;
			}
			if(SystemUtils.isEmpty(sessionId)){
				CherryHttpSession newSession=new CherryHttpSession(jl);
				newSession.setIfNew(true);
				jl.getSessionContext().putSession(newSession);
				session=newSession;
			}else{
				session=(CherryHttpSession)jl.getSessionContext().getSession(sessionId);
				if(session!=null && !session.isIfValidate()){
					session=null;
					jl.getSessionContext().removeSession(sessionId);
				}				
				if(session==null){
					CherryHttpSession newSession=new CherryHttpSession(jl);
					newSession.setIfNew(true);
					jl.getSessionContext().putSession(newSession);
					session=newSession;
				}else{
					session.setIfNew(false);
					session.setLastAccessDate(new Date());
				}
				
			}
		}
		return session;
	}
	/**
	 * 获取到当前的session 没有就创建一个
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public HttpSession getSession() {
		return getSession(true);
	}
	/**
	 * 暂时不需要实现该方法
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public Principal getUserPrincipal() {
		return null;
	}
	/**
	 * sessionId是否来自cookie
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public boolean isRequestedSessionIdFromCookie() {
		Entry.J2EEApp ej=(Entry.J2EEApp)DeployProcessFactory.searchApp(hp.getRequestUrl());
		if(hp.getCookieValue(ej.getCookieName())!=null){
			return true;
		}
		return false;
	}
	/**
	 * sessionId是否来自url中
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public boolean isRequestedSessionIdFromURL() {
		return !isRequestedSessionIdFromCookie();
	}
	/**
	 * sessionId是否来自url中
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public boolean isRequestedSessionIdFromUrl() {
		return isRequestedSessionIdFromURL();
	}
	/**
	 * 是否session有效
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public boolean isRequestedSessionIdValid() {
		String sessionId=getRequestedSessionId();
		CherryHttpSession session=(CherryHttpSession)jl.getSessionContext().getSession(sessionId);
		return session.isIfValidate();
	}
	/**
	 * 暂无需实现
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param role
	 * @return
	 */
	public boolean isUserInRole(String role) {
		return false;
	}
	/**
	 * 通过名称获取属性值
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name) {
		return atrs.get(name);
	}
	/**
	 * 获取所有的属性名称
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public Enumeration getAttributeNames() {
		Vector ve=new Vector();
		ve.addAll(atrs.keySet());
		return ve.elements();
	}
	/**
	 * 获取请求的客户端可以接受的字符集
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public String getCharacterEncoding() {
		return hp.getRequestHead().get("Accept-Charset");
	}
	/**
	 * 获取POST请求的资源的长度
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public int getContentLength() {
		//只有post的请求才有Content-Length 才有内容
		Object contentLength=hp.getRequestHead().get("Content-Length");
		return contentLength!=null?Integer.parseInt(contentLength.toString()):0;
	}
	/**
	 * 获取客户端post的mime类型
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public String getContentType() {
		//只有post的请求才有Content-Type 才有请求内容的mime类型
		Object contentType=hp.getRequestHead().get("Content-Type");
		return contentType!=null?contentType.toString():null;
	}
	/**
	 * 获取servlet输出流
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 * @throws IOException
	 */
	public ServletInputStream getInputStream() throws IOException {
		String POSTDATA=hp.getRequestHead().get("POSTDATA");
		String charset=hp.DEFAULT_ENCODING;
		return new CherryServletInputStream(POSTDATA,charset);
	}
	/**
	 * 获取客户端的ip地址
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public String getLocalAddr() {
		return hp.getCurProcessThread().getSocket().getLocalAddress().getHostAddress();
	}
	/**
	 * 获取客户端的机器名
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public String getLocalName() {
		return hp.getCurProcessThread().getSocket().getLocalAddress().getHostName();
	}
	/**
	 * 获取到本地的端口
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public int getLocalPort() {
		return hp.getCurProcessThread().getSocket().getLocalPort();
	}
	/**
	 * 获取客户端的语言 
	 * 类似 Accept-Language:zh-CN,zh;q=0.8
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public Locale getLocale() {
		String lanague=hp.getRequestHead().get("Accept-Language");
		if(SystemUtils.isEmpty(lanague)){
			return Locale.CHINA;
		}else{
			String[] splt=lanague.split(",");
			String la=splt[0];
			return new Locale(la);
		}
	}

	public Enumeration getLocales() {
		Vector v=new Vector();
		String lanague=hp.getRequestHead().get("Accept-Language");
		if(SystemUtils.isEmpty(lanague)){
			v.add(Locale.CHINA);
		}else{
			String[] splt=lanague.split(",");
			String la=splt[0];
			v.add(new Locale(la));
		}
		return v.elements();
	}
	/**
	 * 获取参数
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return hp.getParameter(name);
	}
	/**
	 * 获取参数集合
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public Map getParameterMap() {
		return hp.getParamMap();
	}
	/**
	 * 获取参数名称的集合
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public Enumeration getParameterNames() {
		Vector v=new Vector();
		v.addAll(hp.getParamMap().keySet());
		return v.elements();
	}
	/**
	 * 获取值的合集
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public String[] getParameterValues(String name) {
		Collection coll=hp.getParamMap().values();
		String[] str=new String[coll.size()];
		coll.toArray(str);
		return str;
	}
	/**
	 * 获取协议
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public String getProtocol() {
		return "HTTP/1.1";
	}
	/**
	 * 获取读取内容的bufferedreader对象
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 * @throws IOException
	 */
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
	
	public String getRealPath(String path) {
		return getSession().getServletContext().getRealPath(path);
	}
	/**
	 * 获取客户端的ip地址
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public String getRemoteAddr() {
		return hp.getCurProcessThread().getSocket().getInetAddress().getHostAddress();
	}
	/**
	 * 获取客户端的主机名
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public String getRemoteHost() {
		return hp.getCurProcessThread().getSocket().getInetAddress().getHostName();
	}
	/**
	 * 获取远程的port
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public int getRemotePort() {
		return hp.getCurProcessThread().getSocket().getPort();
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return new CherryRequestDispatcher(path);
	}
	/**
	 * 获取协议
	 * 日期:2015-5-24
	 * 作者:LM
	 * @return
	 */
	public String getScheme() {
		return "http";
	}
	/**
	 * 获取服务器名称
	 * 日期:2015-5-24
	 * 作者:LM
	 * @return
	 */
	public String getServerName() {
		return getLocalName();
	}
	/**
	 * 获取监控的服务的端口
	 * 日期:2015-5-24
	 * 作者:LM
	 * @return
	 */
	public int getServerPort() {
		return hp.getCurProcessThread().getThreadPool().getHttpServer().getPort();
	}

	public boolean isSecure() {
		return false;
	}
	/**
	 * 删除某个属性
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param name
	 */
	public void removeAttribute(String name) {
		Object value=atrs.get(name);
		atrs.remove(name);
		try {
			listenerManager.attachEvent(ServletRequestAttributeListener.class,"attributeRemoved", new Object[]{new ServletRequestAttributeEvent(jl.getServletContext(),this,name,value)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置某个属性
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param name
	 * @param o
	 */
	public void setAttribute(String name, Object value) {
		atrs.put(name, value);
		String method="attributeAdded";
		if(atrs.containsKey(name)){
			method="attributeReplaced";
		}
		try {
			listenerManager.attachEvent(ServletRequestAttributeListener.class,method, new Object[]{new ServletRequestAttributeEvent(jl.getServletContext(),this,name,value)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置编码
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param env
	 * @throws UnsupportedEncodingException
	 */
	public void setCharacterEncoding(String env)
			throws UnsupportedEncodingException {
		this.characterEncoding=env;
	}
	public Http11Protocal getHp() {
		return hp;
	}
	public void setHp(Http11Protocal hp) {
		this.hp = hp;
	}
	public J2EELoader getJl() {
		return jl;
	}
	public void setJl(J2EELoader jl) {
		this.jl = jl;
	}

}
