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
	 * ��ʼ������ ������־ ����Ӧ�õ�
	 * ����:LM
	 * ��������:2015-1-5����01:42:21
	 * @throws Exception 
	 */
	public void init() throws Exception{
		log=new Log(entry.getLog());
		//socketTheadPool=new SocketThreadPool(this,entry.getThread());
		this.workPath=WorkConfig.getWorkConfPath();
		initDir();
	}
	/**
	 * ��ʼ��Ŀ¼
	 * ����:LM
	 * ��������:2015-1-5����01:57:13
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
	 * �رշ���
	 * ����:LM
	 * ��������:2015-1-4����06:03:03
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
	 * ��������
	 * ����:LM
	 * ��������:2015-1-4����06:00:53
	 */
	public void start(){
		try {
			log.info("����Server �˿ڣ�"+port);
			server=new ServerSocket(port,1000);
			ifStart=true;
			process();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �����������������
	 * ����:LM
	 * ��������:2015-1-4����06:01:00
	 * @throws IOException
	 */
	public void process() throws Exception{
		while(this.flag){
			Socket socket=server.accept();
		    log.info("��ȡ��һ��socket"+socket);
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
