package com.lm.cherry.conf;

import java.util.ArrayList;
import java.util.List;

import com.lm.cherry.proxy.HttpProxy;
import com.lm.cherry.server.javaee.J2EELoader;
import com.lm.cherry.tool.SystemUtils;

/**
 * 所有的实体类
 * 作者:LM
 * 创建日期:2015-1-4下午04:05:51
 * 项目名称:cherry
 * 包名称:com.lm.cherry
 * 文件名:Entry.java
 */
public class Entry {
	public Entry(){
		dir=new ArrayList<Dir>();
		apps=new ArrayList<App>();
		bapps=new ArrayList<BackApp>();
		j2eeapps=new ArrayList<J2EEApp>();
	}
	private int port;
	private String type;
	private String protocol;
	private Entry.LogC log;
	private List<Entry.Dir> dir;
	private List<Entry.App> apps;
	private List<Entry.BackApp> bapps;
	private List<Entry.J2EEApp> j2eeapps;
	private Entry.Thread thread;
	/**
	 * 键值对的类
	 * 作者:LM
	 * 创建日期:2015-1-4下午04:07:29
	 * 项目名称:cherry
	 * 包名称:com.lm.cherry
	 * 文件名:Entry.java
	 */
	public static class KeyVal{
		private String key;
		private String value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	/**
	 * 目录定义的类
	 * 作者:LM
	 * 创建日期:2015-1-4下午04:07:36
	 * 项目名称:cherry
	 * 包名称:com.lm.cherry
	 * 文件名:Entry.java
	 */
	public static class Dir{
		private KeyVal kv;
		public String getName() {
			return kv.getKey();
		}
		public void setName(String name) {
			if(kv==null){
				kv=new KeyVal();
			}
			kv.setKey(name);
		}
		public String getPath() {
			return kv.getValue();
		}
		public void setPath(String path) {
			if(kv==null){
				kv=new KeyVal();
			}
			kv.setValue(path);
		}
		
	}
	/**
	 * 线程的配置类
	 * 作者:LM
	 * 创建日期:2015-1-4下午04:15:05
	 * 项目名称:cherry
	 * 包名称:com.lm.cherry.work
	 * 文件名:Entry.java
	 */
	public static class Thread{
		private int blockMax=50;
		private int max=10;
		private int timeOut=10000;
		private int sendTimeout=10000;
		private int acceptTimeout=10000;
		private int retryCount=3;
		public int getMax() {
			return max;
		}
		public void setMax(int max) {
			this.max = max;
		}
		public int getTimeOut() {
			return timeOut;
		}
		public void setTimeOut(int timeOut) {
			this.timeOut = timeOut;
		}
		public int getSendTimeout() {
			return sendTimeout;
		}
		public void setSendTimeout(int sendTimeout) {
			this.sendTimeout = sendTimeout;
		}
		public int getAcceptTimeout() {
			return acceptTimeout;
		}
		public void setAcceptTimeout(int acceptTimeout) {
			this.acceptTimeout = acceptTimeout;
		}
		public int getRetryCount() {
			return retryCount;
		}
		public void setRetryCount(int retryCount) {
			this.retryCount = retryCount;
		}
		public int getBlockMax() {
			return blockMax;
		}
		public void setBlockMax(int blockMax) {
			this.blockMax = blockMax;
		}
	}
	public static class LogC{
		private String enableLog;
		private String logLevel;
		private String logOut;
		private String logPath;
		public String getEnableLog() {
			return enableLog;
		}
		public void setEnableLog(String enableLog) {
			this.enableLog = enableLog;
		}
		public String getLogLevel() {
			return logLevel;
		}
		public void setLogLevel(String logLevel) {
			this.logLevel = logLevel;
		}
		public String getLogOut() {
			return logOut;
		}
		public void setLogOut(String logOut) {
			this.logOut = logOut;
		}
		public String getLogPath() {
			return logPath;
		}
		public void setLogPath(String logPath) {
			this.logPath = logPath;
		}
	}
	/**
	 * 代理后台应用的
	 * 作者:LM
	 * 创建日期:2015-1-4下午05:02:21
	 * 项目名称:cherry
	 * 包名称:com.lm.cherry.work
	 * 文件名:Entry.java
	 */
	public static class BackApp{
		private String id;
		private String path;
		private String pathAppend;
		private String proxyPath;
		private String cookieName;
		private String strickSession;
		private String index;
		private String connection;
		public String getConnection() {
			return connection;
		}
		public void setConnection(String connection) {
			this.connection = connection;
		}
		private HttpProxy httpProxy;

