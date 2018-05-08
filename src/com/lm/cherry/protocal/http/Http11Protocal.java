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
 * httpЭ����صķ�װ
 * ����:LM
 * ��������:2014-12-31����04:10:30
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.work
 * �ļ���:WorkConfig.java
 */
public class Http11Protocal implements Protocal{

	public static final String HTTP_COOKIE = "Cookie";
	public static final String HTTP_JSESSIONID = "JSESSIONID";
	/**
	 * http ��ֵ�Դ���ķָ���
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
	 * ״̬�붨��
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
		//HttpЭ���е�3XX�����ض��� 301�ĺ����ǡ������ض��򡱣���302�ĺ����ǡ���ʱ�ض���
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
	 * �O����Ӧ�Ġ�B ���е���Ӧ״̬
	 *  
	 */
	public static String statusCode="HTTP/1.1 %{STATUS}% %{STATUSTEXT}%\r\n";//Ĭ�J200
	/**
	 * ��������������� ��Ӧ�����е�Accept-Language:zh-CN,zh;q=0.8
	 */
	public static String contentLanguage="Content-Language:%{CONTENTLANGUAGE}%\r\n";//Ĭ�Jzh-CN
	/**
	 * �����ĵ������� ���ַ�����
	 */
	public static String contentType="Content-Type:%{CONTENTTYPE}%\r\n";//Ĭ�Jtext/html;charset=UTF-8
	/**
	 * �������O������ ��HTTP headers������Ч���ڿ�ʹ��������ȴӱ���Ӳ�̶����Ǵ�����������Դ��
	 */
	public static String date="Date:%{DATE}%\r\n";//Ĭ�Jnew Date().toString()
	/**
	 * ���÷����������� �����汾
	 */
	public static String server="Server:Cherry/1.1\r\n";//Ĭ�JCherry/1.1
	/**
	 * ���ߴ���������������ְ汾����Դ��ѹ���ͷ�ѹ����
	 * �������ڱ���һЩ������������ȷ�ؼ��Content-Encoding��ͷ�����⡣
	 * ���ο� http://www.webkaka.com/blog/archives/how-to-set-Vary-Accept-Encoding-header.html
	 */
	public static String vary="Vary:Accept-Encoding\r\n";//Ĭ�JֵAccept-Encoding
	/**
	 * �����O��ԓ���� �K��ʹ��gzipѹ���������� ��Ӧ����ͷ�� Accept-Encoding:gzip, deflate, sdch
	 */
	public static String contentEncoding="Content-Encoding:gzip\r\n";
	/**
	 * ������Ӧ����ҳ���ݵĳ���
	 */
	public static String contentLength="Content-Length:%{CONTENTLENGTH}%\r\n";
	/**
	 * ����Socket�ĳ�������
	 */
	public static String connection="Connection:%{CONNECTION}%\r\n";
	/**
	 * ������Ӧͷ�� ������������� ��û��д�뵽�����
	 */
	public static String headerNext=NEXT_REGEX;
	/**
	 * Ĭ�ϵ��ַ���
	 */
	public static String DEFAULT_ENCODING="UTF-8";
	/**
	 * ����ͷ��Ϣ
	 */
	private Map<String,String> requestHead=new HashMap();
	/**
	 * ��Ӧͷ��Ϣ
	 */
	private Map<String,String> responseHead=new HashMap();
	/**
	 * ��Ӧ�������ֽ�
	 */
	private List<Integer> responseContentByte=new ArrayList<Integer>();
	
	
	private int reponseStatus=200;
	private String reponseText="OK";
	
