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
 * �Լ�ʵ��HttpServletRequest
 * ����:2015-5-19
 * ����:LM
 */
public class CherryHttpServletRequest implements HttpServletRequest {
	private Http11Protocal hp;
	private J2EELoader jl;
	private CherryHttpSession session=null;
	/**
	 * session�洢�ļ�ֵ��
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
	 * ע������
	 * ����:2015-5-27
	 * ����:LM
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
	 * ��ȡ������·��
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public String getContextPath() {
		return jl.getContextPath();
	}
	/**
	 * ��ȡ���е�cookie
	 * ����:2015-5-20
	 * ����:LM
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
	 * ��δʵ��
	 * ����:2015-5-20
	 * ����:LM
	 * @param name
	 * @return
	 */
	public long getDateHeader(String name) {
		return 0;
	}
	/**
	 * ��ȡĳ������ͷ��Ϣ
	 * ����:2015-5-20
	 * ����:LM
	 * @param name
	 * @return
	 */
	public String getHeader(String name) {
		return hp.getRequestHead().get(name);
	}
	/**
	 * ��ȡ��������ͷ������
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public Enumeration getHeaderNames() {
		Vector ve=new Vector();
		ve.addAll(hp.getRequestHead().keySet());
		return ve.elements();
	}
	/**
	 * ͨ�����ƻ�ȡ���󼯺�
	 * ����:2015-5-20
	 * ����:LM
	 * @param name
	 * @return
	 */
	public Enumeration getHeaders(String name) {
		Vector ve=new Vector();
		ve.add(getHeader(name));
		return ve.elements();
	}
	/**
	 * ����ʵ�� ��������һЩ��ʼ����ͷ �Զ����ͷ
	 * ����:2015-5-20
	 * ����:LM
	 * @param name
	 * @return
	 */
	public int getIntHeader(String name) {
		return -1;
	}
	/**
	 * ��ȡ����ķ��� get����post
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public String getMethod() {
		return hp.getRequestMethod();
	}
	/**
	 * ��ʱ����Ҫʵ��
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public String getPathInfo() {
		return null;
	}
	/**
	 * ��ʱ����Ҫʵ��
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public String getPathTranslated() {
		return null;
	}
	/**
	 * ��ʱ����Ҫʵ��
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public String getQueryString() {
		return null;
	}
	/**
	 * ��ʱ����Ҫʵ��
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public String getRemoteUser() {
		return null;
	}
	/**
	 * ��ȡ�����url
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public String getRequestURI() {
		return hp.getRequestUrlNoParam();
	}
	/**
	 * ��ȡ�����url
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public StringBuffer getRequestURL() {
		return new StringBuffer(getRequestURI());
	}
	/**
	 * cookie�л�ȡ��sessionid
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public String getRequestedSessionId() {
		Entry.J2EEApp ej=(Entry.J2EEApp)DeployProcessFactory.searchAppByContextPath(this.getContextPath());
		String cookieValue=hp.getSomeValueFromAnyWhere(ej.getCookieName());
		return cookieValue;
	}
	/**
	 * ��ȡservletpath
	 * ����:2015-5-20
	 * ����:LM
	 * @return
	 */
	public String getServletPath() {
		String servletPath=hp.getRequestUrlNoParam().substring(getContextPath().length());
		return servletPath;
	}
	/**
	 * ��ȡ��session ������ڷ��ص�ǰsession create=true�����ھʹ���session
	 * ����:2015-5-23
	 * ����:LM
	 * @param create
	 * @return
	 */
	public HttpSession getSession(boolean create) {
		//��ȡ����ǰsessionid��Ӧ��ֵ
		String sessionId=getRequestedSessionId();
		//���create=true ��ʾ�����ھʹ���session ������� �ڶ��λ�ȡ�Ͳ����µ�
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
	 * ��ȡ����ǰ��session û�оʹ���һ��
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public HttpSession getSession() {
		return getSession(true);
	}
	/**
	 * ��ʱ����Ҫʵ�ָ÷���
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public Principal getUserPrincipal() {
		return null;
	}
	/**
	 * sessionId�Ƿ�����cookie
	 * ����:2015-5-23
	 * ����:LM
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
	 * sessionId�Ƿ�����url��
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public boolean isRequestedSessionIdFromURL() {
		return !isRequestedSessionIdFromCookie();
	}
	/**
	 * sessionId�Ƿ�����url��
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public boolean isRequestedSessionIdFromUrl() {
		return isRequestedSessionIdFromURL();
	}
	/**
	 * �Ƿ�session��Ч
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public boolean isRequestedSessionIdValid() {
		String sessionId=getRequestedSessionId();
		CherryHttpSession session=(CherryHttpSession)jl.getSessionContext().getSession(sessionId);
		return session.isIfValidate();
	}
	/**
	 * ������ʵ��
	 * ����:2015-5-23
	 * ����:LM
	 * @param role
	 * @return
	 */
	public boolean isUserInRole(String role) {
		return false;
	}
	/**
	 * ͨ�����ƻ�ȡ����ֵ
	 * ����:2015-5-23
	 * ����:LM
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name) {
		return atrs.get(name);
	}
	/**
	 * ��ȡ���е���������
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public Enumeration getAttributeNames() {
		Vector ve=new Vector();
		ve.addAll(atrs.keySet());
		return ve.elements();
	}
	/**
	 * ��ȡ����Ŀͻ��˿��Խ��ܵ��ַ���
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public String getCharacterEncoding() {
		return hp.getRequestHead().get("Accept-Charset");
	}
	/**
	 * ��ȡPOST�������Դ�ĳ���
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public int getContentLength() {
		//ֻ��post���������Content-Length ��������
		Object contentLength=hp.getRequestHead().get("Content-Length");
		return contentLength!=null?Integer.parseInt(contentLength.toString()):0;
	}
	/**
	 * ��ȡ�ͻ���post��mime����
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public String getContentType() {
		//ֻ��post���������Content-Type �����������ݵ�mime����
		Object contentType=hp.getRequestHead().get("Content-Type");
		return contentType!=null?contentType.toString():null;
	}
	/**
	 * ��ȡservlet�����
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 * @throws IOException
	 */
	public ServletInputStream getInputStream() throws IOException {
		String POSTDATA=hp.getRequestHead().get("POSTDATA");
		String charset=hp.DEFAULT_ENCODING;
		return new CherryServletInputStream(POSTDATA,charset);
	}
	/**
	 * ��ȡ�ͻ��˵�ip��ַ
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public String getLocalAddr() {
		return hp.getCurProcessThread().getSocket().getLocalAddress().getHostAddress();
	}
	/**
	 * ��ȡ�ͻ��˵Ļ�����
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public String getLocalName() {
		return hp.getCurProcessThread().getSocket().getLocalAddress().getHostName();
	}
	/**
	 * ��ȡ�����صĶ˿�
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public int getLocalPort() {
		return hp.getCurProcessThread().getSocket().getLocalPort();
	}
	/**
	 * ��ȡ�ͻ��˵����� 
	 * ���� Accept-Language:zh-CN,zh;q=0.8
	 * ����:2015-5-23
	 * ����:LM
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
	 * ��ȡ����
	 * ����:2015-5-23
	 * ����:LM
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return hp.getParameter(name);
	}
	/**
	 * ��ȡ��������
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public Map getParameterMap() {
		return hp.getParamMap();
	}
	/**
	 * ��ȡ�������Ƶļ���
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public Enumeration getParameterNames() {
		Vector v=new Vector();
		v.addAll(hp.getParamMap().keySet());
		return v.elements();
	}
	/**
	 * ��ȡֵ�ĺϼ�
	 * ����:2015-5-23
	 * ����:LM
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
	 * ��ȡЭ��
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public String getProtocol() {
		return "HTTP/1.1";
	}
	/**
	 * ��ȡ��ȡ���ݵ�bufferedreader����
	 * ����:2015-5-23
	 * ����:LM
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
	 * ��ȡ�ͻ��˵�ip��ַ
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public String getRemoteAddr() {
		return hp.getCurProcessThread().getSocket().getInetAddress().getHostAddress();
	}
	/**
	 * ��ȡ�ͻ��˵�������
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public String getRemoteHost() {
		return hp.getCurProcessThread().getSocket().getInetAddress().getHostName();
	}
	/**
	 * ��ȡԶ�̵�port
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public int getRemotePort() {
		return hp.getCurProcessThread().getSocket().getPort();
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return new CherryRequestDispatcher(path);
	}
	/**
	 * ��ȡЭ��
	 * ����:2015-5-24
	 * ����:LM
	 * @return
	 */
	public String getScheme() {
		return "http";
	}
	/**
	 * ��ȡ����������
	 * ����:2015-5-24
	 * ����:LM
	 * @return
	 */
	public String getServerName() {
		return getLocalName();
	}
	/**
	 * ��ȡ��صķ���Ķ˿�
	 * ����:2015-5-24
	 * ����:LM
	 * @return
	 */
	public int getServerPort() {
		return hp.getCurProcessThread().getThreadPool().getHttpServer().getPort();
	}

	public boolean isSecure() {
		return false;
	}
	/**
	 * ɾ��ĳ������
	 * ����:2015-5-24
	 * ����:LM
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
	 * ����ĳ������
	 * ����:2015-5-24
	 * ����:LM
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
	 * ���ñ���
	 * ����:2015-5-24
	 * ����:LM
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