		public HttpProxy getHttpProxy() {
			return httpProxy;
		}
		public void setHttpProxy(HttpProxy httpProxy) {
			this.httpProxy = httpProxy;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getPathAppend() {
			return pathAppend;
		}
		public void setPathAppend(String pathAppend) {
			this.pathAppend = pathAppend;
		}
		public String getProxyPath() {
			return proxyPath;
		}
		public void setProxyPath(String proxyPath) {
			this.proxyPath = proxyPath;
		}
		public String getCookieName() {
			return cookieName;
		}
		public void setCookieName(String cookieName) {
			this.cookieName = cookieName;
		}
		public String getStrickSession() {
			return strickSession;
		}
		public void setStrickSession(String strickSession) {
			this.strickSession = strickSession;
		}
		public String getIndex() {
			return index;
		}
		public void setIndex(String index) {
			this.index = index;
		}
	}
	/**
	 * 静态网页发布应用的app
	 * 作者:LM
	 * 创建日期:2015-1-4下午05:01:50
	 * 项目名称:cherry
	 * 包名称:com.lm.cherry.work
	 * 文件名:Entry.java
	 */
	public static class App{
		private String id;
		private String path;
		private String dir;
		private String index;
		private String connection;
		public String getConnection() {
			return connection;
		}
		public void setConnection(String connection) {
			this.connection = connection;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getDir() {
			return dir;
		}
		public void setDir(String dir) {
			this.dir = dir;
		}
		public String getIndex() {
			return index;
		}
		public void setIndex(String index) {
			this.index = index;
		}
	}
	/**
	 * 发布J2EE应用的app
	 * 作者:LM
	 * 创建日期:2015-1-4下午05:01:50
	 * 项目名称:cherry
	 * 包名称:com.lm.cherry.work
	 * 文件名:J2EEApp.java
	 */
	public static class J2EEApp{
		private String id;
		private String path;
		private String dir;
		private String index;
		private String cookieName;
		private String connection;
		private J2EELoader j2eeLoder;
		
		public J2EELoader getJ2eeLoder() {
			return j2eeLoder;
		}
		public void setJ2eeLoder(J2EELoader loder) {
			j2eeLoder = loder;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getDir() {
			return dir;
		}
		public void setDir(String dir) {
			this.dir = dir;
		}
		public String getIndex() {
			return index;
		}
		public void setIndex(String index) {
			this.index = index;
		}
		public String getCookieName() {
			if(SystemUtils.isEmpty(cookieName)){
				cookieName="JSESSIONID";
			}
			return cookieName;
		}
		public void setCookieName(String cookieName) {
			this.cookieName = cookieName;
		}
		public String getConnection() {
			if(connection==null){
				connection="close";
			}
			return connection;
		}
		public void setConnection(String connection) {
			this.connection = connection;
		}
		
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Entry.Thread getThread() {
		return thread;
	}
	public void setThread(Entry.Thread thread) {
		this.thread = thread;
	}
	public List<Entry.Dir> getDir() {
		return dir;
	}
	public void setDir(List<Entry.Dir> dir) {
		this.dir = dir;
	}
	public List<Entry.App> getApps() {
		return apps;
	}
	public void setApps(List<Entry.App> apps) {
		this.apps = apps;
	}
	public List<Entry.BackApp> getBapps() {
		return bapps;
	}
	public void setBapps(List<Entry.BackApp> bapps) {
		this.bapps = bapps;
	}
	public Entry.LogC getLog() {
		return log;
	}
	public void setLog(Entry.LogC log) {
		this.log = log;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Entry.J2EEApp> getJ2eeapps() {
		return j2eeapps;
	}
	public void setJ2eeapps(List<Entry.J2EEApp> j2eeapps) {
		this.j2eeapps = j2eeapps;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	
}
