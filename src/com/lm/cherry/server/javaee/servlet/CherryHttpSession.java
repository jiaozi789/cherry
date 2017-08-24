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
 * session的实现
 * 日期:2015-5-23
 * 作者:LM
 */
public class CherryHttpSession implements HttpSession {
	/**
	 * session存储的键值对
	 */
	private Map atrs=new HashMap();
	/**
	 * session的创建日期
	 */
	private Date createDate=null;
	/**
	 * session的id
	 */
	private String sessionId=null;
	/**
	 * 最后进入的日期
	 */
	private Date lastAccessDate=null;
	/**
	 * 上下文对象
	 */
	private ServletContext servletContext=null;
	
	private ListenersManager listenerManager=null;
	
	private HttpSessionContext sessionContext=null;
	/**
	 * 默认session超时时间为30分钟
	 */
	private int maxInactiveInterval=1800;
	/**
	 * 当前的session是否失效
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
		//设置session的有效期为配置文件中配置的时间 未配置默认为30分钟
		int sessionTimeOut=jl.getWebParse().getSessionTimeOut();
		maxInactiveInterval=sessionTimeOut*60;
		try {
			listenerManager.attachEvent(HttpSessionListener.class, "sessionCreated", new Object[]{new HttpSessionEvent(this)});
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取session中的值
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name) {
		return atrs.get(name);
	}
	/**
	 * 获取session中的所有属性名称
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public Enumeration getAttributeNames() {
		lastAccessDate=new Date();
		Vector v=new Vector();
		v.addAll(atrs.keySet());
		return v.elements();
	}
	/**
	 * 获取session的创建时间
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public long getCreationTime() {
		return createDate.getTime();
	}
	/**
	 * 获取session创建的id
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public String getId() {
		return sessionId;
	}
	/**
	 * 获取客户端最后一次访问的session的时间
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public long getLastAccessedTime() {
		return lastAccessDate.getTime();
	}
	/**
	 * 获取客户端最后一次访问session时间到当前时间的之间的值的最大值是多少
	 * 超过了这个值 直接session超时 默认单位是（秒）默认是永不超时
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}
	/**
	 * 获取到上下文的对象
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public ServletContext getServletContext() {
		return this.servletContext;
	}
	
	public HttpSessionContext getSessionContext() {
		return sessionContext;
	}
	/**
	 * 获取某个名称对应的session的值
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public Object getValue(String name) {
		return getAttribute(name);
	}
	/**
	 * 获取值的合集
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public String[] getValueNames() {
		String[] str=new String[atrs.size()];
		atrs.values().toArray(str);
		return str;
	}
	/**
	 * 注销掉当前的session
	 * 日期:2015-5-23
	 * 作者:LM
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
	 * 这里刚刚创建的就一定是new的 如果有客户端连接 要自动将这个值改成false
	 * 判断当前的session是否是新的session
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public boolean isNew() {
		return ifNew;
	}

	public void putValue(String name, Object value) {
		atrs.put(name, value);

	}
	/**
	 * 删除属性
	 * 日期:2015-5-23
	 * 作者:LM
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
	 * 删除属性
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param name
	 */
	public void removeValue(String name) {
		atrs.remove(name);
	}
	/**
	 * 设置属性
	 * 日期:2015-5-23
	 * 作者:LM
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
	 * 最值session的超时时间 单位为妙
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param interval
	 */
	public void setMaxInactiveInterval(int interval) {
		this.maxInactiveInterval=interval;
	}
	/**
	 * 判断当前的session是否有效
	 * 日期:2015-5-27
	 * 作者:LM
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
