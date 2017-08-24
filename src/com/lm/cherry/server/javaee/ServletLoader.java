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
 * ͨ��web.xml���õ�servlet��Ϣ ����servlet�������
 *   
 * ����:2015-5-19
 * ����:LM
 */
public class ServletLoader {
	/**
	 * webxml�Ľ�����
	 */
	private WebParse parse=null;
	/**
	 * �Ѿ����ص�servlet����
	 * keyֵΪ���ʵ����ӵ�ַ valueΪservlet�Ķ���
	 * 
	 * ������url����ӳ�䵽ͬһ��servlet����url���ܲ�����ΪΨһ�ı�־��
	 * ����
	 *    ��������������һ����ͻ ����ͬһ��·��ʱ���׽����ĸ�servlet�� 
	 *    �������ø���ǰ�������
	 */
	private Map<String,Object> loadedServlet=new HashMap();
	/**
	 * classloader ������ļ�����
	 */
	ClassLoader cherryLoder=null;
	/**
	 * ���췽�� ����һ��webxml�������� ���ҹ���classloader
	 * @param parse
	 * @throws MalformedURLException
	 */
	public ServletLoader(WebParse parse,ClassLoader cherryLoder) throws Exception{
		this.parse=parse;
		this.cherryLoder=cherryLoder;
	}
	/**
	 * Ĭ�ϳ�ʼ�����Ҽ���ʵ�����Ѿ�����servlet
	 * ����:2015-5-19
	 * ����:LM
	 * @throws Exception
	 */
	public void load() throws Exception{
		Map<String,Map> servlets=parse.getServlets();
		for(Map.Entry<String,Map> me:servlets.entrySet()){
			String servletName=me.getKey();
			Map servletOther=me.getValue();
			int loadOnStart=Integer.parseInt(servletOther.containsKey("load-on-startup")?servletOther.get("load-on-startup").toString():"0");
			String servletClass=servletOther.get("servlet-class").toString();
			//�������û��url-pattern ֻ��Ϊ���ڳ���������ʱ���ʼ��servlet����init ������ӳ��·��
			//��ô���servlet��url����ʹ��һ�������url
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
	 * ͨ��url��ȡservlet���� ��� �Ѿ����ط���servlet����
	 * δ���� ��Ҫ�ȴ������� �ڷ���
	 * ����:2015-5-19
	 * ����:LM
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
	 * ͨ��name��ȡservlet���� ��� �Ѿ����ط���servlet����
	 * δ���� ��Ҫ�ȴ������� �ڷ���
	 * ����:2015-5-19
	 * ����:LM
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
