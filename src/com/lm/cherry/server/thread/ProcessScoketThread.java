package com.lm.cherry.server.thread;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.proxy.HttpProxy;
import com.lm.cherry.proxy.ProxyResponse;
import com.lm.cherry.server.DeployProcessFactory;
import com.lm.cherry.server.HtmlResponse;
import com.lm.cherry.server.J2EEResponse;
import com.lm.cherry.server.Response;
import com.lm.cherry.server.ServerWrapper;
import com.lm.cherry.tool.SystemUtils;
/**
 * 处理请求的类
 * 作者:LM
 * 创建日期:2014-12-31下午05:12:16
 * 项目名称:cherry
 * 包名称:com.lm.cherry.protocal
 * 文件名:Http11Process.java
 */
public class ProcessScoketThread extends Thread{
	protected Socket socket;
	protected OutputStream outPut;
	protected SocketThreadPool threadPool;
	Http11Protocal httpProtocalThis;
	public ProcessScoketThread(Socket socket,SocketThreadPool tp){
		this.socket=socket;
		this.threadPool=tp;
	}
	/**
	 * 线程处理的清理清除
	 * 作者:LM
	 * 创建日期:2015-2-3上午09:23:03
	 * @throws IOException
	 */
	public void clear() throws IOException{
		if(!socket.isClosed() && !socket.isInputShutdown())
			socket.shutdownInput();
		if(!socket.isClosed() && !socket.isOutputShutdown())
			socket.shutdownOutput();
		if(!socket.isClosed())
			socket.close();
	}
	
	/**
	 * 判断浏览器是否关闭了当前socket 关闭了 当前的socket就不能再服务了 直接clear
	 * @return
	 */
	public boolean ifCloseConn(){
		 try{  
			 socket.sendUrgentData(0);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信  
	         return false;  
	     }catch(Exception se){  
	        return true;  
	     }  
	}
	/**
	 * 获取socket请求的头信息
	 * 这里浏览器 传递的参数是KeepAlive的话 可能不能的请求可能在同一个socket的中
	 * 所以发现头里 存在KeepAlive 则一直获取等待请求里有数据
	 * 作者:LM
	 * 创建日期:2014-12-31下午05:12:43
	 * @throws IOException
	 */
	public void process()throws Exception {
		boolean ifSocketKeepAlive=true;
		while(ifSocketKeepAlive){
			if(!socket.isConnected() || socket.isClosed() || (socket.isInputShutdown()&&socket.isOutputShutdown())){
				clear();
				return;
			}
			httpProtocalThis=new Http11Protocal(this);
			InputStream is=socket.getInputStream();
			int readLength=is.available();
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			if(readLength==0){
				if(ifSocketKeepAlive){
					Thread.currentThread().sleep(1);
					continue;
				}else{
					clear();
					return;
				}
			}
			byte[] bt = new byte[readLength];
			is.read(bt);
			bos.write(bt,0,readLength);
			bt=bos.toByteArray();
			httpProtocalThis.parse(bos.toByteArray());
			ServerWrapper.getCurrentServer().getLog().debug(new String(bt,"UTF-8"));
			String url=httpProtocalThis.getRequestUrl();
			Object obj=threadPool.getHttpServer().getDeployFactory().searchApp(url);
			ifSocketKeepAlive=httpProtocalThis.ifSupportKeepLive();
			if(obj==null){
				threadPool.getHttpServer().getSocketError().throws404Error(socket);
				if(httpProtocalThis.isSocketKeepAlive()){
					continue;
				}else{
					clear();
					return;
				}
			}
			Response reponse=null;
			if(obj instanceof Entry.App){
				Entry.App eba=(Entry.App)obj;
				reponse=new HtmlResponse();
			}
			if(obj instanceof Entry.BackApp){
				Entry.BackApp eba=(Entry.BackApp)obj;
				if(eba.getHttpProxy()==null){
					eba.setHttpProxy(new HttpProxy(eba));
				}
				reponse=new ProxyResponse();
			}
			if(obj instanceof Entry.J2EEApp){
				Entry.J2EEApp eba=(Entry.J2EEApp)obj;
				if(url.equals(eba.getPath())){
					httpProtocalThis.setRequestUrl(url+"/"+eba.getIndex());
				}
				reponse=new J2EEResponse();
			}
			if(reponse!=null){
				reponse.processResponse(httpProtocalThis);
			}
			Thread.currentThread().sleep(1);
		}
	}
	/**
	 * 线程的调用方法
	 * 作者:LM
	 * 创建日期:2015-1-5下午03:11:06
	 */
	public void run(){
		try {
			process();
			threadPool.completeThread(this);
			clear();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				threadPool.getHttpServer().getSocketError().throws500Error(socket, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public OutputStream getOutPut() throws IOException {
		if(outPut==null){
			outPut=socket.getOutputStream();
		}
		return outPut;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public SocketThreadPool getThreadPool() {
		return threadPool;
	}
	public void setThreadPool(SocketThreadPool threadPool) {
		this.threadPool = threadPool;
	}
}
