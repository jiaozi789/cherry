package com.lm.cherry.server.javaee;

import java.util.List;

import javax.servlet.ServletException;

import com.lm.cherry.tool.ReverseUtils;
import com.lm.cherry.tool.SystemUtils;

/**
 * 时间触发管理器
 * 日期:2015-5-27
 * 作者:LM
 */
public class ListenersManager {
	private ListenerLoader ll;
	public ListenersManager(ListenerLoader ll) {
		this.ll=ll;
	}
	/**
	 * 执行事件
	 * 日期:2015-5-27
	 * 作者:LM
	 * @param type
	 * @param method
	 * @param param
	 * @throws ServletException
	 */
	public void attachEvent(Class type,String method,Object[] param) throws ServletException{
		try {
			List<Object> ls=ll.getLoadedListeners().get(type);
			if(ls!=null && ls.size()>0){
				Class[] clses=new Class[param.length];
				int i=0;
				for(Object pa:param){
					clses[i]=pa.getClass();
					i++;
				}
				for(int s=0;s<ls.size();s++){
					Object object=ls.get(s);
					ReverseUtils.invoke(object, method, param, clses);
				}
			}
		} catch (Exception e) {
			throw new ServletException(SystemUtils.getExceptionMsg(e));
		} 
	}
}
