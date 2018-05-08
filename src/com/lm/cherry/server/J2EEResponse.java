package com.lm.cherry.server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.protocal.http.Http11Protocal.REQEUSTTYPE;
import com.lm.cherry.server.javaee.J2EELoader;
import com.lm.cherry.server.javaee.JspLoader;
import com.lm.cherry.server.javaee.servlet.CherryFilterChain;
import com.lm.cherry.server.javaee.servlet.CherryHttpServletRequest;
import com.lm.cherry.server.javaee.servlet.CherryHttpServletResponse;
import com.lm.cherry.server.javaee.servlet.CherryServletConfig;
import com.lm.cherry.tool.ContextTypeMap;
import com.lm.cherry.tool.ReverseUtils;
/**
 * ����html�������Ӧ
 * ����:LM
 * ��������:2015-1-30����04:11:19
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.server
 * �ļ���:HtmlResponse.java
 */
public class J2EEResponse implements Response{
	/**
	 * ִ�е�servlet��ʼ������
	 */
	private static final String INIT = "init";
	/**
	 * ִ�е�servlet��ʵ�ʷ���
	 */
	private static final String SERVICE = "service";
	private HtmlResponse hp=new HtmlResponse();
	/**
	 * ����html httpЭ�����Ӧ
	 * ����:LM
	 * ��������:2015-1-30����04:11:35
	 * @param httpProtocal
	 * @throws IOException
	 */
	public void processResponse(Http11Protocal httpProtocal) throws Exception {
		//��ȡ�ͻ�������socket�������
		OutputStream out=httpProtocal.getCurProcessThread().getSocket().getOutputStream();
		boolean ifSocketKeepAlive=httpProtocal.ifSupportKeepLive();
		processResponse(httpProtocal,null,null);
		if(!ifSocketKeepAlive){
			out.close();
			httpProtocal.getCurProcessThread().clear();
		}
	}
	/**
	 * ��ȡjsp����
	 * @param loader
	 * @return
	 * @throws Exception 
	 */
	private Object invokeJsp(J2EELoader loader,String url,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JspLoader jspLoader=loader.getJspLoader();
		Object jspObject=jspLoader.load(url);
		CherryServletConfig servletConfig=new CherryServletConfig(loader.getServletContext());
		ReverseUtils.invoke(jspObject, 
				INIT,new Object[]{servletConfig}
				,new Class[]{ServletConfig.class});
		Object[] param=new Object[]{
				request,response
		};
		Class[] clsParam=new Class[]{
				HttpServletRequest.class,
				HttpServletResponse.class
		};
		ReverseUtils.invoke(jspObject, SERVICE, param , clsParam);
		return jspObject;
	}
	/**
	 * ִ��serlvet
	 * @param loader
	 * @param httpProtocal
	 * @param url
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void invokeServlet(J2EELoader loader,Http11Protocal httpProtocal,String url,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//��ȡservlet������ִ��
		HttpServlet servlet=loader.getServletLoader().getServlet(url);
		if(servlet!=null){
			Method method=null;
			method=servlet.getClass().getMethod(SERVICE, ServletRequest.class,ServletResponse.class);
//			if("GET".equals(httpProtocal.getRequestMethod()))
//				method=servlet.getClass().getMethod("doGet", HttpServletRequest.class,HttpServletResponse.class);
//			else{
//				method=servlet.getClass().getMethod("doPost", HttpServletRequest.class,HttpServletResponse.class);;
//			}
			//ִ��֮ǰ������ڹ����� ��ִ�й�����
			method.invoke(servlet, request,response);
		}
	}
	/**
	 * ����������д��socket��
	 * @param httpProtocal
	 * @param response
	 * @throws IOException
	 */
	public void writeToResponse(Http11Protocal httpProtocal,HttpServletRequest request,HttpServletResponse response) throws IOException{
		OutputStream out=httpProtocal.getCurProcessThread().getSocket().getOutputStream();
		response.getWriter().flush();
		response.getWriter().close();
		//����������
		Entry.J2EEApp ej=(Entry.J2EEApp)DeployProcessFactory.searchAppByContextPath(request.getContextPath());
		//��ȡ�����ĵ� session������
		String sessionCookieName=ej.getCookieName();
		//��ȡ��ǰ��session
		HttpSession session=request.getSession(false);
		//���session��Ϊ��
		if(session!=null){
			//���session���µ� ��Ҫ��session��ֵ����cookie��
			if(session.isNew()){
				//����cookie����
				Cookie cookie=new Cookie(sessionCookieName, session.getId());
				//��վ���е�jsp��Ŀ¼�µ�jsp����servlet ���������cookie
				cookie.setPath(ej.getPath());
				//��cookie������Ӧ��
				response.addCookie(cookie);
			}
		}
		//��ȡ����ͨ��httpͷ��Ϣ ����������ļ����� ������ ���ڵ���Ϣ
		String reponserText=httpProtocal.generaJ2eeReponse();
		//д��Ĭ�ϱ������ͨͷ��Ϣ
		out.write(reponserText.getBytes(Http11Protocal.DEFAULT_ENCODING));
		boolean ifSocketKeepAlive=httpProtocal.ifSupportKeepLive();
		//��������� ���Ǹ�������  ������Ը�socket������������
		if(ifSocketKeepAlive){
			out.write(HtmlResponse.CONNECTION_KEEP_ALIVE.getBytes(Http11Protocal.DEFAULT_ENCODING));
		}else{		   
			out.write(HtmlResponse.CONNECTION_CLOSE.getBytes(Http11Protocal.DEFAULT_ENCODING));
		}
		//���һ��\r\n��ʾ \r\n������������������
		out.write(Http11Protocal.headerNext.getBytes(Http11Protocal.DEFAULT_ENCODING));
		//����д�������ļ�����ʵ���� �������ҳ������ҳ���� �����ͼƬ����ͼƬ���� ʹ��2������
		Integer[] contentByte=new Integer[httpProtocal.getResponseContentByte().size()];
		//ת��Ϊ�����д�������
		httpProtocal.getResponseContentByte().toArray(contentByte);	
		for(Integer ite:contentByte){
			out.write(ite);
		}
	}
	/**
	 * ���������Ķ���
	 */
	private CherryFilterChain filterChain;
	/**
	 * ִ�й�����
	 * @param loader
	 * @throws Exception 
	 */
	private boolean doFilter(J2EELoader loader,String url,
			HttpServletRequest request,HttpServletResponse response,
			Callback callback) throws Exception{

		//��ȡ������ ����������ִ�е�һ��filter��doFilter���� Ȼ�������û���������
		//chain.doFilter�������Ƿ�ִ����һ��������
		List<Filter> filters=loader.getFilterLoader().getFilter(url);
		if(filters.size()>0){
			filterChain=new CherryFilterChain(filters,loader.getFilterLoader(),callback);
			filterChain.getRootFilter().getFilter().doFilter(request, response, filterChain);
			return filterChain.isIfContinue();
		}
		return true;
	}
	/**
	 * �ж��Ƿ���ڹ�����
	 * @param loader
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private boolean ifHaveFilter(J2EELoader loader,String url) throws Exception{
		List<Filter> filters=loader.getFilterLoader().getFilter(url);
		if(filters.size()>0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * ���������Ĺ�������������ɺ� ��ִ������������������
 * ����Ҫ����ִ�й�����dofilter������������� ��Ҫ����һ��callback
 * ��ִ��һЩ����
	 * @author jiaozi
	 *
	 */
	class FilterCallback implements Callback{
		Http11Protocal httpProtocal;
		J2EELoader loader;
		String url;
		HttpServletRequest request;
		HttpServletResponse response;
		
