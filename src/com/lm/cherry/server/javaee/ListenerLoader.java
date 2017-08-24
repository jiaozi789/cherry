package com.lm.cherry.server.javaee;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import com.lm.cherry.tool.ReverseUtils;
/**
 * ����listeners
 * ����:2015-5-27
 * ����:LM
 */
public class ListenerLoader {
	/**
	 * webxml�Ľ�����
	 */
	private WebParse parse=null;
	/**
	 * �Ѿ����ص�listeners����
	 * keyֵΪlistener������ valueΪlisteners�Ķ��󼯺�
	 */
	private Map<Class,List<Object>> loadedListeners=new HashMap<Class,List<Object>>();
	/**
	 * classloader ������ļ�����
	 */
	ClassLoader cherryLoder=null;
	private J2EELoader jl=null;
	/**
	 * ���췽�� ����һ��webxml�������� ���ҹ���classloader
	 * @param parse
	 * @throws MalformedURLException
	 */
	public ListenerLoader(J2EELoader jl) throws Exception{
		this.jl=jl;
		this.parse=jl.getWebParse();
		this.cherryLoder=jl.getCherryLoder();
		this.load();
	}
	/**
	 * Ĭ�ϳ�ʼ�����Ҽ���ʵ�����Ѿ�����servlet
	 * ����:2015-5-19
	 * ����:LM
	 * @throws Exception
	 */
	public void load() throws Exception{
		List<String> listeners=parse.getListeners();
		for(String listenerClass:listeners){
			Class servletLoadClass=cherryLoder.loadClass(listenerClass);
			Object obj=servletLoadClass.newInstance();
			Class lisCls=null;
			if(ReverseUtils.ifInstanceof(obj.getClass(),"javax.servlet.http.HttpSessionListener")){
				lisCls=cherryLoder.loadClass("javax.servlet.http.HttpSessionListener");
			}
			//����¼���֪����δ���
			if(ReverseUtils.ifInstanceof(obj.getClass(),"javax.servlet.http.HttpSessionActivationListener")){
				lisCls=cherryLoder.loadClass("javax.servlet.http.HttpSessionActivationListener");
			}
			if(ReverseUtils.ifInstanceof(obj.getClass(),"javax.servlet.http.HttpSessionAttributeListener")){
				lisCls=cherryLoder.loadClass("javax.servlet.http.HttpSessionAttributeListener");
			}
			if(ReverseUtils.ifInstanceof(obj.getClass(),"javax.servlet.http.HttpSessionBindingListener")){
				lisCls=cherryLoder.loadClass("javax.servlet.http.HttpSessionBindingListener");
			}
			if(ReverseUtils.ifInstanceof(obj.getClass(),"javax.servlet.ServletContextAttributeListener")){
				lisCls=cherryLoder.loadClass("javax.servlet.ServletContextAttributeListener");
			}
			if(ReverseUtils.ifInstanceof(obj.getClass(),"javax.servlet.ServletContextListener")){
				//ServletContextListener�ĳ�ʼ���¼� ����ȫ��Ҫ���������
				lisCls=cherryLoder.loadClass("javax.servlet.ServletContextListener");
			}
			if(ReverseUtils.ifInstanceof(obj.getClass(),"javax.servlet.ServletRequestAttributeListener")){
				lisCls=cherryLoder.loadClass("javax.servlet.ServletRequestAttributeListener");
			}
			if(ReverseUtils.ifInstanceof(obj.getClass(),"javax.servlet.ServletRequestListener")){
				lisCls=cherryLoder.loadClass("javax.servlet.ServletRequestListener");
			}
			if(ReverseUtils.ifInstanceof(obj.getClass(),"javax.servlet.ELContextListener")){
				lisCls=cherryLoder.loadClass("javax.servlet.ELContextListener");
			}
			Object list=loadedListeners.get(lisCls);
			List rlist=list==null?new ArrayList():(ArrayList)list;
			rlist.add(obj);
			loadedListeners.put(lisCls, rlist);
		}
		
		
	}
	public Map<Class, List<Object>> getLoadedListeners() {
		return loadedListeners;
	}
	public void setLoadedListeners(Map<Class, List<Object>> loadedListeners) {
		this.loadedListeners = loadedListeners;
	}
}
