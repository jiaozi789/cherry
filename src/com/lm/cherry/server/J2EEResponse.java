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
 * 处理html请求的响应
 * 作者:LM
 * 创建日期:2015-1-30下午04:11:19
 * 项目名称:cherry
 * 包名称:com.lm.cherry.server
 * 文件名:HtmlResponse.java
 */
public class J2EEResponse implements Response{
	/**
	 * 执行的servlet初始化方法
	 */
	private static final String INIT = "init";
	/**
	 * 执行的servlet的实际方法
	 */
	private static final String SERVICE = "service";
	private HtmlResponse hp=new HtmlResponse();
	/**
	 * 处理html http协议的响应
	 * 作者:LM
	 * 创建日期:2015-1-30下午04:11:35
	 * @param httpProtocal
	 * @throws IOException
	 */
	public void processResponse(Http11Protocal httpProtocal) throws Exception {
		//获取客户端连接socket的输出流
		OutputStream out=httpProtocal.getCurProcessThread().getSocket().getOutputStream();
		boolean ifSocketKeepAlive=httpProtocal.ifSupportKeepLive();
		processResponse(httpProtocal,null,null);
		if(!ifSocketKeepAlive){
			out.close();
			httpProtocal.getCurProcessThread().clear();
		}
	}
	/**
	 * 获取jsp对象
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
	 * 执行serlvet
	 * @param loader
	 * @param httpProtocal
	 * @param url
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void invokeServlet(J2EELoader loader,Http11Protocal httpProtocal,String url,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取servlet对象并且执行
		HttpServlet servlet=loader.getServletLoader().getServlet(url);
		if(servlet!=null){
			Method method=null;
			method=servlet.getClass().getMethod(SERVICE, ServletRequest.class,ServletResponse.class);
//			if("GET".equals(httpProtocal.getRequestMethod()))
//				method=servlet.getClass().getMethod("doGet", HttpServletRequest.class,HttpServletResponse.class);
//			else{
//				method=servlet.getClass().getMethod("doPost", HttpServletRequest.class,HttpServletResponse.class);;
//			}
			//执行之前如果存在过滤器 先执行过滤器
			method.invoke(servlet, request,response);
		}
	}
	/**
	 * 生成流并且写入socket中
	 * @param httpProtocal
	 * @param response
	 * @throws IOException
	 */
	public void writeToResponse(Http11Protocal httpProtocal,HttpServletRequest request,HttpServletResponse response) throws IOException{
		OutputStream out=httpProtocal.getCurProcessThread().getSocket().getOutputStream();
		response.getWriter().flush();
		response.getWriter().close();
		//搜索上下文
		Entry.J2EEApp ej=(Entry.J2EEApp)DeployProcessFactory.searchAppByContextPath(request.getContextPath());
		//获取上下文的 session的名称
		String sessionCookieName=ej.getCookieName();
		//获取当前的session
		HttpSession session=request.getSession(false);
		//如果session不为空
		if(session!=null){
			//如果session是新的 需要将session的值放入cookie中
			if(session.isNew()){
				//创建cookie对象
				Cookie cookie=new Cookie(sessionCookieName, session.getId());
				//网站所有的jsp和目录下的jsp或者servlet 都共享这个cookie
				cookie.setPath(ej.getPath());
				//将cookie加入响应中
				response.addCookie(cookie);
			}
		}
		//获取到普通的http头信息 报文输出的文件类型 服务器 日期等信息
		String reponserText=httpProtocal.generaJ2eeReponse();
		//写入默认编码的普通头信息
		out.write(reponserText.getBytes(Http11Protocal.DEFAULT_ENCODING));
		boolean ifSocketKeepAlive=httpProtocal.ifSupportKeepLive();
		//告诉浏览器 这是个长连接  可以针对该socket继续发送请求
		if(ifSocketKeepAlive){
			out.write(HtmlResponse.CONNECTION_KEEP_ALIVE.getBytes(Http11Protocal.DEFAULT_ENCODING));
		}else{		   
			out.write(HtmlResponse.CONNECTION_CLOSE.getBytes(Http11Protocal.DEFAULT_ENCODING));
		}
		//输出一个\r\n表示 \r\n后是真正的请求数据
		out.write(Http11Protocal.headerNext.getBytes(Http11Protocal.DEFAULT_ENCODING));
		//这里写入请求文件的真实内容 如果是网页就是网页内容 如果是图片就是图片内容 使用2进制流
		Integer[] contentByte=new Integer[httpProtocal.getResponseContentByte().size()];
		//转换为数组后写入输出流
		httpProtocal.getResponseContentByte().toArray(contentByte);	
		for(Integer ite:contentByte){
			out.write(ite);
		}
	}
	/**
	 * 过滤器链的对象
	 */
	private CherryFilterChain filterChain;
	/**
	 * 执行过滤器
	 * @param loader
	 * @throws Exception 
	 */
	private boolean doFilter(J2EELoader loader,String url,
			HttpServletRequest request,HttpServletResponse response,
			Callback callback) throws Exception{

		//获取过滤器 首先由容器执行第一个filter的doFilter方法 然后在由用户过滤器的
		//chain.doFilter来决定是否执行下一个过滤器
		List<Filter> filters=loader.getFilterLoader().getFilter(url);
		if(filters.size()>0){
			filterChain=new CherryFilterChain(filters,loader.getFilterLoader(),callback);
			filterChain.getRootFilter().getFilter().doFilter(request, response, filterChain);
			return filterChain.isIfContinue();
		}
		return true;
	}
	/**
	 * 判断是否存在过滤器
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
	 * 当过滤器的过滤器链调用完成后 先执行完后续的请求操作后
 * 还需要继续执行过滤器dofilter下面的内容所以 需要传入一个callback
 * 先执行一些动作
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
				ServerWrapper.getCurrentServer().getLog().debug("开始执行jsp");
				invokeJsp(loader, url, request, response);
			}else{
				HttpServlet servlet=loader.getServletLoader().getServlet(url);
				if(servlet!=null){
					invokeServlet(loader, httpProtocal, url, request, response);
				}else{
					//响应静态资源
					hp.processResponse(httpProtocal,url);
					return;
				}
			}
		}
		
	}
	/**
	 * 处理html http协议的响应
	 * 作者:LM
	 * 创建日期:2015-1-30下午04:11:35
	 * @param httpProtocal
	 * @throws IOException
	 */
	public void processResponse(Http11Protocal httpProtocal,HttpServletRequest req,HttpServletResponse res) throws Exception {
		//获取请求的url
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
		//执行jsp或者servlet或者静态网页的执行方法
		FilterCallback fc=new FilterCallback(httpProtocal, loader, url, request, response);
		if(ifHaveFilter(loader,url)){
			//调用完过滤器 请求对象有可能变成其他的请求对象所有获取到新的请求和响应对象
			this.doFilter(loader,url, request, response,fc);
			request=filterChain.getCurRequest();
			response=filterChain.getCurResponse();
			
		}else{
			//直接处理各种请求
			fc.call();
		}
		//判断mini类型 如果web.xml配置了mini类型就使用配置的类型 否则使用服务器内置的类型
		if(thisApp.getJ2eeLoder().getWebParse().getMimeMapping().size()>0){
				String contextTypeFind=ContextTypeMap.getFileType(url,thisApp.getJ2eeLoder().getWebParse().getMimeMapping());
				if(contextTypeFind!=null){
					httpProtocal.getResponseHead().put(Http11Protocal.HTTP_CONTENT_TYPE, contextTypeFind);
				}
		}
		ServerWrapper.getCurrentServer().getLog().debug("写入流");
		writeToResponse(httpProtocal,request, response);
		
	}
	/**
	 * 处理html http协议的响应
	 * 作者:LM
	 * 创建日期:2015-1-30下午04:11:35
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
