package com.lm.cherry.server;
/**
 * ���ڴ洢��ǰ�����ķ����� ���Խ���
 * @author jiaozi
 *
 */
public class ServerWrapper {
	/**
	 * ��ǰ������
	 */
	private static HttpServer server;
	/**
	 * ���������÷�����
	 * @param server
	 */
	public static void setServer(HttpServer server){
		ServerWrapper.server=server;
	}
	/**
	 * ��ȡ������
	 * @return
	 */
	public static HttpServer getCurrentServer(){
		return server;
	}
}
