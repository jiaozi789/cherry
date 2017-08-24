package com.lm.cherry.server;

public class ServerWrapper {
	private static HttpServer server;
	public static void setServer(HttpServer server){
		ServerWrapper.server=server;
	}
	public static HttpServer getCurrentServer(){
		return server;
	}
}
