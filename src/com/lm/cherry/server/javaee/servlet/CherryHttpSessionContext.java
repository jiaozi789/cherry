package com.lm.cherry.server.javaee.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
/**
 * ��ȡsession�������ĺϼ�
 * ����:2015-5-23
 * ����:LM
 */
public class CherryHttpSessionContext implements HttpSessionContext {
	private Map<String,HttpSession> hst=new HashMap<String,HttpSession>();
	/**
	 * ��ȡ�����е�sessionId�ϼ�
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 */
	public Enumeration getIds() {
		Vector ve=new Vector();
		ve.addAll(hst.keySet());
		return ve.elements();
	}
	/**
	 * ͨ��sessionId��ȡ��session
	 * ����:2015-5-23
	 * ����:LM
	 * @param sessionId
	 * @return
	 */
	public HttpSession getSession(String sessionId) {
		// TODO Auto-generated method stub
		return hst.get(sessionId);
	}
	/**
	 * ����session����������
	 * ����:2015-5-23
	 * ����:LM
	 * @param session
	 */
	public void putSession(HttpSession session){
		hst.put(session.getId(), session);
	}
	/**
	 * ����session����������
	 * ����:2015-5-23
	 * ����:LM
	 * @param session
	 */
	public void removeSession(String sessionId){
		hst.remove(sessionId);
	}

}
