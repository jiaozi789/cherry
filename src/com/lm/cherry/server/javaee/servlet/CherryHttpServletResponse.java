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
 * ������Ӧ����
 * ����:2015-5-24
 * ����:LM
 */

public class CherryHttpServletResponse implements HttpServletResponse {
	/**
	 * �������
	 */
	private CherryHttpServletRequest request;
	/**
	 * �����
	 */
	private CherryServletOutputStream out;
	private PrintWriter writer;
	/**
	 * http11�������
	 */
	private Http11Protocal http11;
	public CherryHttpServletResponse(CherryHttpServletRequest request){
		this.request=request;
		this.http11=request.getHp();
	}
	/**
	 * ���cookie��ֵ 
	 * cookiename=value;Path=/;Domain=domainvalue;Max-Age=seconds;HttpOnly
	 * cookiename=value; EXPIRES=Fri, 02-Jan-2020 00:00:00 GMT; PATH=/; DOMAIN=qq.com;,cookiename1=value1; PATH=/; DOMAIN=ptlogin2.qq.com; HttpOnly
	 *  1.Expires��HTTP/1.0���Ѿ����壬Cache-Control:max-age��HTTP/1.1�в��ж��壬Ϊ�����¼��ݣ���ʹ��max-age������
	    2.Expiresָ��һ�����ԵĹ���ʱ��(GMT��ʽ),��ô���ᵼ������2������1)�ͻ��˺ͷ�����ʱ�䲻ͬ������Expires�����ó������� 2�������������ú����Ǿ���Ĺ���ʱ�䣬���¹������ٳ�����ӿ����
		3.max-age ָ�����Ǵ��ĵ������ʺ�Ĵ��ʱ�䣬���ʱ���Ǹ����ֵ(����:3600s),��Ե����ĵ���һ�α�����ʱ��������¼��Request_time(����ʱ��)
		4.Expiresָ����ʱ�����������ļ���������ʱ��(Atime)�����޸�ʱ��(MTime),��max-age��ԶԵ����ĵ�������ʱ��(Atime)
		5.��Apache�У�max-age�Ǹ���Expires��ʱ�������������max-age = expires- request_time:(mod_expires.c)
		path:��ʾcookie���ڵ�Ŀ¼��asp.netĬ��Ϊ/�����Ǹ�Ŀ¼����ͬһ������������Ŀ¼���£�/test/,/test/cd/,/test/dd/������һ��cookie1��pathΪ/test/��cookie2��pathΪ/test/cd/����ôtest�µ�����ҳ�涼���Է��ʵ�cookie1����/test/��/test/dd/����ҳ�治�ܷ���cookie2��������Ϊcookie������path·���µ�ҳ����ʡ�
		Domain:��ʾ���� ��ͬ��������ͬpath����õ�ͬһ���ļ���
	 * ����:2015-5-24
	 * ����:LM
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
	 * ������Ӧ������
	 * ����:2015-5-24
	 * ����:LM
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
	 * �����Ӧ��ͷ
	 * ����:2015-5-24
	 * ����:LM
	 * @param name
	 * @param value
	 */
	public void addHeader(String name, String value) {
		Map head=http11.getResponseHead();
		head.put(name, value);
	}
	/**
	 * ���һ��int�͵�httpͷ
	 * ����:2015-5-24
	 * ����:LM
	 * @param name
	 * @param value
	 */
	public void addIntHeader(String name, int value) {
		Map head=http11.getResponseHead();
		head.put(name, value);
	}
	/**
	 * �Ƿ����name��ͷ
	 * ����:2015-5-24
	 * ����:LM
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
	 * ��������location���߿ͻ����ض��� ��������
	 * ����:2015-5-25
	 * ����:LM
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
	 * ������Ӧ��״̬
	 * ����:2015-5-25
	 * ����:LM
	 * @param sc
	 */
	public void setStatus(int sc) {
		http11.setReponseStatus(sc);
		String val=SystemUtils.trim(http11.statusMap.get(sc));
		http11.setReponseText(val);
	}
	/**
	 * ������Ӧ��״̬���ı�
	 * ����:2015-5-25
	 * ����:LM
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
	 * ��ȡcharacterEncoding
	 * ����:2015-5-25
	 * ����:LM
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
	 * ��ȡcontenttype
	 * ����:2015-5-25
	 * ����:LM
	 * @return
	 */
	public String getContentType() {
		Map head=http11.getResponseHead();
		return SystemUtils.trim(head.get("Content-Type"));
	}
	/**
	 * ��ȡ��������locale
	 * ����:2015-5-25
	 * ����:LM
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
	 * ��ȡ�����
	 * ����:2015-5-25
	 * ����:LM
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
	 * ��ȡд��writer
	 * ����:2015-5-25
	 * ����:LM
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
	 * ����CharacterEncoding
	 * ����:2015-5-25
	 * ����:LM
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
	 * ������Ӧ��ĳ���
	 * ����:2015-5-25
	 * ����:LM
	 * @param loc
	 */
	public void setContentLength(int len) {
		Map head=http11.getResponseHead();
		head.put("Content-Length", len);
	}
	/**
	 * ���÷������ݵ�MIME����
	 * ����:2015-5-25
	 * ����:LM
	 * @param loc
	 */
	public void setContentType(String type) {
		Map head=http11.getResponseHead();
		head.put("Content-Type", type);
	}
	/**
	 * ������Ӧ������
	 * ����:2015-5-25
	 * ����:LM
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
