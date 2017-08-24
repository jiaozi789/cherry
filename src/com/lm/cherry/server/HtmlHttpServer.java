package com.lm.cherry.server;

import java.util.List;

import com.lm.cherry.conf.Entry;
/**
 * 啟動http服務器
 * 作者:LM
 * 创建日期:2015-1-29上午11:15:32
 * 项目名称:cherry
 * 包名称:com.lm.cherry.server.html
 * 文件名:HtmlHttpServer.java
 */
public class HtmlHttpServer extends HttpServer{
	public HtmlHttpServer(int port, Entry entry) {
		super(port, entry);
		
	}
	/**
	 * 发布app
	 * 作者:LM
	 * 创建日期:2015-1-5下午02:08:25
	 */
	public void deployApp(){
		 List<Entry.App> lea=getEntry().getApps();
		 for(Entry.App ea : lea){
			 getDeployFactory().processDeploy(ea);
			 getLog().info("发布app"+ea.getPath()+" "+ea.getDir()+"成功");
		 }
	}
}
