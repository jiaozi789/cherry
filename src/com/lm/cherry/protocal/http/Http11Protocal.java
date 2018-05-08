package com.lm.cherry.protocal.http;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.lm.cherry.protocal.Protocal;
import com.lm.cherry.server.ServerWrapper;
import com.lm.cherry.server.error.HttpError;
import com.lm.cherry.server.thread.ProcessScoketThread;
import com.lm.cherry.tool.SystemUtils;

/**
 * http协议相关的封装
 * 作者:LM
 * 创建日期:2014-12-31下午04:10:30
 * 项目名称:cherry
 * 包名称:com.lm.cherry.work
 * 文件名:WorkConfig.java
 */
public class Http11Protocal implements Protocal{

	public static final String HTTP_COOKIE = "Cookie";
	public static final String HTTP_JSESSIONID = "JSESSIONID";
	/**
	 * http 键值对处理的分隔符
	 */
	public static final String HTTP_PARAMFILENAME = "filename";
	public static final String HTTP_PARAM_NAME = "name";
	public static final String REPLACEMENT_END = ";";
	public static final String HTTP_BOUNDARY = "boundary=";
	public static final String REPLACEMENT_NULL = "";
	public static final String HTTP_CONTENT_TYPE = "Content-Type";
	public static final String PARAM_EQ_REGEX = "=";
	public static final String PARAM_SPLIT_REGEX = "&";
	public static final String PARAM_REGEX = "\\?";
	public static final String HTTP_CONNECTION = "Connection";
	public static final String HTTP_KEEP_ALIVE = "keep-alive";
	public static final String POSTDATA_KEY = "POSTDATA";
	public static final String SPLIT_REGEX2 = ":";
	public static final String SPLIT_REGEX = ": ";
	public static final String SPACE_REGEX = " ";
	public static final String NEXT_REGEX = "\r\n";
	public static final String HTTP_METHOD_POST = "POST";
	public static final String HTTP_METHOD_GET = "GET";
	/**
	 * 状态码定义
	 */
	public static Map statusMap=new HashMap();
	static{
		statusMap.put("100","Continue");
		statusMap.put("101","witchingProtocols");
		statusMap.put("200","OK");
		statusMap.put("201","Created");
		statusMap.put("202","Accepted");
		statusMap.put("203","Non-AuthoritativeInformation");
		statusMap.put("204","NoContent");
		statusMap.put("205","ResetContent");
		statusMap.put("206","PartialContent");
		//Http协议中的3XX都是重定向 301的含义是“永久重定向”，而302的含义是“临时重定向”
		statusMap.put("300","MultipleChoices");
		statusMap.put("301","MovedPermanently");
		statusMap.put("302","Found");
		statusMap.put("303","SeeOther");
		statusMap.put("304","NotModified");
		statusMap.put("305","UseProxy");
		statusMap.put("307","TemporaryRedirect");
		statusMap.put("400","BadRequest");
		statusMap.put("401","Unauthorized");
		statusMap.put("402","PaymentRequired");
		statusMap.put("403","Forbidden");
		statusMap.put("404","NotFound");
		statusMap.put("405","MethodNotAllowed");
		statusMap.put("406","NotAcceptable");
		statusMap.put("407","ProxyAuthenticationRequired");
		statusMap.put("408","RequestTime-out");
		statusMap.put("409","Conflict");
		statusMap.put("410","Gone");
		statusMap.put("411","LengthRequired");
		statusMap.put("412","PreconditionFailed");
		statusMap.put("413","RequestEntityTooLarge");
		statusMap.put("414","Request-URITooLarge");
		statusMap.put("415","UnsupportedMediaType");
		statusMap.put("416","Requestedrangenotsatisfiable");
		statusMap.put("417","ExpectationFailed");
		statusMap.put("500","InternalServerError");
		statusMap.put("501","NotImplemented");
		statusMap.put("502","BadGateway");
		statusMap.put("503","ServiceUnavailable");
		statusMap.put("504","GatewayTime-out");
		statusMap.put("505","HTTPVersionnotsupported");
	}
	public static enum REQEUSTTYPE{
		REQUEST,
		FORWARD,
		INCLUDE,
		EXCEPTION
	}
	private REQEUSTTYPE requestType=REQEUSTTYPE.REQUEST;
	/**
	 * 設置响应的狀態 所有的相应状态
	 *  
	 */
	public static String statusCode="HTTP/1.1 %{STATUS}% %{STATUSTEXT}%\r\n";//默認200
	/**
	 * 设置浏览器的语言 对应请求中的Accept-Language:zh-CN,zh;q=0.8
	 */
	public static String contentLanguage="Content-Language:%{CONTENTLANGUAGE}%\r\n";//默認zh-CN
	/**
	 * 设置文档的类型 及字符编码
	 */
	public static String contentType="Content-Type:%{CONTENTTYPE}%\r\n";//默認text/html;charset=UTF-8
	/**
	 * 服務器設置日期 在HTTP headers设置有效日期可使浏览器优先从本地硬盘而不是从网络下载资源。
	 */
	public static String date="Date:%{DATE}%\r\n";//默認new Date().toString()
	/**
	 * 设置服务器的名称 机器版本
	 */
	public static String server="Server:Cherry/1.1\r\n";//默認Cherry/1.1
	/**
	 * 告诉代理服务器缓存两种版本的资源：压缩和非压缩，
	 * 这有助于避免一些公共代理不能正确地检测Content-Encoding标头的问题。
	 * 理解参考 http://www.webkaka.com/blog/archives/how-to-set-Vary-Accept-Encoding-header.html
	 */
	public static String vary="Vary:Accept-Encoding\r\n";//默認值Accept-Encoding
	/**
	 * 可以設置該參數 並且使用gzip压缩内容数据 对应请求头的 Accept-Encoding:gzip, deflate, sdch
	 */
	public static String contentEncoding="Content-Encoding:gzip\r\n";
	/**
	 * 设置相应的网页数据的长度
	 */
	public static String contentLength="Content-Length:%{CONTENTLENGTH}%\r\n";
	/**
	 * 设置Socket的长短连接
	 */
	public static String connection="Connection:%{CONNECTION}%\r\n";
	/**
	 * 设置响应头后 不设置这个换行 会没法写入到浏览器
	 */
	public static String headerNext=NEXT_REGEX;
	/**
	 * 默认的字符集
	 */
	public static String DEFAULT_ENCODING="UTF-8";
	/**
	 * 请求头信息
	 */
	private Map<String,String> requestHead=new HashMap();
	/**
	 * 响应头信息
	 */
	private Map<String,String> responseHead=new HashMap();
	/**
	 * 响应的内容字节
	 */
	private List<Integer> responseContentByte=new ArrayList<Integer>();
	
	
	private int reponseStatus=200;
	private String reponseText="OK";
	
