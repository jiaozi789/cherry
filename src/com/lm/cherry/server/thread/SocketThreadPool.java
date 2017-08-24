package com.lm.cherry.server.thread;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.server.HttpServer;

/**
 * 获取到一个Socket进行处理的
 * 作者:LM
 * 创建日期:2014-12-31下午04:13:30
 * 项目名称:cherry
 * 包名称:com.lm.cherry.server
 * 文件名:ThreadProcess.java
 */
public class SocketThreadPool{
	//尚未处理的socket
	private List<Socket> waitSocket=new ArrayList<Socket>();
	//http服务器
	private HttpServer httpServer;
	//正在处理的线程
	private List<ProcessScoketThread> aliveProcess=new ArrayList<ProcessScoketThread>();
	//线程配置
	private Entry.Thread etConfig=null;
	boolean atopWaitThreadMark=true;
	//用于处理等待中的socket
	WaitSocketThread wst=new WaitSocketThread(this);
	/**
	 * 构造方法 启动查询队列中的socket
	 * @param httpServer
	 * @param etConfig
	 */
	public SocketThreadPool(HttpServer httpServer,Entry.Thread etConfig){
		this.httpServer=httpServer;
		this.etConfig=etConfig;
		wst.start();
	}
	public void shutdown() throws IOException{
		this.atopWaitThreadMark=false;
		List<Socket> waitSocket=getWaitSocket();
		if(waitSocket.size()>0){
			waitSocket.get(0).close();
		}
		
	}
	/**
	 * 线程处理完成直接从 正在处理进程中T出
	 * 作者:LM
	 * 创建日期:2015-1-5下午02:22:08
	 * @param http11
	 */
	public void completeThread(ProcessScoketThread http11){
		if(aliveProcess.contains(http11)){
			aliveProcess.remove(http11);
		}
	}
	/**
	 * 添加一个socket处理线程
	 * 作者:LM
	 * 创建日期:2015-1-5下午02:29:52
	 * @param socket
	 * @throws IOException 
	 */
	public void newThread(Socket socket) throws IOException{
		if(aliveProcess.size()<etConfig.getMax()){
			newProcessThread(socket);
		}else{
			if(waitSocket.size()>=etConfig.getBlockMax()){
				getHttpServer().getSocketError().throws403Error(socket);
			}else{
				getWaitSocket().add(socket);
			}
		}
	}
	/**
	 * 通过socket直接创建一个进程处理
	 * 作者:LM
	 * 创建日期:2015-1-5下午03:01:51
	 * @param socket
	 */
	private void newProcessThread(Socket socket){
		ProcessScoketThread http11=new ProcessScoketThread(socket,this);
		http11.start();
		if(http11.isAlive()){
			aliveProcess.add(http11);
		}
	}
	/**
	 * 处理等待的socket
	 * 作者:LM
	 * 创建日期:2015-1-5下午02:58:48
	 * 项目名称:cherry
	 * 包名称:com.lm.cherry.server
	 * 文件名:ThreadProcess.java
	 */
	class WaitSocketThread extends Thread{
		private SocketThreadPool tp;
		public WaitSocketThread(SocketThreadPool tp){
			this.tp=tp;
		}
		@Override
		public void run() {
			while(tp.atopWaitThreadMark){
				Socket waitSocket=tp.getOneWaitSocket();
				if(waitSocket!=null){
					if(tp.aliveProcess.size()<tp.etConfig.getMax()){
						tp.newProcessThread(waitSocket);
						tp.getWaitSocket().remove(waitSocket);
					}
				}
				try {
					Thread.currentThread().sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	/**
	 * 获取一个未处理的socket
	 * 作者:LM
	 * 创建日期:2015-1-5下午03:03:42
	 * @return
	 */
	public Socket getOneWaitSocket(){
			List<Socket> waitSocket=getWaitSocket();
			if(waitSocket.size()>0){
				return waitSocket.get(0);
			}
			return null;
	}
	public  List<Socket> getWaitSocket() {
		return waitSocket;
	}
	public void setWaitSocket(List<Socket> waitSocket) {
		this.waitSocket = waitSocket;
	}
	public HttpServer getHttpServer() {
		return httpServer;
	}
	public void setHttpServer(HttpServer httpServer) {
		this.httpServer = httpServer;
	}
	public List<ProcessScoketThread> getAliveProcess() {
		return aliveProcess;
	}
	public void setAliveProcess(List<ProcessScoketThread> aliveProcess) {
		this.aliveProcess = aliveProcess;
	}
	public Entry.Thread getEtConfig() {
		return etConfig;
	}
	public void setEtConfig(Entry.Thread etConfig) {
		this.etConfig = etConfig;
	}
	
}
