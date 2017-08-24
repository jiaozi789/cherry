package com.lm.cherry.proxy;

import java.io.IOException;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.server.Response;
import com.lm.cherry.tool.SystemUtils;
/**
 * 处理html请求的响应
 * 作者:LM
 * 创建日期:2015-1-30下午04:11:19
 * 项目名称:cherry
 * 包名称:com.lm.cherry.server
 * 文件名:HtmlResponse.java
 */
public class ProxyResponse implements Response{
	/**
	 * 处理html http协议的响应
	 * 作者:LM
	 * 创建日期:2015-1-30下午04:11:35
	 * @param httpProtocal
	 * @throws IOException
	 */
	public void processResponse(Http11Protocal httpProtocal) throws Exception {
		String url=httpProtocal.getRequestUrl();
		Entry.BackApp app=(Entry.BackApp)httpProtocal.getCurProcessThread().getThreadPool().getHttpServer().getDeployFactory().searchApp(url);
		//如果请求的路径是/如果存在pathAppend则需要添加 该值到路径下
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
