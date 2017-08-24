package com.lm.cherry.server.javaee.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.server.J2EEResponse;
import com.lm.cherry.tool.RequestUtils;
import com.lm.cherry.tool.SystemUtils;
/**
 * 请求转发实现类
 * 日期:2015-5-24
 * 作者:LM
 */
public class CherryRequestDispatcher implements RequestDispatcher {
	private String path;
	public CherryRequestDispatcher(String path){
		this.path=path;
	}
	/**
	 * 请求转发到指定的内部地址
	 * 日期:2015-5-27
	 * 作者:LM
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void forward(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		CherryHttpServletRequest crequest=RequestUtils.getCherryHttpRequest(request);
		CherryHttpServletResponse cresponse=RequestUtils.getCherryHttpResponse(response);
		HttpServletRequest hrequest=RequestUtils.getHttpRequest(request);
		HttpServletResponse hresponse=RequestUtils.getHttpResponse(response);
		Http11Protocal hp=crequest.getHp();
		SystemUtils.encodePath(this.path);
		if(!this.path.startsWith(crequest.getJl().getContextPath()+"/")){
			this.path=crequest.getJl().getContextPath()+"/"+this.path;
			this.path=SystemUtils.encodePath(this.path);
		}
		hp.setForwardUrl(this.path);
		hp.setRequestUrl(this.path);
		hp.setRequestType(Http11Protocal.REQEUSTTYPE.FORWARD);
		J2EEResponse reponse=new J2EEResponse();
		try {
			hp.setResponseContentByte(new ArrayList<Integer>());
			reponse.processResponse(hp,hrequest,hresponse);
		} catch (Exception e) {
			throw new ServletException(SystemUtils.getExceptionMsg(e));
		}
	}
	/**将请求地址的内容包括进来
	 * 
	 * 日期:2015-5-27
	 * 作者:LM
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void include(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		CherryHttpServletRequest crequest=null;
		CherryHttpServletResponse cresponse=null;
		if(request instanceof ServletRequestWrapper){
			ServletRequestWrapper srw=(ServletRequestWrapper)request;
			crequest=(CherryHttpServletRequest)srw.getRequest();
		}else{
			crequest=(CherryHttpServletRequest)request;
		}
		if(response instanceof ServletResponseWrapper){
			ServletResponseWrapper srw=(ServletResponseWrapper)response;
			cresponse=(CherryHttpServletResponse)srw.getResponse();
		}else{
			cresponse=(CherryHttpServletResponse)response;
		}
		//必须手工刷新缓存 不然会导致 前面jsp写入的数据 无法写入字节数组
		Http11Protocal hp=crequest.getHp();
		String oriPath=hp.getRequestUrl();
		if(!this.path.startsWith(crequest.getJl().getContextPath())){
			this.path=crequest.getJl().getContextPath()+"/"+this.path;
			this.path=SystemUtils.encodePath(this.path);
		}
		hp.setIncludeUrl(this.path);
		hp.setRequestType(Http11Protocal.REQEUSTTYPE.INCLUDE);
		J2EEResponse reponse=new J2EEResponse();
		try {
			//因为jspwriter在ServletResponseWrapperInclude里 如果直接传入cresponse 会导致前面写入的字节丢失
			reponse.processResponse(hp,crequest,(HttpServletResponse)response);
		} catch (Exception e) {
			throw new ServletException(SystemUtils.getExceptionMsg(e));
		}
	}

}
