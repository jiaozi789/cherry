package com.lm.cherry.server;

import java.io.IOException;
import java.util.List;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.conf.WorkConfig;
import com.lm.cherry.tool.CherryException;

/**
 * 启动类 作者:LM 创建日期:2015-1-5下午06:51:24gi 项目名称:cherry 包名称:com.lm.cherry.server
 * 文件名:Catalina.java
 */
public class Catalina {
	public static final String SERVER_HTML = "html";
	public static final String SERVER_J2EE = "j2ee";
	public static final String SERVER_PROXY = "proxy";
	private WorkConfig wkcf = new WorkConfig();
	private HttpServer hs = null;

	/**
	 * 作者:LM 创建日期:2014-12-31下午05:18:27
	 * 
	 * @param args
	 *            http
	 * @throws CherryException
	 */
	public static void main(String[] args) throws Exception {
		Catalina cla = new Catalina();
		cla.init();
		cla.run(0);
	}

	/**
	 * 初始化配置文件 作者:LM 创建日期:2015-1-5下午06:49:56
	 */
	public void init() {
		wkcf.loadInitConifg(null);
	}

	/**
	 * 
	 * 作者:LM 创建日期:2015-1-29上午11:38:53
	 * 
	 * @throws IOException
	 */
	public void stop() throws IOException {
		if (hs != null) {
			hs.shutdown();
		}
	}

	/**
	 * 启动所有的服务器 作者:LM 创建日期:2015-1-5下午06:50:03
	 * 
	 * @throws CherryException
	 */
	public void run(int port) throws Exception {
		List<Entry> entrys = WorkConfig.entry;
		if (entrys.size() > 1) {
			throw new CherryException("配置文件中 只允许出现一个server");
		}
		for (int i = 0; i < entrys.size(); i++) {
			Entry entry = entrys.get(i);
			HttpServer hs = null;
			if (port == 0) {
				port = entry.getPort();
			}
			if (SERVER_HTML.equalsIgnoreCase(entry.getType())) {
				hs = new HtmlHttpServer(port, entry);
			} else if (SERVER_PROXY.equalsIgnoreCase(entry.getType())) {
				hs = new HttpProxyServer(port, entry);
			} else if (SERVER_J2EE.equalsIgnoreCase(entry.getType())) {
				hs = new J2EEHttpServer(port, entry);
			}
			ServerWrapper.setServer(hs);
			hs.start();
		}

	}

}
