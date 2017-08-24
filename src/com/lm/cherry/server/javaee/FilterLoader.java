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
 * ͨ��web.xml���õ�servlet��Ϣ ����servlet�������
 * ����:2015-5-19
 * ����:LM
 */
public class FilterLoader {
	/**
	 * webxml�Ľ�����
	 */
	private WebParse parse=null;
	/**
	 * �Ѿ����ص�servlet����
	 * keyֵΪ���ʵ����ӵ�ַ valueΪservlet�Ķ���
	 */
	private Map<String,List<Filter>> loadedFilter=new HashMap<String,List<Filter>>();
	private Map<Filter,Map> filterOtherInfo=new HashMap<Filter, Map>();
	/**
	 * classloader ������ļ�����
	 */
	ClassLoader cherryLoder=null;
	/**
	 * ���췽�� ����һ��webxml�������� ���ҹ���classloader
	 * @param parse
	 * @throws MalformedURLException
	 */
	public FilterLoader(WebParse parse,ClassLoader cherryLoder) throws Exception{
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
	 * ͨ��url��ȡFilter���� ��� �Ѿ����ط���filter����
	 *  /test/*.jsp,/*,/test*a/*.do
	 * δ���� ��Ҫ�ȴ������� �ڷ���
	 * ����:2015-5-19
	 * ����:LM
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
	 * ��ȡ������Ϣ
	 * ����:2015-5-27
	 * ����:LM
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
