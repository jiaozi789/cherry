package com.lm.cherry.server.javaee.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.util.StringUtils;

import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.tool.ContextTypeMap;
import com.lm.cherry.tool.SystemUtils;
/**
 * 请求响应对象
 * 日期:2015-5-24
 * 作者:LM
 */

public class CherryHttpServletResponse implements HttpServletResponse {
	/**
	 * 请求对象
	 */
	private CherryHttpServletRequest request;
	/**
	 * 输出流
	 */
	private CherryServletOutputStream out;
	private PrintWriter writer;
	/**
	 * http11请求对象
	 */
	private Http11Protocal http11;
	public CherryHttpServletResponse(CherryHttpServletRequest request){
		this.request=request;
		this.http11=request.getHp();
	}
	/**
	 * 添加cookie的值 
	 * cookiename=value;Path=/;Domain=domainvalue;Max-Age=seconds;HttpOnly
	 * cookiename=value; EXPIRES=Fri, 02-Jan-2020 00:00:00 GMT; PATH=/; DOMAIN=qq.com;,cookiename1=value1; PATH=/; DOMAIN=ptlogin2.qq.com; HttpOnly
	 *  1.Expires在HTTP/1.0中已经定义，Cache-Control:max-age在HTTP/1.1中才有定义，为了向下兼容，仅使用max-age不够；
	    2.Expires指定一个绝对的过期时间(GMT格式),这么做会导致至少2个问题1)客户端和服务器时间不同步导致Expires的配置出现问题 2）很容易在配置后忘记具体的过期时间，导致过期来临出现浪涌现象；
		3.max-age 指定的是从文档被访问后的存活时间，这个时间是个相对值(比如:3600s),相对的是文档第一次被请求时服务器记录的Request_time(请求时间)
		4.Expires指定的时间可以是相对文件的最后访问时间(Atime)或者修改时间(MTime),而max-age相对对的是文档的请求时间(Atime)
		5.在Apache中，max-age是根据Expires的时间来计算出来的max-age = expires- request_time:(mod_expires.c)
		path:表示cookie所在的目录，asp.net默认为/，就是根目录。在同一个服务器上有目录如下：/test/,/test/cd/,/test/dd/，现设一个cookie1的path为/test/，cookie2的path为/test/cd/，那么test下的所有页面都可以访问到cookie1，而/test/和/test/dd/的子页面不能访问cookie2。这是因为cookie能让其path路径下的页面访问。
		Domain:表示域名 相同域名和相同path会放置到同一个文件中
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param cookie
	 */
	public void addCookie(Cookie cookie) {
		Map head=http11.getResponseHead();
		Object setCookie=head.get("Set-Cookie");
		String setCookieString=setCookie!=null?setCookie.toString():"";
		String curCookie=""+cookie.getName()+"="+cookie.getValue();
		
		if(SystemUtils.isNotEmpty(cookie.getPath())){
			curCookie+=";path="+cookie.getPath();
		}
		if(SystemUtils.isNotEmpty(cookie.getMaxAge()) && !"-1".equals(String.valueOf(cookie.getMaxAge()))){
			//long date=new Date().getTime();
			//curCookie+=";expires="+new Date(date+(cookie.getMaxAge()*1000));
			curCookie+=";max-age="+(cookie.getMaxAge());
		}
		if(SystemUtils.isNotEmpty(cookie.getDomain())){
			curCookie+=";domain="+cookie.getDomain();
		}
		setCookieString=SystemUtils.isEmpty(setCookieString)
								?curCookie:(setCookieString+";"+curCookie);
		head.put("Set-Cookie", setCookieString);
	}
	/**
	 * 这是响应的日期
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param name
	 * @param date
	 */
	public void addDateHeader(String name, long date) {
		Map head=http11.getResponseHead();
		Date dd=new Date();
		dd.setTime(date);
		head.put("Date", dd);
	}
	/**
	 * 添加响应的头
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param name
	 * @param value
	 */
	public void addHeader(String name, String value) {
		Map head=http11.getResponseHead();
		head.put(name, value);
	}
	/**
	 * 添加一个int型的http头
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param name
	 * @param value
	 */
	public void addIntHeader(String name, int value) {
		Map head=http11.getResponseHead();
		head.put(name, value);
	}
	/**
	 * 是否包含name的头
	 * 日期:2015-5-24
	 * 作者:LM
	 * @param name
	 * @return
	 */
	public boolean containsHeader(String name) {
		Map head=http11.getResponseHead();
		return head.containsKey(name);
	}

	public String encodeRedirectURL(String url) {
		return url;
	}

	public String encodeRedirectUrl(String url) {
		// TODO Auto-generated method stub
		return url;
	}

	public String encodeURL(String url) {
		// TODO Auto-generated method stub
		return url;
	}

	public String encodeUrl(String url) {
		// TODO Auto-generated method stub
		return url;
	}

	public void sendError(int sc, String msg) throws IOException {
		setStatus(sc, msg);

	}

