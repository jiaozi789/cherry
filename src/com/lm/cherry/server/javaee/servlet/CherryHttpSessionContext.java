package com.lm.cherry.server.javaee.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
/**
 * 获取session的上下文合集
 * 日期:2015-5-23
 * 作者:LM
 */
public class CherryHttpSessionContext implements HttpSessionContext {
	private Map<String,HttpSession> hst=new HashMap<String,HttpSession>();
	/**
	 * 获取到所有的sessionId合集
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	public Enumeration getIds() {
		Vector ve=new Vector();
		ve.addAll(hst.keySet());
		return ve.elements();
	}
	/**
	 * 通过sessionId获取到session
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param sessionId
	 * @return
	 */
	public HttpSession getSession(String sessionId) {
		// TODO Auto-generated method stub
		return hst.get(sessionId);
	}
	/**
	 * 设置session到上下文中
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param session
	 */
	public void putSession(HttpSession session){
		hst.put(session.getId(), session);
	}
	/**
	 * 设置session到上下文中
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param session
	 */
	public void removeSession(String sessionId){
		hst.remove(sessionId);
	}

}
