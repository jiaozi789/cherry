package com.lm.cherry.server.javaee.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lm.cherry.server.javaee.FilterLoader;
import com.lm.cherry.tool.RequestUtils;
/**
 * filter链式调用 谁先定义调用第一个
 * 日期:2015-5-27
 * 作者:LM
 */
public class CherryFilterChain_Bak implements FilterChain {
	private List<Filter> filters=new ArrayList<Filter>();
	private int index;
	private FilterLoader loader;
	/**
	 * 过滤器执行完 是否继续往下执行代码
	 */
	private boolean ifContinue=true;
	public CherryFilterChain_Bak(List<Filter> filters,FilterLoader loader){
		this(filters,0,loader);
	}
	public CherryFilterChain_Bak(List<Filter> filters,int index,FilterLoader loader){
		this.filters=filters;
		this.index=index;
		this.loader=loader;
	}
	/**
	 * 调用chain的filter 会链条式的执行所有的filter如果没有调用了 所有后面的
	 * filter都终止调用
	 * 日期:2015-5-27
	 * 作者:LM
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		CherryHttpServletRequest crequest=RequestUtils.getCherryHttpRequest(request);
		CherryHttpServletResponse cresponse=RequestUtils.getCherryHttpResponse(response);
		HttpServletRequest hrequest=RequestUtils.getHttpRequest(request);
		HttpServletResponse hresponse=RequestUtils.getHttpResponse(response);
		if(index<filters.size()){
			Filter exeFilter=filters.get(index);
			Map other=loader.getConfigueByFilter(exeFilter);
			List dispatch=(List)other.get("dispatcher");
			if(dispatch.contains(crequest.getHp().getRequestType().toString())){
				exeFilter.doFilter(hrequest, hresponse, this);
			}
		}
		this.index++;
		if(index<=filters.size()){
			ifContinue=false;
		}
	}
	public boolean isIfContinue() {
		return ifContinue;
	}
	public void setIfContinue(boolean ifContinue) {
		this.ifContinue = ifContinue;
	}
	/**
	 * 过滤器链表
	 * @author Administrator
	 */
	public class FilterLink{
		/**
		 * 过滤器的集合
		 */
		private List<Filter> filters;
		/**
		 * 当前的过滤器
		 */
		private Filter curFilter;
		public FilterLink(Filter curFilter){
			this.filters=CherryFilterChain_Bak.this.filters;
			this.curFilter=curFilter;
		}
		/**
		 * 获取第一个过滤器
		 * @return
		 */
		public FilterLink getRootFilter(){
			FilterLink f=new FilterLink(filters.get(0));
			return f;
		}
		/**
		 * 获取当前的过滤器
		 * @return
		 */
		public FilterLink getCurfilter(){
			FilterLink f=new FilterLink(curFilter);
			return f;
		}
		/**
		 * 获取下一个过滤器
		 * @return
		 */
		public FilterLink getNextFilter(){
			int index=getCurPosition();
			if(index<filters.size()){
				return new FilterLink(filters.get(index+1));
			}else{
				return null;
			}
		}
		/**
		 * 获取当前过滤器在集合的位置
		 * @return
		 */
		private int getCurPosition(){
			int index=0;
			for(int i=0;i<filters.size();i++){
				if(filters.get(i)==curFilter){
					index=i;
				}
			}
			return index;
		}
		/**
		 * 获得上一个过滤器
		 * @return
		 */
		public FilterLink getPreFilter(){
			int index=getCurPosition();
			if(index>0){
				return new FilterLink(filters.get(index-1));
			}else{
				return null;
			}
		}
		/**
		 * 获取原始的过滤器对象
		 * @return
		 */
		public Filter getFilter(){
			return curFilter;
		}
		
	}
	
}
