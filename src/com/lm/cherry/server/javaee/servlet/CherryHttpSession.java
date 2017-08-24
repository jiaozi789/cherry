package com.lm.cherry.server.javaee.servlet;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.lm.cherry.server.javaee.J2EELoader;
import com.lm.cherry.server.javaee.ListenersManager;
/**
 * session��ʵ��
 * ����:2015-5-23
 * ����:LM
 */
public class CherryHttpSession implements HttpSession {
	/**
	 * session�洢�ļ�ֵ��
	 */
	private Map atrs=new HashMap();
	/**
	 * session�Ĵ�������
	 */
	private Date createDate=null;
	/**
	 * session��id
	 */
	private String sessionId=null;
	/**
	 * �����������
	 */
	private Date lastAccessDate=null;
	/**
	 * �����Ķ���
	 */
	private ServletContext servletContext=null;
	
	private ListenersManager listenerManager=null;
	
	private HttpSessionContext sessionContext=null;
	/**
	 * Ĭ��session��ʱʱ��Ϊ30����
	 */
	private int maxInactiveInterval=1800;
	/**
	 * ��ǰ��session�Ƿ�ʧЧ
	 */
	private boolean ifValidate=true;
	private boolean ifNew=true;
	public boolean isIfNew() {
		return ifNew;
	}
	public void setIfNew(boolean ifNew) {
		this.ifNew = ifNew;
	}
	public Date getLastAccessDate() {
		return lastAccessDate;
	}
	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}
	public CherryHttpSession(J2EELoader jl){
		createDate=new Date();
		lastAccessDate=new Date();
		sessionId="CHERRY_"+UUID.randomUUID().toString().replace("-", "").toUpperCase();
		servletContext=jl.getServletContext();
		sessionContext=jl.getSessionContext();
		listenerManager=new ListenersManager(jl.getListenerLoader());
		//����session����Ч��Ϊ�����ļ������õ�ʱ�� δ����Ĭ��Ϊ30����
		int sessionTimeOut=jl.getWebParse().getSessionTimeOut();
		maxInactiveInterval=sessionTimeOut*60;
		try {
			listenerManager.attachEvent(HttpSessionListener.class, "sessionCreated", new Object[]{new HttpSessionEvent(this)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡsession�е�ֵ
	 * ����:2015-5-23
	 * ����:LM
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name) {
		return atrs.get(name);
	}
	/**
	 * ��ȡsession�е�������������
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public Enumeration getAttributeNames() {
		lastAccessDate=new Date();
		Vector v=new Vector();
		v.addAll(atrs.keySet());
		return v.elements();
	}
	/**
	 * ��ȡsession�Ĵ���ʱ��
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public long getCreationTime() {
		return createDate.getTime();
	}
	/**
	 * ��ȡsession������id
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public String getId() {
		return sessionId;
	}
	/**
	 * ��ȡ�ͻ������һ�η��ʵ�session��ʱ��
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public long getLastAccessedTime() {
		return lastAccessDate.getTime();
	}
	/**
	 * ��ȡ�ͻ������һ�η���sessionʱ�䵽��ǰʱ���֮���ֵ�����ֵ�Ƕ���
	 * ���������ֵ ֱ��session��ʱ Ĭ�ϵ�λ�ǣ��룩Ĭ����������ʱ
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}
	/**
	 * ��ȡ�������ĵĶ���
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public ServletContext getServletContext() {
		return this.servletContext;
	}
	
	public HttpSessionContext getSessionContext() {
		return sessionContext;
	}
	/**
	 * ��ȡĳ�����ƶ�Ӧ��session��ֵ
	 * ����:2015-5-23
	 * ����:LM
	 * @param name
	 * @return
	 */
	public Object getValue(String name) {
		return getAttribute(name);
	}
	/**
	 * ��ȡֵ�ĺϼ�
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public String[] getValueNames() {
		String[] str=new String[atrs.size()];
		atrs.values().toArray(str);
		return str;
	}
	/**
	 * ע������ǰ��session
	 * ����:2015-5-23
	 * ����:LM
	 */
	public void invalidate() {
		ifValidate=false;
		atrs.clear();
		try {
			listenerManager.attachEvent(HttpSessionListener.class, "sessionDestroyed", new Object[]{new HttpSessionEvent(this)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����ոմ����ľ�һ����new�� ����пͻ������� Ҫ�Զ������ֵ�ĳ�false
	 * �жϵ�ǰ��session�Ƿ����µ�session
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public boolean isNew() {
		return ifNew;
	}

	public void putValue(String name, Object value) {
		atrs.put(name, value);

	}
	/**
	 * ɾ������
	 * ����:2015-5-23
	 * ����:LM
	 * @param name
	 */
	public void removeAttribute(String name) {
		Object value=atrs.get(name);
		atrs.remove(name);
		if(value instanceof HttpSessionBindingListener){
			try {
				listenerManager.attachEvent(HttpSessionBindingListener.class,"valueUnbound", new Object[]{new HttpSessionBindingEvent(this,name,value)});
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}else{
			try {
				listenerManager.attachEvent(HttpSessionAttributeListener.class,"attributeRemoved", new Object[]{new HttpSessionBindingEvent(this,name,value)});
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * ɾ������
	 * ����:2015-5-23
	 * ����:LM
	 * @param name
	 */
	public void removeValue(String name) {
		atrs.remove(name);
	}
	/**
	 * ��������
	 * ����:2015-5-23
	 * ����:LM
	 * @param name
	 * @param value
	 */
	public void setAttribute(String name, Object value) {
		atrs.put(name, value);
		if(value instanceof HttpSessionBindingListener){
			try {
				listenerManager.attachEvent(HttpSessionBindingListener.class,"valueBound", new Object[]{new HttpSessionBindingEvent(this,name,value)});
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}else{
			String method="attributeAdded";
			if(atrs.containsKey(name)){
				method="attributeReplaced";
			}
			try {
				listenerManager.attachEvent(HttpSessionAttributeListener.class,method, new Object[]{new HttpSessionBindingEvent(this,name,value)});
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * ��ֵsession�ĳ�ʱʱ�� ��λΪ��
	 * ����:2015-5-23
	 * ����:LM
	 * @param interval
	 */
	public void setMaxInactiveInterval(int interval) {
		this.maxInactiveInterval=interval;
	}
	/**
	 * �жϵ�ǰ��session�Ƿ���Ч
	 * ����:2015-5-27
	 * ����:LM
	 * @return
	 */
	public boolean isIfValidate() {
		long how=lastAccessDate.getTime()-new Date().getTime();
		if(how>maxInactiveInterval){
			this.invalidate();
		}
		return ifValidate;
	}
	public void setIfValidate(boolean ifValidate) {
		this.ifValidate = ifValidate;
	}

}
