package com.lm.cherry.server.thread;

import java.io.IOException;
import java.util.List;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.server.HttpServer;
import com.lm.cherry.server.javaee.servlet.CherryServletContext;
/**
 * ϵͳ�ر�ʱ����
 * ����:2015-5-27
 * ����:LM
 */
public class SystemShutDownProcess extends Thread {
	protected HttpServer hs;
	public SystemShutDownProcess(HttpServer hs){
		this.hs=hs;
	}
	/**
	 * �������Զ�����run����
	 * ����:2015-5-27
	 * ����:LM
	 */
	@Override
	public void run() {
		try {
			//�رշ�����
			hs.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