		public FilterCallback(Http11Protocal httpProtocal,J2EELoader loader, String url,
				HttpServletRequest request, HttpServletResponse response) {
			this.httpProtocal=httpProtocal;
			this.loader = loader;
			this.url = url;
			this.request = request;
			this.response = response;
		}
		
		@Override
		public void call() throws Exception {
			if(url.endsWith(".jsp")){
				ServerWrapper.getCurrentServer().getLog().debug("��ʼִ��jsp");
				invokeJsp(loader, url, request, response);
			}else{
				HttpServlet servlet=loader.getServletLoader().getServlet(url);
				if(servlet!=null){
					invokeServlet(loader, httpProtocal, url, request, response);
				}else{
					//��Ӧ��̬��Դ
					hp.processResponse(httpProtocal,url);
					return;
				}
			}
		}
		
	}
	/**
	 * ����html httpЭ�����Ӧ
	 * ����:LM
	 * ��������:2015-1-30����04:11:35
	 * @param httpProtocal
	 * @throws IOException
	 */
	public void processResponse(Http11Protocal httpProtocal,HttpServletRequest req,HttpServletResponse res) throws Exception {
		//��ȡ�����url
		String url=httpProtocal.getRequestUrlNoParam();
		Entry.J2EEApp thisApp=null;
		if(req!=null){
			thisApp=(Entry.J2EEApp)DeployProcessFactory.searchAppByContextPath(req.getContextPath());
		}else{
			thisApp=(Entry.J2EEApp)DeployProcessFactory.searchApp(url);
		}
		J2EELoader loader=thisApp.getJ2eeLoder();
		HttpServletRequest request=null;
		HttpServletResponse response=null;
		if(httpProtocal.getRequestType()==REQEUSTTYPE.FORWARD){
			request=req;
			response=res;
			url=httpProtocal.getForwardUrlNoParam();
			if(!url.startsWith(thisApp.getPath()+"/")){
				url=thisApp.getPath()+url;
			}
		}else if(httpProtocal.getRequestType()==REQEUSTTYPE.INCLUDE){
			processInclude(httpProtocal, req, res);
			return;
		}else{
			request=new CherryHttpServletRequest(httpProtocal,loader);
			response=new CherryHttpServletResponse((CherryHttpServletRequest)request);
		}
		//ִ��jsp����servlet���߾�̬��ҳ��ִ�з���
		FilterCallback fc=new FilterCallback(httpProtocal, loader, url, request, response);
		if(ifHaveFilter(loader,url)){
			//����������� ��������п��ܱ������������������л�ȡ���µ��������Ӧ����
			this.doFilter(loader,url, request, response,fc);
			request=filterChain.getCurRequest();
			response=filterChain.getCurResponse();
			
		}else{
			//ֱ�Ӵ����������
			fc.call();
		}
		//�ж�mini���� ���web.xml������mini���;�ʹ�����õ����� ����ʹ�÷��������õ�����
		if(thisApp.getJ2eeLoder().getWebParse().getMimeMapping().size()>0){
				String contextTypeFind=ContextTypeMap.getFileType(url,thisApp.getJ2eeLoder().getWebParse().getMimeMapping());
				if(contextTypeFind!=null){
					httpProtocal.getResponseHead().put(Http11Protocal.HTTP_CONTENT_TYPE, contextTypeFind);
				}
		}
		ServerWrapper.getCurrentServer().getLog().debug("д����");
		writeToResponse(httpProtocal,request, response);
		
	}
	/**
	 * ����html httpЭ�����Ӧ
	 * ����:LM
	 * ��������:2015-1-30����04:11:35
	 * @param httpProtocal
	 * @throws IOException
	 */
	private void processInclude(Http11Protocal httpProtocal,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String includeUrl=httpProtocal.getIncludeUrlNoParam();
		Entry.J2EEApp thisApp=(Entry.J2EEApp)DeployProcessFactory.searchApp(includeUrl);
		J2EELoader loader=thisApp.getJ2eeLoder();
		if(includeUrl.endsWith(".jsp")){
			invokeJsp(loader, includeUrl, request, response);
		}else{
			invokeServlet(loader, httpProtocal, includeUrl, request, response);
		}
	}

}
