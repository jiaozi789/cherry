package com.lm.cherry.server.thread;

import java.io.IOException;
import java.util.List;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.server.HttpServer;
import com.lm.cherry.server.javaee.servlet.CherryServletContext;
/**
 * 系统关闭时触发
 * 日期:2015-5-27
 * 作者:LM
 */
public class SystemShutDownProcess extends Thread {
	protected HttpServer hs;
	public SystemShutDownProcess(HttpServer hs){
		this.hs=hs;
	}
	/**
	 * 触发后自动调用run方法
	 * 日期:2015-5-27
	 * 作者:LM
	 */
	@Override
	public void run() {
		try {
			//关闭服务器
			hs.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
