package com.lm.cherry.server;

import java.util.List;

import com.lm.cherry.conf.Entry;
/**
 * 啟動代理服務器
 * 作者:LM
 * 创建日期:2015-1-29上午11:15:23
 * 项目名称:cherry
 * 包名称:com.lm.cherry.server.html
 * 文件名:HttpProxyServer.java
 */
public class HttpProxyServer  extends HttpServer{

	public HttpProxyServer(int port, Entry entry) {
		super(port, entry);
	}
	/**
	 * 发布代理app
	 * 作者:LM
	 * 创建日期:2015-1-5下午02:08:25
	 */
	public void deployApp(){
		 List<Entry.BackApp> lea=getEntry().getBapps();
		 for(Entry.BackApp ea : lea){
			 getDeployFactory().processDeploy(ea);
		 }
	}
}
