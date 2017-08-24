package com.lm.cherry.server;

import java.util.List;

import com.lm.cherry.conf.Entry;
/**
 * ����http������
 * ����:LM
 * ��������:2015-1-29����11:15:32
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.server.html
 * �ļ���:HtmlHttpServer.java
 */
public class HtmlHttpServer extends HttpServer{
	public HtmlHttpServer(int port, Entry entry) {
		super(port, entry);
		
	}
	/**
	 * ����app
	 * ����:LM
	 * ��������:2015-1-5����02:08:25
	 */
	public void deployApp(){
		 List<Entry.App> lea=getEntry().getApps();
		 for(Entry.App ea : lea){
			 getDeployFactory().processDeploy(ea);
			 getLog().info("����app"+ea.getPath()+" "+ea.getDir()+"�ɹ�");
		 }
	}
}