	/**
	 * 请求的method的类型
	 */
	private String requestMethod=HTTP_METHOD_GET;
	/**
	 * 请求的虚拟路径
	 */
	private String requestUrl=REPLACEMENT_NULL;
	/**
	 * 被包含的页面
	 */
	private String includeUrl=REPLACEMENT_NULL;
	/**
	 * 被包含的页面
	 */
	private String forwardUrl=REPLACEMENT_NULL;
	/**
	 * 请求的内容字节
	 */
	private byte requestContentByte[];
	/**
	 * 请求的内容文本
	 */
	private String requestContent=REPLACEMENT_NULL;
	/**
	 * 这个表示客户端是否支持长连接
	 */
	private boolean socketKeepAlive=true;
	/**
	 * 获取所有的参数
	 */
	private Map paramMap=new HashMap();
	/**
	 * 获取所有的文件
	 */
	private Map fileMap=new HashMap();
	/**
	 * 当前处理的线程
	 */
	private ProcessScoketThread curProcessThread;
	private Boolean ifSupportKeepLive=null;
	
	public ProcessScoketThread getCurProcessThread() {
		return curProcessThread;
	}
	public void setCurProcessThread(ProcessScoketThread curProcessThread) {
		this.curProcessThread = curProcessThread;
	}
	public Http11Protocal(ProcessScoketThread hp){
		this.curProcessThread=hp;
	}
	public void parse(byte[] line) throws UnsupportedEncodingException{
		requestContentByte=line;
		parse(new String(line,DEFAULT_ENCODING));
	}
	public boolean ifSupportKeepLive(){
		if(ifSupportKeepLive!=null){
			return ifSupportKeepLive;
		}else{
			String url=this.getRequestUrl();
			return getCurProcessThread().getThreadPool().getHttpServer().ifEnableKeepLive(url,this.isSocketKeepAlive());
		}
	}
	/**
	 * 通过请求获取的 头进行解析 //GET /console/login/LoginForm.jsp HTTP/1.1\r\n;
	 * 作者:LM
	 * 创建日期:2014-12-31下午04:44:49
	 * @param line
	 */
	public void parse(String line){
		requestContent=line;
		String[] split=line.split(NEXT_REGEX);
		for(String tsplit:split){
			if(!SystemUtils.isEmpty(tsplit)){
				boolean ifGetOrPost=false;
				if(tsplit.startsWith(HTTP_METHOD_GET)){
					requestMethod=HTTP_METHOD_GET;
					ifGetOrPost=true;
				}
				else if(tsplit.startsWith(HTTP_METHOD_POST)){
					requestMethod=HTTP_METHOD_POST;
					ifGetOrPost=true;
				}
				if(ifGetOrPost){
					requestUrl=tsplit.split(SPACE_REGEX)[1];
				}else{
					if(tsplit.indexOf(SPLIT_REGEX)>=0){
						String[] thSplit=tsplit.split(SPLIT_REGEX);
						requestHead.put(thSplit[0],thSplit[1].replace(NEXT_REGEX, REPLACEMENT_NULL));
					}else{
						if(requestMethod.equals(HTTP_METHOD_POST)){
							String post=requestHead.get(POSTDATA_KEY);
							if(post==null){
								post=tsplit;
							}else{
								post+=NEXT_REGEX+tsplit;
							}
							requestHead.put(POSTDATA_KEY,post);
						}
					}
				}
			}
		}
		//判断是否是长连接
		if(requestHead.containsKey(HTTP_CONNECTION)){
			socketKeepAlive=requestHead.get(HTTP_CONNECTION).toString().trim().equalsIgnoreCase(HTTP_KEEP_ALIVE)?true:false;
		}else{
			socketKeepAlive=false;
		}
		//协议请求的参数以及文件
		if(HTTP_METHOD_POST.equals(requestMethod)){
			//获取post的参数
			getPostParam();
		}
		//获取get的参数
		getGetParam();
		
		
	}
	/**
	 * 获取到所有的请求的param的集合
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	private Map getGetParam(){
		String url=getRequestUrl();
		String[] split=url.split(PARAM_REGEX);
		if(split.length>1){
			String param=split[1];
			split=param.split(PARAM_SPLIT_REGEX);
			for(String child:split){
				if(child.indexOf(PARAM_EQ_REGEX)>=0){
					String[] vkSet=child.split(PARAM_EQ_REGEX);
					paramMap.put(vkSet[0], vkSet[1]);
				}
			}
		}
		return paramMap;
	}
	/**
	 * 请求的分割是通过context-type去判断的 
	 * 例如 Content-Type:multipart/form-data; boundary=----WebKitFormBoundaryDGnZRH8QYMjpIVkH
	 * 后面的内容就是
	 *  -----WebKitFormBoundaryDGnZRH8QYMjpIVkH
		Content-Disposition: form-data; name="eee"
		
		ssss
		------WebKitFormBoundaryDGnZRH8QYMjpIVkH
		Content-Disposition: form-data; name="ggg"; filename="column.sql"
		Content-Type: application/octet-stream
		
		declare crow w_grid_column%rowtype;
		cursor csr is select * from w_grid_column where grid_id='821' and idx_seq>10  order by idx_seq;
		str varchar2(20000);
		end;
		------WebKitFormBoundaryDGnZRH8QYMjpIVkH
		Content-Disposition: form-data; name="aaa"
		
		123
	 * 例如 Content-Type:application/x-www-form-urlencoded
	 * 后面的内容就是 ee=ssss&aaa=123&bbb=456
	 * 日期:2015-5-23
	 * 作者:LM
	 * @return
	 */
	private void getPostParam(){
		//Map map=new HashMap();
		String POSTDATA=requestHead.get(POSTDATA_KEY);
		String contentType=requestHead.get(HTTP_CONTENT_TYPE);
		if(contentType.indexOf(HTTP_BOUNDARY)<0){
			String[] split=POSTDATA.split(PARAM_SPLIT_REGEX);
			for(String child:split){
				if(child.indexOf(PARAM_EQ_REGEX)>=0){
					if(!PARAM_EQ_REGEX.equals(child)){
						String[] vkSet=child.split(PARAM_EQ_REGEX);
						paramMap.put(vkSet[0], vkSet.length>1?vkSet[1]:REPLACEMENT_NULL);
					}
				}
			}
		}else{
			//获取到分割的字符串
			String splitStr=contentType.split(HTTP_BOUNDARY)[1];
			//利用分割的字符串切割
			String[] split=POSTDATA.split(splitStr);
			for(int i=0;i<split.length;i++){
				String tmpStr=split[i];
				if(SystemUtils.isNotEmpty(tmpStr)){
					//用换行切割
					String [] huanhang=tmpStr.split(NEXT_REGEX);
					String name=null;
					String fileName=null;
					boolean ifNextValue=false;
					String content=null;
					String tcontentType=null;
					for(int s=0;s<huanhang.length;s++){
						if(!ifNextValue){
							if(huanhang[s].startsWith("Content-Disposition")){
								String[] conteSplit=huanhang[s].split(REPLACEMENT_END);
								for(String conteS:conteSplit){
									conteS=conteS.trim();
									if(conteS.indexOf(PARAM_EQ_REGEX)>=0){
										if(conteS.split(PARAM_EQ_REGEX)[0].equals(HTTP_PARAM_NAME)){
											name=conteS.split(PARAM_EQ_REGEX)[1];
											name=name.replaceAll("\"", REPLACEMENT_NULL);
										}
										if(conteS.split(PARAM_EQ_REGEX)[0].equals(HTTP_PARAMFILENAME)){
											fileName=conteS.split(PARAM_EQ_REGEX)[1];
											fileName=fileName.replaceAll("\"", REPLACEMENT_NULL);
										}
									}
								}
							}else if(huanhang[s].startsWith(HTTP_CONTENT_TYPE)){
								tcontentType=huanhang[s].split(SPLIT_REGEX2)[1].trim();
							}else if(SystemUtils.isEmpty(huanhang[s])){
								ifNextValue=true;
							}
						}else{
							content+=huanhang[s];
						}
					}
					if(SystemUtils.isEmpty(fileName)){
						paramMap.put(name, content);
					}else{
						Map fileType=new HashMap();
						fileType.put(HTTP_PARAMFILENAME, fileName);
						fileType.put(HTTP_CONTENT_TYPE, tcontentType);
						fileType.put("Content", content);
						fileMap.put(name, fileType);
					}
					
				}
			}
		}
	}
	/**
	 * 通过参数名称获取参数值
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public String getParameter(String name){
		Object tvalue=paramMap.get(name);
		String value=tvalue!=null?tvalue.toString():null;
		return value;
	}
	/**
	 * 首先从cookie中获取 如果取不到 取参数
	 * 日期:2015-5-23
	 * 作者:LM
	 * @param cookieName
	 * @return
	 */
	public String getSomeValueFromAnyWhere(String cookieName){
		String cookie=getRequestHead().get(HTTP_COOKIE);
		String value=null;
		if(SystemUtils.isEmpty(cookieName)){
			cookieName=HTTP_JSESSIONID;
		}
		if(SystemUtils.isEmpty(cookie)){
			value=getParameter(cookieName);
		}else{
			value=getCookieValue(cookieName);
			if(value==null){
				value=getParameter(cookieName);
			}
		}
		return value;
	}
	/**
	 * 获取请求中的cookie的值
	 * 作者:LM
	 * 创建日期:2015-1-6上午10:58:54
	 * @param cookieName
	 * @return
	 */
	public String getCookieValue(String cookieName){
		String value=null;
		String cookie=getRequestHead().get(HTTP_COOKIE);
		if(SystemUtils.isEmpty(cookieName)){
			cookieName=HTTP_JSESSIONID;
		}
		//如果cookie中不存在 请求参数中获取
		if(SystemUtils.isNotEmpty(cookie) ){
			if(cookie.indexOf(cookieName)>=0){
				String[] fenhao=cookie.split(REPLACEMENT_END);
				for(String child:fenhao){
					if(child.trim().indexOf(cookieName+PARAM_EQ_REGEX)==0){
						value=child.split(cookieName+PARAM_EQ_REGEX)[1].trim();
					}
				}
			}
		}
		return value;
	}
	/**
	 * 生成响应的字符串
	 * 作者:LM
	 * 创建日期:2015-1-5下午06:31:38
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String generaReponse(String content,String miniType) throws UnsupportedEncodingException{
		StringBuffer sbf=new StringBuffer();
		Object acceptLanguage=requestHead.get("Accept-Language");
		byte[] contentByte=content.getBytes(DEFAULT_ENCODING);
		sbf.append(statusCode.replaceAll("%\\{STATUS\\}%", "200").replaceAll("%\\{STATUSTEXT\\}%", statusMap.get("200").toString()));
		sbf.append(contentLength.replaceAll("%\\{CONTENTLENGTH\\}%", content.length()+REPLACEMENT_NULL));
		if(SystemUtils.isEmpty(contentType))
			sbf.append(contentType.replaceAll("%\\{CONTENTTYPE\\}%", "text/html;charset=UTF-8"));
		else{
			sbf.append(contentType.replaceAll("%\\{CONTENTTYPE\\}%", miniType));
			//sbf.append("Accept-Ranges: bytes\r\n");
		}
		sbf.append(contentLanguage.replaceAll("%\\{CONTENTLANGUAGE\\}%", (acceptLanguage!=null?acceptLanguage.toString():"zh-CN")));
		sbf.append(date.replaceAll("%\\{DATE\\}%",new Date().toString()));
		sbf.append(server);
		sbf.append(headerNext);
		sbf.append(content);
		return sbf.toString();
	}
	/**
	 * 生成响应的字符串
	 * 作者:LM
	 * 创建日期:2015-1-5下午06:31:38
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String generaJ2eeReponse() throws UnsupportedEncodingException{
		StringBuffer sbf=new StringBuffer();
		Integer[] contentByte=new Integer[responseContentByte.size()];
		responseContentByte.toArray(contentByte);
		sbf.append(statusCode.replaceAll("%\\{STATUS\\}%", reponseStatus+REPLACEMENT_NULL).replaceAll("%\\{STATUSTEXT\\}%",URLEncoder.encode(reponseText,DEFAULT_ENCODING)));
		sbf.append(contentLength.replaceAll("%\\{CONTENTLENGTH\\}%", contentByte.length+REPLACEMENT_NULL));
		sbf.append(date.replaceAll("%\\{DATE\\}%",new Date().toString()));
		sbf.append(server);
		if(!responseHead.containsKey(HTTP_CONTENT_TYPE)){
			sbf.append(contentType.replaceAll("%\\{CONTENTTYPE\\}%", "text/html;charset="+DEFAULT_ENCODING));
		}
		for(String me:responseHead.keySet()){
			sbf.append(me+SPLIT_REGEX2+responseHead.get(me)+headerNext);
		}
		return sbf.toString();
	}
	/**
	 * 只是生成http头
	 * @param miniType
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String generaHeader(String miniType) throws UnsupportedEncodingException{
		StringBuffer sbf=new StringBuffer();
		Object acceptLanguage=requestHead.get("Accept-Language");
		sbf.append(statusCode.replaceAll("%\\{STATUS\\}%", "200").replaceAll("%\\{STATUSTEXT\\}%", statusMap.get("200").toString()));
		if(SystemUtils.isEmpty(miniType))
			sbf.append(contentType.replaceAll("%\\{CONTENTTYPE\\}%", "text/html;charset="+DEFAULT_ENCODING));
		else{
			sbf.append(contentType.replaceAll("%\\{CONTENTTYPE\\}%", miniType));
		}
		sbf.append(contentLanguage.replaceAll("%\\{CONTENTLANGUAGE\\}%", (acceptLanguage!=null?acceptLanguage.toString():"zh-CN")));
		sbf.append(date.replaceAll("%\\{DATE\\}%",new Date().toString()));
		sbf.append(server);
		return sbf.toString();
	}
	/**
	 * 直接将内容写入response
	 * 作者:LM
	 * 创建日期:2015-1-6上午11:25:35
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public void writeResponse(String content) throws IOException{
		OutputStream os=getCurProcessThread().getSocket().getOutputStream();
		os.write(content.getBytes(DEFAULT_ENCODING));
		os.flush();
		os.close();
		getCurProcessThread().clear();
	}
	/**
	 * 如果出现错误直接写入状态进入响应
	 * 作者:LM
	 * 创建日期:2015-1-5下午03:13:55
	 * @param status
	 * @return
	 * @throws IOException 
	 */
//	public static void writeError(Socket socket,String status,String msg) throws IOException{
////		StringBuffer sbf=new StringBuffer();
////		OutputStream os=socket.getOutputStream();
////		byte[] contentByte=msg.getBytes(DEFAULT_ENCODING);
////		sbf.append(statusCode.replaceAll("%\\{STATUS\\}%", status).replaceAll("%\\{STATUSTEXT\\}%", statusMap.get(status).toString()));
////		sbf.append(contentLength.replaceAll("%\\{CONTENTLENGTH\\}%", contentByte.length+""));
////		sbf.append(contentType.replaceAll("%\\{CONTENTTYPE\\}%", "text/html;charset=UTF-8"));
////		sbf.append(connection.replaceAll("%\\{CONNECTION\\}%", "close"));
////		sbf.append("\r\n");
////		sbf.append(msg);
////		os.write(sbf.toString().getBytes(DEFAULT_ENCODING));
////		os.flush();
////		socket.close();
//	}
	/**
	 * 适用于代理的服务器
	 * 作者:LM
	 * 创建日期:2015-1-6上午11:15:21
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void sendRequest(String host) throws Exception{
		String ip=host.split(SPLIT_REGEX2)[0];
		int port=Integer.parseInt(host.split(SPLIT_REGEX2)[1]);
		//这里连接后台服务器 并且去获取到数据
		long start=new Date().getTime();
		Socket requestSocket = new Socket(ip, port);
		OutputStream requestOut=requestSocket.getOutputStream();
        requestOut.write(requestContentByte);
        requestOut.flush();
        requestSocket.shutdownOutput();
        //requestOut.close();
        long end=new Date().getTime();
        ServerWrapper.getCurrentServer().getLog().debug("读取总共使用了"+(end-start)+"ms");
        InputStream requestIn=requestSocket.getInputStream();
        sendResponse(requestIn);
        requestIn.close();
        requestSocket.close();
	}
	public void sendResponse(InputStream is) throws Exception{
		Socket clientSocket=this.getCurProcessThread().getSocket();
		OutputStream clientOut=clientSocket.getOutputStream();
		int n=0;
		byte[] bt=new byte[500000];
		ByteArrayOutputStream ba=new ByteArrayOutputStream();
		while((n=is.read(bt))!=-1){
			ba.write(bt,0,n);
			clientOut.write(bt,0,n);
		}	
		String response=new String(ba.toByteArray(),DEFAULT_ENCODING);
		this.getCurProcessThread().getThreadPool().getHttpServer().getLog().debug("\r\n请求："+requestContent+"\r\n响应："+response);
		clientOut.flush();
		bt=null;
		if(!socketKeepAlive){
			getCurProcessThread().clear();
		}
	}
	public String getRequestUrl() {
		requestUrl=requestUrl.replaceAll("/{1,}", "/");
		return requestUrl;
	}
	public String getRequestUrlNoParam() {
		String url=getRequestUrl();
		String[] split=url.split(PARAM_REGEX);
		return split[0];
	}
	public String getForwardUrlNoParam() {
		String url=getForwardUrl();
		String[] split=url.split(PARAM_REGEX);
		return split[0];
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public Map<String, String> getRequestHead() {
		return requestHead;
	}
	public void setRequestHead(Map<String, String> requestHead) {
		this.requestHead = requestHead;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public boolean isSocketKeepAlive() {
		return socketKeepAlive;
	}
	public void setSocketKeepAlive(boolean socketKeepAlive) {
		this.socketKeepAlive = socketKeepAlive;
	}
	public Map getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}
	public Map<String, String> getResponseHead() {
		return responseHead;
	}
	public void setResponseHead(Map<String, String> responseHead) {
		this.responseHead = responseHead;
	}
	public List<Integer> getResponseContentByte() {
		return responseContentByte;
	}
	public void setResponseContentByte(List<Integer> responseContentByte) {
		this.responseContentByte = responseContentByte;
	}
	public int getReponseStatus() {
		return reponseStatus;
	}
	public void setReponseStatus(int reponseStatus) {
		this.reponseStatus = reponseStatus;
	}
	public String getReponseText() {
		return reponseText;
	}
	public void setReponseText(String reponseText) {
		this.reponseText = reponseText;
	}
	public REQEUSTTYPE getRequestType() {
		return requestType;
	}
	public void setRequestType(REQEUSTTYPE requestType) {
		this.requestType = requestType;
	}
	public String getIncludeUrl() {
		return includeUrl;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	public String getIncludeUrlNoParam() {
		String url=getIncludeUrl();
		String[] split=url.split(PARAM_REGEX);
		return split[0];
	}
	public void setIncludeUrl(String includeUrl) {
		this.includeUrl = includeUrl;
	}
	
}
