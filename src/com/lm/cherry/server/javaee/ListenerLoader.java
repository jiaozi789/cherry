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
 * 加载listeners
 * 日期:2015-5-27
 * 作者:LM
 */
public class ListenerLoader {
	/**
	 * webxml的解析类
	 */
	private WebParse parse=null;
	/**
	 * 已经加载的listeners对象
	 * key值为listener的类型 value为listeners的对象集合
	 */
	private Map<Class,List<Object>> loadedListeners=new HashMap<Class,List<Object>>();
	/**
	 * classloader 加载类的加载器
	 */
	ClassLoader cherryLoder=null;
	private J2EELoader jl=null;
	/**
	 * 构造方法 传入一个webxml解析对象 并且构建classloader
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
	 * 默认初始化并且加载实例化已经配置servlet
	 * 日期:2015-5-19
	 * 作者:LM
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
			//这个事件不知道如何处理
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
				//ServletContextListener的初始化事件 必须全部要在这里调用
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
