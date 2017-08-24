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

import com.lm.cherry.server.Callback;
import com.lm.cherry.server.javaee.FilterLoader;
import com.lm.cherry.tool.RequestUtils;
/**
 * filter链式调用 谁先定义调用第一个
 * 日期:2015-5-27
 * 作者:LM
 */
public class CherryFilterChain implements FilterChain {
	private List<Filter> filters=new ArrayList<Filter>();
	private FilterLoader loader;
	private FilterLink rootFilter;
	private FilterLink preFilter;
	private Callback callback;
	private HttpServletRequest curRequest;
	private HttpServletResponse curResponse;
	/**
	 * 过滤器执行完 是否继续往下执行代码
	 */
	private boolean ifContinue=false;

	public CherryFilterChain(List<Filter> filters,FilterLoader loader,Callback callback){
		this.filters=filters;
		this.rootFilter=getRootFilter();
		preFilter=this.rootFilter;
		this.loader=loader;
		this.callback=callback;
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
		curRequest=hrequest;
		curResponse=hresponse;
		FilterLink nextFilter=preFilter.getNextFilter();
		if(nextFilter==null){
			ifContinue=true;
			try {
				callback.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			Filter exeFilter=nextFilter.getFilter();
			Map other=loader.getConfigueByFilter(exeFilter);
			List dispatch=(List)other.get("dispatcher");
			//没有添加dispatcher是不用调用该filter但是还是必须让他过
			if(dispatch.contains(crequest.getHp().getRequestType().toString())){
				preFilter=nextFilter;
				exeFilter.doFilter(hrequest, hresponse, this);
			}else{
				preFilter=nextFilter;
				doFilter(request,response);
			}
		}
	}
	/**
	 * 获取第一个过滤器
	 * @return
	 */
	public FilterLink getRootFilter(){
		FilterLink f=new FilterLink(filters.get(0));
		return f;
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
			this.filters=CherryFilterChain.this.filters;
			this.curFilter=curFilter;
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
			if(index<filters.size()-1){
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
		 * 判断是否是最后一个过滤器
		 * @return
		 */
		public boolean ifLast(){
			if(getNextFilter()==null){
				return false;
			}
			return true;
		}
		/**
		 * 获取原始的过滤器对象
		 * @return
		 */
		public Filter getFilter(){
			return curFilter;
		}
		
	}
	public HttpServletRequest getCurRequest() {
		return curRequest;
	}
	public void setCurRequest(HttpServletRequest curRequest) {
		this.curRequest = curRequest;
	}
	public HttpServletResponse getCurResponse() {
		return curResponse;
	}
	public void setCurResponse(HttpServletResponse curResponse) {
		this.curResponse = curResponse;
	}
	
}