	/**
	 * �����method������
	 */
	private String requestMethod=HTTP_METHOD_GET;
	/**
	 * ���������·��
	 */
	private String requestUrl=REPLACEMENT_NULL;
	/**
	 * ��������ҳ��
	 */
	private String includeUrl=REPLACEMENT_NULL;
	/**
	 * ��������ҳ��
	 */
	private String forwardUrl=REPLACEMENT_NULL;
	/**
	 * ����������ֽ�
	 */
	private byte requestContentByte[];
	/**
	 * ����������ı�
	 */
	private String requestContent=REPLACEMENT_NULL;
	/**
	 * �����ʾ�ͻ����Ƿ�֧�ֳ�����
	 */
	private boolean socketKeepAlive=true;
	/**
	 * ��ȡ���еĲ���
	 */
	private Map paramMap=new HashMap();
	/**
	 * ��ȡ���е��ļ�
	 */
	private Map fileMap=new HashMap();
	/**
	 * ��ǰ������߳�
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
	 * ͨ�������ȡ�� ͷ���н��� //GET /console/login/LoginForm.jsp HTTP/1.1\r\n;
	 * ����:LM
	 * ��������:2014-12-31����04:44:49
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
		//�ж��Ƿ��ǳ�����
		if(requestHead.containsKey(HTTP_CONNECTION)){
			socketKeepAlive=requestHead.get(HTTP_CONNECTION).toString().trim().equalsIgnoreCase(HTTP_KEEP_ALIVE)?true:false;
		}else{
			socketKeepAlive=false;
		}
		//Э������Ĳ����Լ��ļ�
		if(HTTP_METHOD_POST.equals(requestMethod)){
			//��ȡpost�Ĳ���
			getPostParam();
		}
		//��ȡget�Ĳ���
		getGetParam();
		
		
	}
	/**
	 * ��ȡ�����е������param�ļ���
	 * ����:2015-5-23
	 * ����:LM
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
	 * ����ķָ���ͨ��context-typeȥ�жϵ� 
	 * ���� Content-Type:multipart/form-data; boundary=----WebKitFormBoundaryDGnZRH8QYMjpIVkH
	 * ��������ݾ���
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
	 * ���� Content-Type:application/x-www-form-urlencoded
	 * ��������ݾ��� ee=ssss&aaa=123&bbb=456
	 * ����:2015-5-23
	 * ����:LM
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
			//��ȡ���ָ���ַ���
			String splitStr=contentType.split(HTTP_BOUNDARY)[1];
			//���÷ָ���ַ����и�
			String[] split=POSTDATA.split(splitStr);
			for(int i=0;i<split.length;i++){
				String tmpStr=split[i];
				if(SystemUtils.isNotEmpty(tmpStr)){
					//�û����и�
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
	 * ͨ���������ƻ�ȡ����ֵ
	 * ����:2015-5-23
	 * ����:LM
	 * @param name
	 * @return
	 */
	public String getParameter(String name){
		Object tvalue=paramMap.get(name);
		String value=tvalue!=null?tvalue.toString():null;
		return value;
	}
	/**
	 * ���ȴ�cookie�л�ȡ ���ȡ���� ȡ����
	 * ����:2015-5-23
	 * ����:LM
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
	 * ��ȡ�����е�cookie��ֵ
	 * ����:LM
	 * ��������:2015-1-6����10:58:54
	 * @param cookieName
	 * @return
	 */
	public String getCookieValue(String cookieName){
		String value=null;
		String cookie=getRequestHead().get(HTTP_COOKIE);
		if(SystemUtils.isEmpty(cookieName)){
			cookieName=HTTP_JSESSIONID;
		}
		//���cookie�в����� ��������л�ȡ
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
	 * ������Ӧ���ַ���
	 * ����:LM
	 * ��������:2015-1-5����06:31:38
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
	 * ������Ӧ���ַ���
	 * ����:LM
	 * ��������:2015-1-5����06:31:38
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
	 * ֻ������httpͷ
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
	 * ֱ�ӽ�����д��response
	 * ����:LM
	 * ��������:2015-1-6����11:25:35
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
	 * ������ִ���ֱ��д��״̬������Ӧ
	 * ����:LM
	 * ��������:2015-1-5����03:13:55
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
	 * �����ڴ���ķ�����
	 * ����:LM
	 * ��������:2015-1-6����11:15:21
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void sendRequest(String host) throws Exception{
		String ip=host.split(SPLIT_REGEX2)[0];
		int port=Integer.parseInt(host.split(SPLIT_REGEX2)[1]);
		//�������Ӻ�̨������ ����ȥ��ȡ������
		long start=new Date().getTime();
		Socket requestSocket = new Socket(ip, port);
		OutputStream requestOut=requestSocket.getOutputStream();
        requestOut.write(requestContentByte);
        requestOut.flush();
        requestSocket.shutdownOutput();
        //requestOut.close();
        long end=new Date().getTime();
        ServerWrapper.getCurrentServer().getLog().debug("��ȡ�ܹ�ʹ����"+(end-start)+"ms");
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
		this.getCurProcessThread().getThreadPool().getHttpServer().getLog().debug("\r\n����"+requestContent+"\r\n��Ӧ��"+response);
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
