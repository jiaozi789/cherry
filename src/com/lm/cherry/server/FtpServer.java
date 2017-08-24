package com.lm.cherry.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.conf.WorkConfig;
import com.lm.cherry.server.thread.SocketThreadPool;
import com.lm.cherry.server.thread.SystemShutDownProcess;
import com.lm.cherry.tool.Log;

public class FtpServer {
	private ServerSocket server;
	private Entry entry;
	private Log log;
	private boolean flag=false;
	private boolean ifStart=false;
	private int port;
	private String workPath="";
	private SocketThreadPool socketTheadPool=null;


	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	public FtpServer(int port,Entry entry){
		this.flag=true;
		this.port=port;
		this.entry=entry;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化配置 包括日志 发布应用等
	 * 作者:LM
	 * 创建日期:2015-1-5下午01:42:21
	 * @throws Exception 
	 */
	public void init() throws Exception{
		log=new Log(entry.getLog());
		//socketTheadPool=new SocketThreadPool(this,entry.getThread());
		this.workPath=WorkConfig.getWorkConfPath();
		initDir();
	}
	/**
	 * 初始化目录
	 * 作者:LM
	 * 创建日期:2015-1-5下午01:57:13
	 */
	public void initDir(){
		List<Entry.Dir> dirs=entry.getDir();
		for(Entry.Dir ed:dirs){
			if(ed.getPath().startsWith("/")){
				ed.setPath(workPath+ed.getPath());
			}
		}
	}
	
	/**
	 * 关闭服务
	 * 作者:LM
	 * 创建日期:2015-1-4下午06:03:03
	 * @throws IOException
	 */
	public void shutdown() throws IOException{
		if(ifStart){
			this.flag=true;
			this.socketTheadPool.shutdown();
			this.log.shutdown();
			this.server.close();
		}
	}
	/**
	 * 开启服务
	 * 作者:LM
	 * 创建日期:2015-1-4下午06:00:53
	 */
	public void start(){
		try {
			log.info("启动Server 端口："+port);
			server=new ServerSocket(port,1000);
			ifStart=true;
			process();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 处理浏览器连接请求
	 * 作者:LM
	 * 创建日期:2015-1-4下午06:01:00
	 * @throws IOException
	 */
	public void process() throws Exception{
		while(this.flag){
			Socket socket=server.accept();
		    log.info("获取到一个socket"+socket);
		    socketTheadPool.newThread(socket);
		}
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}
}
