package com.lm.cherry.server;

import java.util.List;

import com.lm.cherry.conf.Entry;
/**
 * ���Ӵ��������
 * ����:LM
 * ��������:2015-1-29����11:15:23
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.server.html
 * �ļ���:HttpProxyServer.java
 */
public class HttpProxyServer  extends HttpServer{

	public HttpProxyServer(int port, Entry entry) {
		super(port, entry);
	}
	/**
	 * ��������app
	 * ����:LM
	 * ��������:2015-1-5����02:08:25
	 */
	public void deployApp(){
		 List<Entry.BackApp> lea=getEntry().getBapps();
		 for(Entry.BackApp ea : lea){
			 getDeployFactory().processDeploy(ea);
		 }
	}
}
