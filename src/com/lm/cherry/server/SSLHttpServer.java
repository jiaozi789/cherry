package com.lm.cherry.server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.List;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.conf.WorkConfig;
import com.lm.cherry.server.error.HttpError;
import com.lm.cherry.server.javaee.servlet.CherryServletContext;
import com.lm.cherry.server.thread.SocketThreadPool;
import com.lm.cherry.server.thread.SystemShutDownProcess;
import com.lm.cherry.tool.Log;
import com.lm.cherry.tool.SystemUtils;

/**
 * �o�B������socket�
 * ����:LM
 * ��������:2014-12-31����02:00:36
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.server
 * �ļ���:HttpServer.java
 */
public abstract class SSLHttpServer extends HttpServer{
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
	public SSLHttpServer(int port,Entry entry){
		super(port, entry);
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
		socketTheadPool=new SocketThreadPool(this,entry.getThread());
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
			ServerWrapper.getCurrentServer().getLog().info("��ʼshutdown");
			Entry entry=getEntry();
			//�����ر������ĵ�ʱ�� ������Դ
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
	 * �Ƿ����ó�����
	 * @param protocal
	 * @return
	 */
	public boolean ifEnableKeepLive(String url,boolean clientSupport){
		Object obj=DeployProcessFactory.searchApp(url);
		String confKeepAlive="close";
		//�ж�������Ƿ�֧�ֳ����� ���֧�� �жϷ������Ƿ��ж��Ƿ�֧�ֳ�����
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
	 * �ȴ����ȥ���F
	 * ����:LM
	 * ��������:2015-1-29����11:16:49
	 */
	public abstract void deployApp() throws Exception;
	public static final String SERVER_KEY_STORE="C:/cert/tomcat.keystore";
	/**
	 * �������˵�keystore
	 *  ֻ��ͨ�� keytool -v -genkey -alias tomcat -keyalg RSA -keystore d:/tomcat.keystore  -validity 36500
	 *  ����ͨ�� keytool -genkeypair����
	 *  ʱ�䣺2017-2-8 ����04:28:34
	 *  ���ߣ� LM
	 *  ��ϵ��ʽ��973465719@qq.com
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//System.setProperty( "javax.net.ssl.trustStore" ,SERVER_KEY_STORE );  
		System.setProperty("javax.net.ssl.keyStore", SERVER_KEY_STORE);
		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		SSLContext context = SSLContext.getInstance( "TLSv1.2" );  
		KeyStore ks = KeyStore.getInstance("jks");  
		ks.load(new FileInputStream(SERVER_KEY_STORE),  "123456".toCharArray()); 
		KeyManagerFactory kf = KeyManagerFactory.getInstance( "SunX509");  
		kf.init(ks, "123456".toCharArray());
		context.init(kf.getKeyManagers(), null ,  null );  
		ServerSocketFactory factory = context.getServerSocketFactory();
		SSLServerSocket serverScoket = (SSLServerSocket)factory.createServerSocket( 8443 ); 
		serverScoket.setEnabledCipherSuites(serverScoket.getSupportedCipherSuites());
		serverScoket.setNeedClientAuth( false );  
		serverScoket.setWantClientAuth(false);
		while ( true ) { 
			SSLSocket socket=(SSLSocket)serverScoket.accept();  
			socket.startHandshake();
			socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line=br.readLine();
			while(line!=null)
			{
				System.out.println(line);
				line=br.readLine();
			}
			if(line!=null){
				socket.getOutputStream().write("<font color=red>���</font>".getBytes("UTF-8"));
				socket.getOutputStream().flush();
				//socket.close();
			}
			//socket.getOutputStream().write("<font color=red>���</font>".getBytes("UTF-8"));
			//socket.getOutputStream().flush();
			socket.close();
			//System.out.println(socket);

		}
		
	}
	/**
	 * ��������
	 * ����:LM
	 * ��������:2015-1-4����06:00:53
	 */
	public void start(){
		try {
			log.info("��ӭʹ��Cherry������ ���ߣ������ϰ��");
			log.info("����Server �˿ڣ�"+port+",Э��https");

			
			
			
			
			ifStart=true;
			deployApp();
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
		Runtime.getRuntime().addShutdownHook(new SystemShutDownProcess(this));
		while(this.flag){
			Socket socket=server.accept();
		    log.debug("��ȡ��һ��socket"+socket);
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
