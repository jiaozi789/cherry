package com.lm.cherry.server.javaee;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.server.DeployProcessFactory;
import com.lm.cherry.server.javaee.servlet.CherryFilterConfig;
import com.lm.cherry.server.javaee.servlet.CherryServletContext;
import com.lm.cherry.tool.ReverseUtils;
/**
 * 通过web.xml配置的servlet信息 加载servlet的类对象
 * 日期:2015-5-19
 * 作者:LM
 */
public class FilterLoader {
	/**
	 * webxml的解析类
	 */
	private WebParse parse=null;
	/**
	 * 已经加载的servlet对象
	 * key值为访问的链接地址 value为servlet的对象
	 */
	private Map<String,List<Filter>> loadedFilter=new HashMap<String,List<Filter>>();
	private Map<Filter,Map> filterOtherInfo=new HashMap<Filter, Map>();
	/**
	 * classloader 加载类的加载器
	 */
	ClassLoader cherryLoder=null;
	/**
	 * 构造方法 传入一个webxml解析对象 并且构建classloader
	 * @param parse
	 * @throws MalformedURLException
	 */
	public FilterLoader(WebParse parse,ClassLoader cherryLoder) throws Exception{
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
		Map<String,Map> servlets=parse.getFilters();
		for(Map.Entry<String,Map> me:servlets.entrySet()){
			String filterName=me.getKey();
			Map filterOther=me.getValue();
			String filterClass=filterOther.get("filter-class").toString();
			List url=(List)filterOther.get("url-pattern");
			Class servletLoadClass=cherryLoder.loadClass(filterClass);
			Filter filter=(Filter)servletLoadClass.newInstance();
			List<Filter> filters=loadedFilter.get(url);
			if(filters==null){
				filters=new ArrayList<Filter>();
			}
			filters.add(filter);
			filterOtherInfo.put(filter, filterOther);
			for(int i=0;i<url.size();i++){
				loadedFilter.put(url.get(i).toString(), filters);
			}
			ServletContext sc=CherryServletContext.contextList.get(parse.getContextPath());
			CherryFilterConfig servletConfig=new CherryFilterConfig(filterOther,sc);
			ReverseUtils.invoke(filter, "init", new Object[]{servletConfig}, new Class[]{FilterConfig.class});
			//filter.init(servletConfig);
		}
		
	}
	/**
	 * 通过url获取Filter对象 如果 已经加载返回filter对象
	 *  /test/*.jsp,/*,/test*a/*.do
	 * 未加载 需要先创建对象 在返回
	 * 日期:2015-5-19
	 * 作者:LM
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public List<Filter> getFilter(String url) throws Exception{
		List<Filter> filters=new ArrayList<Filter>();
		Entry.J2EEApp enj2ee=(Entry.J2EEApp)DeployProcessFactory.searchApp(url);
		String contextPath=enj2ee.getPath();
		if(url.startsWith(contextPath)){
			url=url.substring(contextPath.length());
		}
		for(String key:loadedFilter.keySet()){
			String keyReplace=".{0,}"+key.replaceAll("\\*", ".{0,}");;
			Pattern p=Pattern.compile(keyReplace,Pattern.CASE_INSENSITIVE);
			Matcher ma=p.matcher(url);
			if(ma.matches()){
				filters.addAll(loadedFilter.get(key));
			}
		}
		return filters;
	}
	/**
	 * 获取配置信息
	 * 日期:2015-5-27
	 * 作者:LM
	 * @param searchName
	 * @return
	 */
	public Map getConfigueByFilter(Filter filter){
		return filterOtherInfo.get(filter);
	}
	public Map<String, List<Filter>> getLoadedFilter() {
		return loadedFilter;
	}
	public void setLoadedFilter(Map<String, List<Filter>> loadedFilter) {
		this.loadedFilter = loadedFilter;
	}
	public Map<Filter, Map> getFilterOtherInfo() {
		return filterOtherInfo;
	}
	public void setFilterOtherInfo(Map<Filter, Map> filterOtherInfo) {
		this.filterOtherInfo = filterOtherInfo;
	}
}
