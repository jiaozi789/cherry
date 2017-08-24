package com.lm.cherry.proxy;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.server.thread.ProcessScoketThread;
import com.lm.cherry.tool.SystemUtils;
/*
 * http������
 */
public class HttpProxy {
	//��̨Ӧ��
	private Entry.BackApp app;
	//����Ĵ����ļ���
	private Map<String,BigInteger> requestCount=new HashMap<String,BigInteger>();
	//sessionId ��Ӧ�Ļ���
	private Map<String,String> requestSession=new HashMap<String,String>(); 
	public HttpProxy(Entry.BackApp app){
		this.app=app;
	}
	/**
	 * ͳ��������
	 * ����:LM
	 * ��������:2015-1-6����10:17:17
	 * @param hp
	 */
	public void statisRequest(ProcessScoketThread hp){
		if(requestCount.size()==0){
			
		}
	}
	public String getProxyPath(){
		if(requestCount.size()==0){
			String proxyPath=app.getProxyPath();
			String[] split=proxyPath.split(",");
			for(int i=0;i<split.length;i++){
				requestCount.put(split[i], new BigInteger("0"));
			}
			return split[0];
		}else{
			String maxUrl="";
			BigInteger maxRequest=null;
			int i=0;
			for(Map.Entry me:requestCount.entrySet()){
				if(i==0){
					maxUrl=me.getKey().toString();
					maxRequest=new BigInteger(me.getValue().toString());
				}else{
					if(new BigInteger(me.getValue().toString()).compareTo(maxRequest)>0){
						maxRequest=new BigInteger(me.getValue().toString());
						maxUrl=me.getKey().toString();
					}
				}
			}
			return maxUrl;
		}
	}
	/**
	 * ���ʹ���ָ���ķ�����
	 * ����:LM
	 * ��������:2015-1-6����11:01:56
	 * @param htp11
	 * @throws IOException 
	 */
	public void sendProxy(Http11Protocal htp11) throws Exception{
		String cookieName=app.getCookieName();
		String sessionValue=htp11.getCookieValue(cookieName);
		String path=null;
		String strick=app.getStrickSession();
		if(SystemUtils.isEmpty(sessionValue)){
			path=getProxyPath();
		}else{
			if("true".equals(strick)){
				if(requestSession.get(sessionValue)!=null){
					path=requestSession.get(sessionValue).toString();
				}else{
					path=getProxyPath();
					requestSession.put(sessionValue, path);
				}
			}else{
				path=getProxyPath();
			}
		}
		if(path==null){
			htp11.getCurProcessThread().getThreadPool().getHttpServer().getSocketError().throws404Error(
					htp11.getCurProcessThread().getSocket());
		}
		htp11.sendRequest(path);
	}
}
