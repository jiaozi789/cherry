package com.lm.cherry.server;
/**
 * 用于存储当前启动的服务器 可以解耦
 * @author jiaozi
 *
 */
public class ServerWrapper {
	/**
	 * 当前服务器
	 */
	private static HttpServer server;
	/**
	 * 启动后设置服务器
	 * @param server
	 */
	public static void setServer(HttpServer server){
		ServerWrapper.server=server;
	}
	/**
	 * 获取服务器
	 * @return
	 */
	public static HttpServer getCurrentServer(){
		return server;
	}
}
