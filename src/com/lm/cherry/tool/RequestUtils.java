package com.lm.cherry.tool;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.lm.cherry.server.javaee.servlet.CherryHttpServletRequest;
import com.lm.cherry.server.javaee.servlet.CherryHttpServletResponse;

public class RequestUtils {
	/**
	 * 获取HttpServletRequest对象
	 * @param request
	 * @return
	 */
	public static HttpServletRequest getHttpRequest(ServletRequest request){
		HttpServletRequest hsr=null;
		if(request instanceof ServletRequestWrapper){
			ServletRequestWrapper srw=(ServletRequestWrapper)request;
			hsr=(HttpServletRequest)srw.getRequest();
		}if(request instanceof HttpServletRequestWrapper){
			hsr=(HttpServletRequest)request;
		}else{
			hsr=(HttpServletRequest)request;
		}
		return hsr;
	}
	/**
	 * 获取CherryHttpServletRequest对象
	 * @param request
	 * @return
	 */
	public static CherryHttpServletRequest getCherryHttpRequest(ServletRequest request){
		CherryHttpServletRequest hsr=null;
		if(request instanceof ServletRequestWrapper){
			ServletRequestWrapper srw=(ServletRequestWrapper)request;
			hsr=(CherryHttpServletRequest)srw.getRequest();
		}else{
			hsr=(CherryHttpServletRequest)request;
		}
		return hsr;
		
	}
	/**
	 * 获取HttpServletRequest对象
	 * @param request
	 * @return
	 */
	public static HttpServletResponse getHttpResponse(ServletResponse response){
		HttpServletResponse hsr=null;
		if(response instanceof ServletResponseWrapper){
			ServletRequestWrapper srw=(ServletRequestWrapper)response;
			hsr=(HttpServletResponse)srw.getRequest();
		}if(response instanceof HttpServletResponseWrapper){
			hsr=(HttpServletResponse)response;
		}else{
			hsr=(HttpServletResponse)response;
		}
		return hsr;
	}
	/**
	 * 获取CherryHttpServletRequest对象
	 * @param request
	 * @return
	 */
	public static CherryHttpServletResponse getCherryHttpResponse(ServletResponse response){
		CherryHttpServletResponse hsr=null;
		if(response instanceof ServletResponseWrapper){
			ServletResponseWrapper srw=(ServletResponseWrapper)response;
			hsr=(CherryHttpServletResponse)srw.getResponse();
		}else{
			hsr=(CherryHttpServletResponse)response;
		}
		return hsr;
		
	}
}
