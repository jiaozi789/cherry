package com.lm.cherry.server.javaee;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.server.DeployProcessFactory;
import com.lm.cherry.server.javaee.servlet.CherryServletConfig;
import com.lm.cherry.server.javaee.servlet.CherryServletContext;
/**
 * 通过web.xml配置的servlet信息 加载servlet的类对象
 *   
 * 日期:2015-5-19
 * 作者:LM
 */
public class ServletLoader {
	/**
	 * webxml的解析类
	 */
	private WebParse parse=null;
	/**
	 * 已经加载的servlet对象
	 * key值为访问的链接地址 value为servlet的对象
	 * 
	 * 这里多个url可以映射到同一个servlet所以url可能不能作为唯一的标志符
	 * 比如
	 *    但是这样会引发一个冲突 访问同一个路径时到底进入哪个servlet了 
	 *    最后的配置覆盖前面的配置
	 */
	private Map<String,Object> loadedServlet=new HashMap();
	/**
	 * classloader 加载类的加载器
	 */
	ClassLoader cherryLoder=null;
	/**
	 * 构造方法 传入一个webxml解析对象 并且构建classloader
	 * @param parse
	 * @throws MalformedURLException
	 */
	public ServletLoader(WebParse parse,ClassLoader cherryLoder) throws Exception{
		this.parse=parse;
		this.cherryLoder=cherryLoder;
	}
	/**
	 * 默认初始化并且加载实例化已经配置servlet
	 * 日期:2015-5-19
	 * 作者:LM
	 * @throws Exception
	 */
	public void load() throws Exception{
		Map<String,Map> servlets=parse.getServlets();
		for(Map.Entry<String,Map> me:servlets.entrySet()){
			String servletName=me.getKey();
			Map servletOther=me.getValue();
			int loadOnStart=Integer.parseInt(servletOther.containsKey("load-on-startup")?servletOther.get("load-on-startup").toString():"0");
			String servletClass=servletOther.get("servlet-class").toString();
			//程序可能没有url-pattern 只能为了在程序启动的时候初始化servlet调用init 不用于映射路径
			//那么这个servlet的url可以使用一个随机的url
			String url=null;
			if(servletOther.containsKey("url-pattern")){
				url=servletOther.get("url-pattern").toString();
			}else{
				url=UUID.randomUUID().toString();
				servletOther.put("url-pattern", url);
			}
			if(loadOnStart>0){
				Class servletLoadClass=cherryLoder.loadClass(servletClass);
				HttpServlet hs=(HttpServlet)servletLoadClass.newInstance();
				loadedServlet.put(url, hs);
				ServletContext sc=CherryServletContext.contextList.get(parse.getContextPath());
				CherryServletConfig servletConfig=new CherryServletConfig(hs,servletOther,sc);
				hs.init(servletConfig);
			}else{
				loadedServlet.put(url, servletOther);
			}
		}
		
	}
	/**
	 * 通过url获取servlet对象 如果 已经加载返回servlet对象
	 * 未加载 需要先创建对象 在返回
	 * 日期:2015-5-19
	 * 作者:LM
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpServlet getServlet(String url) throws Exception{
		Entry.J2EEApp enj2ee=(Entry.J2EEApp)DeployProcessFactory.searchApp(url);
		String contextPath=enj2ee.getPath();
		if(url.startsWith(contextPath)){
			url=url.split(contextPath)[1];
		}
		Object obj=loadedServlet.get(url);
		if(obj!=null){
			if(obj instanceof HttpServlet)
				return (HttpServlet)loadedServlet.get(url);
			else{
				Map servletOther=(Map)loadedServlet.get(url);
				String servletClass=servletOther.get("servlet-class").toString();
				Class servletLoadClass=cherryLoder.loadClass(servletClass);
				HttpServlet hs=(HttpServlet)servletLoadClass.newInstance();
				ServletContext sc=CherryServletContext.contextList.get(parse.getContextPath());
				CherryServletConfig servletConfig=new CherryServletConfig(hs,servletOther,sc);
				hs.init(servletConfig);
				loadedServlet.put(url, hs);
				return hs;
			}
		}
		return null;
	}
	/**
	 * 通过name获取servlet对象 如果 已经加载返回servlet对象
	 * 未加载 需要先创建对象 在返回
	 * 日期:2015-5-19
	 * 作者:LM
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpServlet getServletByName(String name) throws Exception{
		Map servlet=parse.getServlets().get(name);
		String urlPattern=servlet.get("url-pattern").toString();
		Object obj=loadedServlet.get(urlPattern);
		if(obj!=null){
			if(obj instanceof HttpServlet)
				return (HttpServlet)loadedServlet.get(urlPattern);
			else{
				Map servletOther=(Map)loadedServlet.get(urlPattern);
				String servletClass=servletOther.get("servlet-class").toString();
				Class servletLoadClass=cherryLoder.loadClass(servletClass);
				HttpServlet hs=(HttpServlet)servletLoadClass.newInstance();
				ServletContext sc=CherryServletContext.contextList.get(parse.getContextPath());
				CherryServletConfig servletConfig=new CherryServletConfig(hs,servletOther,sc);
				hs.init(servletConfig);
				loadedServlet.put(urlPattern, hs);
				return hs;
			}
		}
		return null;
	}
	public Map<String, Object> getLoadedServlet() {
		return loadedServlet;
	}
}
