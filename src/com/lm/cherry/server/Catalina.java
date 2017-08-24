package com.lm.cherry.server;

import java.io.IOException;
import java.util.List;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.conf.WorkConfig;
import com.lm.cherry.tool.CherryException;

/**
 * ������ ����:LM ��������:2015-1-5����06:51:24gi ��Ŀ����:cherry ������:com.lm.cherry.server
 * �ļ���:Catalina.java
 */
public class Catalina {
	public static final String SERVER_HTML = "html";
	public static final String SERVER_J2EE = "j2ee";
	public static final String SERVER_PROXY = "proxy";
	private WorkConfig wkcf = new WorkConfig();
	private HttpServer hs = null;

	/**
	 * ����:LM ��������:2014-12-31����05:18:27
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
	 * ��ʼ�������ļ� ����:LM ��������:2015-1-5����06:49:56
	 */
	public void init() {
		wkcf.loadInitConifg(null);
	}

	/**
	 * 
	 * ����:LM ��������:2015-1-29����11:38:53
	 * 
	 * @throws IOException
	 */
	public void stop() throws IOException {
		if (hs != null) {
			hs.shutdown();
		}
	}

	/**
	 * �������еķ����� ����:LM ��������:2015-1-5����06:50:03
	 * 
	 * @throws CherryException
	 */
	public void run(int port) throws Exception {
		List<Entry> entrys = WorkConfig.entry;
		if (entrys.size() > 1) {
			throw new CherryException("�����ļ��� ֻ�������һ��server");
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
