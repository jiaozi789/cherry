package com.lm.cherry.server.thread;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.server.HttpServer;

/**
 * ��ȡ��һ��Socket���д����
 * ����:LM
 * ��������:2014-12-31����04:13:30
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.server
 * �ļ���:ThreadProcess.java
 */
public class SocketThreadPool{
	//��δ�����socket
	private List<Socket> waitSocket=new ArrayList<Socket>();
	//http������
	private HttpServer httpServer;
	//���ڴ�����߳�
	private List<ProcessScoketThread> aliveProcess=new ArrayList<ProcessScoketThread>();
	//�߳�����
	private Entry.Thread etConfig=null;
	boolean atopWaitThreadMark=true;
	//���ڴ���ȴ��е�socket
	WaitSocketThread wst=new WaitSocketThread(this);
	/**
	 * ���췽�� ������ѯ�����е�socket
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
	 * �̴߳������ֱ�Ӵ� ���ڴ��������T��
	 * ����:LM
	 * ��������:2015-1-5����02:22:08
	 * @param http11
	 */
	public void completeThread(ProcessScoketThread http11){
		if(aliveProcess.contains(http11)){
			aliveProcess.remove(http11);
		}
	}
	/**
	 * ���һ��socket�����߳�
	 * ����:LM
	 * ��������:2015-1-5����02:29:52
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
	 * ͨ��socketֱ�Ӵ���һ�����̴���
	 * ����:LM
	 * ��������:2015-1-5����03:01:51
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
	 * ����ȴ���socket
	 * ����:LM
	 * ��������:2015-1-5����02:58:48
	 * ��Ŀ����:cherry
	 * ������:com.lm.cherry.server
	 * �ļ���:ThreadProcess.java
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
	 * ��ȡһ��δ�����socket
	 * ����:LM
	 * ��������:2015-1-5����03:03:42
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
