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
 * filter��ʽ���� ˭�ȶ�����õ�һ��
 * ����:2015-5-27
 * ����:LM
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
	 * ������ִ���� �Ƿ��������ִ�д���
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
	 * ����chain��filter ������ʽ��ִ�����е�filter���û�е����� ���к����
	 * filter����ֹ����
	 * ����:2015-5-27
	 * ����:LM
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
			//û�����dispatcher�ǲ��õ��ø�filter���ǻ��Ǳ���������
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
	 * ��ȡ��һ��������
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
	 * ����������
	 * @author Administrator
	 */
	public class FilterLink{
		/**
		 * �������ļ���
		 */
		private List<Filter> filters;
		/**
		 * ��ǰ�Ĺ�����
		 */
		private Filter curFilter;
		public FilterLink(Filter curFilter){
			this.filters=CherryFilterChain.this.filters;
			this.curFilter=curFilter;
		}
		/**
		 * ��ȡ��ǰ�Ĺ�����
		 * @return
		 */
		public FilterLink getCurfilter(){
			FilterLink f=new FilterLink(curFilter);
			return f;
		}
		/**
		 * ��ȡ��һ��������
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
		 * ��ȡ��ǰ�������ڼ��ϵ�λ��
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
		 * �����һ��������
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
		 * �ж��Ƿ������һ��������
		 * @return
		 */
		public boolean ifLast(){
			if(getNextFilter()==null){
				return false;
			}
			return true;
		}
		/**
		 * ��ȡԭʼ�Ĺ���������
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
