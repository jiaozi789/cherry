package com.lm.cherry.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.conf.WorkConfig;
import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.server.error.HttpError;
import com.lm.cherry.server.error.SocketError;
import com.lm.cherry.server.javaee.servlet.CherryServletContext;
import com.lm.cherry.server.thread.SystemShutDownProcess;
import com.lm.cherry.server.thread.SocketThreadPool;
import com.lm.cherry.tool.Log;
import com.lm.cherry.tool.SystemUtils;

/**
 * 靜態服務器socket類
 * 作者:LM
 * 创建日期:2014-12-31下午02:00:36
 * 项目名称:cherry
 * 包名称:com.lm.cherry.server
 * 文件名:HttpServer.java
 */
public abstract class HttpServer {
	private ServerSocket server;
	private Entry entry;
	private Log log;
	private boolean flag=false;
	private boolean ifStart=false;
	private int port;
	private String workPath="";
	private DeployProcessFactory deployFactory=new DeployProcessFactory(this);
	private SocketThreadPool socketTheadPool=null;
	private HttpError socketError=new HttpError();
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	public HttpServer(int port,Entry entry){
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
		socketTheadPool=new SocketThreadPool(this,entry.getThread());
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
			ServerWrapper.getCurrentServer().getLog().info("开始shutdown");
			Entry entry=getEntry();
			//触发关闭上下文的时间 清理资源
			List<Entry.J2EEApp> lej=entry.getJ2eeapps();
			for(Entry.J2EEApp lejtmp:lej){
				CherryServletContext context=(CherryServletContext)lejtmp.getJ2eeLoder().getServletContext();
				context.contextDestroy();
			}
			this.flag=true;
			this.socketTheadPool.shutdown();
			this.log.shutdown();
			this.server.close();
		}
	}
	/**
	 * 是否启用长连接
	 * @param protocal
	 * @return
	 */
	public boolean ifEnableKeepLive(String url,boolean clientSupport){
		Object obj=DeployProcessFactory.searchApp(url);
		String confKeepAlive="close";
		//判断浏览器是否支持长连接 如果支持 判断服务器是否判断是否支持长连接
		if(clientSupport){
			if(obj instanceof Entry.App){
				Entry.App eba=(Entry.App)obj;
				confKeepAlive=eba.getConnection();
			}
			if(obj instanceof Entry.BackApp){
				Entry.BackApp eba=(Entry.BackApp)obj;
				confKeepAlive=eba.getConnection();
			}
			if(obj instanceof Entry.J2EEApp){
				Entry.J2EEApp eba=(Entry.J2EEApp)obj;
				confKeepAlive=eba.getConnection();
			}
			if(SystemUtils.isEmpty(confKeepAlive) || "close".equals(confKeepAlive)){
				return false;
			}else{
				return true;
			}
		}
		return false;
	}
	/**
	 * 等待子類去實現
	 * 作者:LM
	 * 创建日期:2015-1-29上午11:16:49
	 */
	public abstract void deployApp() throws Exception;
	/**
	 * 开启服务
	 * 作者:LM
	 * 创建日期:2015-1-4下午06:00:53
	 */
	public void start(){
		try {
			log.info("欢迎使用Cherry服务器 作者：★廖老板★");
			log.info("启动Server 端口："+port);
			server=new ServerSocket(port,1000);
			ifStart=true;
			deployApp();
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
		Runtime.getRuntime().addShutdownHook(new SystemShutDownProcess(this));
		while(this.flag){
			Socket socket=server.accept();
		    log.debug("获取到一个socket"+socket);
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

	public DeployProcessFactory getDeployFactory() {
		return deployFactory;
	}

	public void setDeployFactory(DeployProcessFactory deployFactory) {
		this.deployFactory = deployFactory;
	}

	public HttpError getSocketError() {
		return socketError;
	}
	
	
}
