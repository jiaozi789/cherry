package com.lm.cherry.proxy;

import java.io.IOException;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.server.Response;
import com.lm.cherry.tool.SystemUtils;
/**
 * ����html�������Ӧ
 * ����:LM
 * ��������:2015-1-30����04:11:19
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.server
 * �ļ���:HtmlResponse.java
 */
public class ProxyResponse implements Response{
	/**
	 * ����html httpЭ�����Ӧ
	 * ����:LM
	 * ��������:2015-1-30����04:11:35
	 * @param httpProtocal
	 * @throws IOException
	 */
	public void processResponse(Http11Protocal httpProtocal) throws Exception {
		String url=httpProtocal.getRequestUrl();
		Entry.BackApp app=(Entry.BackApp)httpProtocal.getCurProcessThread().getThreadPool().getHttpServer().getDeployFactory().searchApp(url);
		//��������·����/�������pathAppend����Ҫ��� ��ֵ��·����
		String append=app.getPathAppend();
		if(SystemUtils.isNotEmpty(url)){
			url=url.replaceAll("/{1,}", "/");
		}
		if(url.length()==1 && url.indexOf("/")==0 ){
			url+=append;
		}
		String indexPage=app.getIndex();
		if("/".equals(url.substring(url.length()-1))){
			url+=indexPage;
		}
		app.getHttpProxy().sendProxy(httpProtocal);
	}

}