	public void sendError(int sc) throws IOException {
		setStatus(sc);
	}
	/**
	 * 这里设置location告诉客户端重定向 并且生成
	 * 日期:2015-5-25
	 * 作者:LM
	 * @param location
	 * @throws IOException
	 */
	public void sendRedirect(String location) throws IOException {
		setStatus(302);
		Map head=http11.getResponseHead();
		head.put("Location", location);
	}

	public void setDateHeader(String name, long date) {
		addDateHeader(name, date);
	}

	public void setHeader(String name, String value) {
		addHeader(name, value);

	}

	public void setIntHeader(String name, int value) {
		addIntHeader(name, value);
	}
	/**
	 * 设置响应的状态
	 * 日期:2015-5-25
	 * 作者:LM
	 * @param sc
	 */
	public void setStatus(int sc) {
		http11.setReponseStatus(sc);
		String val=SystemUtils.trim(http11.statusMap.get(sc));
		http11.setReponseText(val);
	}
	/**
	 * 设置响应的状态和文本
	 * 日期:2015-5-25
	 * 作者:LM
	 * @param sc
	 * @param sm
	 */
	public void setStatus(int sc, String sm) {
		http11.setReponseStatus(sc);
		http11.setReponseText(sm);
	}

	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub

	}

	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * 获取characterEncoding
	 * 日期:2015-5-25
	 * 作者:LM
	 * @return
	 */
	public String getCharacterEncoding() {
		Map head=http11.getResponseHead();
		String contextTypeStr=SystemUtils.trim(head.get("Content-Type"));
		if(contextTypeStr.indexOf("charset=")>=0){
			String[] csplit=contextTypeStr.split("charset=");
			return csplit[1];
		}
		return null;
	}
	/**
	 * 获取contenttype
	 * 日期:2015-5-25
	 * 作者:LM
	 * @return
	 */
	public String getContentType() {
		Map head=http11.getResponseHead();
		return SystemUtils.trim(head.get("Content-Type"));
	}
	/**
	 * 获取服务器的locale
	 * 日期:2015-5-25
	 * 作者:LM
	 * @return
	 */
	public Locale getLocale() {
		String lanague=http11.getResponseHead().get("Content-Language");
		if(SystemUtils.isEmpty(lanague)){
			return Locale.CHINA;
		}else{
			String[] splt=lanague.split(",");
			String la=splt[0];
			return new Locale(la);
		}
	}
	/**
	 * 获取输出流
	 * 日期:2015-5-25
	 * 作者:LM
	 * @return
	 * @throws IOException
	 */
	public ServletOutputStream getOutputStream() throws IOException {
		if(out==null){
			out=new CherryServletOutputStream(this);
		}
		return out;
	}
	/**
	 * 获取写入writer
	 * 日期:2015-5-25
	 * 作者:LM
	 * @return
	 * @throws IOException
	 */
	public PrintWriter getWriter() throws IOException {
		ServletOutputStream csos=this.getOutputStream();
		if(writer==null)
			writer=new PrintWriter(new OutputStreamWriter(csos,Http11Protocal.DEFAULT_ENCODING));
		return writer;
	}

	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public void resetBuffer() {
		int i=0;

	}

	public void setBufferSize(int size) {
		// TODO Auto-generated method stub
		int i=0;
	}
	/**
	 * 设置CharacterEncoding
	 * 日期:2015-5-25
	 * 作者:LM
	 * @param charset
	 */
	public void setCharacterEncoding(String charset) {
		Map head=http11.getResponseHead();
		Object contextType=head.get("Content-Type");
		String contextTypeStr=null;
		if(contextType!=null){
			contextTypeStr=contextType.toString();
		}else
		{
			contextTypeStr=ContextTypeMap.getContextType(http11.getRequestUrl());
		}
		if(contextTypeStr.indexOf("charset=")>=0){
			String[] csplit=contextTypeStr.split("charset=");
			contextTypeStr=csplit[0]+"charset="+charset;
		}else{
			contextTypeStr=contextTypeStr+";charset="+charset;
		}
		head.put("Content-Type", contextTypeStr);
	}
	/**
	 * 设置响应体的长度
	 * 日期:2015-5-25
	 * 作者:LM
	 * @param loc
	 */
	public void setContentLength(int len) {
		Map head=http11.getResponseHead();
		head.put("Content-Length", len);
	}
	/**
	 * 设置返回内容的MIME类型
	 * 日期:2015-5-25
	 * 作者:LM
	 * @param loc
	 */
	public void setContentType(String type) {
		Map head=http11.getResponseHead();
		head.put("Content-Type", type);
	}
	/**
	 * 设置响应的语言
	 * 日期:2015-5-25
	 * 作者:LM
	 * @param loc
	 */
	public void setLocale(Locale loc) {
		String enc=(loc.getLanguage()+"_"+loc.getCountry());
		Map head=http11.getResponseHead();
		head.put("Content-Language", enc);
	}
	public CherryHttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(CherryHttpServletRequest request) {
		this.request = request;
	}
	public Http11Protocal getHttp11() {
		return http11;
	}
	public void setHttp11(Http11Protocal http11) {
		this.http11 = http11;
	}
}
