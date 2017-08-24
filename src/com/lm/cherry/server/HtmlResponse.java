package com.lm.cherry.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.protocal.http.Http11Protocal;
import com.lm.cherry.tool.ContextTypeMap;
import com.lm.cherry.tool.SystemUtils;
/**
 * 处理html请求的响应
 * 作者:LM
 * 创建日期:2015-1-30下午04:11:19
 * 项目名称:cherry
 * 包名称:com.lm.cherry.server
 * 文件名:HtmlResponse.java
 */
public class HtmlResponse implements Response{
	public void processResponse(Http11Protocal httpProtocal) throws IOException {
		this.processResponse(httpProtocal,null);
	}
	/**
	 * 处理html http协议的响应
	 * 作者:LM
	 * 创建日期:2015-1-30下午04:11:35
	 * @param httpProtocal
	 * @throws IOException
	 */
	public void processResponse(Http11Protocal httpProtocal,String rurl) throws IOException {
		//获取客户端连接socket的输出流
		OutputStream out = httpProtocal.getCurProcessThread().getSocket().getOutputStream();
		//获取请求的url
		String url=(rurl!=null?rurl:httpProtocal.getRequestUrlNoParam());
		boolean ifSocketKeepAlive=httpProtocal.ifSupportKeepLive();
		//通过请求的url获得实际的文件
		String file=httpProtocal.getCurProcessThread().getThreadPool().getHttpServer().getDeployFactory().getRequestFile(url);
		File destFile=new File(file);
		if(destFile.exists() && destFile.isDirectory()){
			String refererPage=httpProtocal.getRequestHead().get("Referer");
			if(SystemUtils.isNotEmpty(refererPage)){
				file=httpProtocal.getCurProcessThread().getThreadPool().getHttpServer().getDeployFactory().getRequestFile(refererPage);
			}
		}
		//将访问的文件创建输入流
		FileInputStream findFile=new FileInputStream(file);
		//获取到普通的http头信息 报文输出的文件类型 服务器 日期等信息 如果ie中图片等类型的context-type不设置对 不会显示图片
		Object objEj=DeployProcessFactory.searchApp(url);
		String contextType=null;
		if(objEj!=null && objEj instanceof Entry.J2EEApp){
			Entry.J2EEApp ej=(Entry.J2EEApp)objEj;
			contextType=ContextTypeMap.getFileType(file,ej.getJ2eeLoder().getWebParse().getMimeMapping());
		}else{
			contextType=ContextTypeMap.getFileType(file);
		}
		String reponserText=httpProtocal.generaHeader(contextType);
		//写入默认编码的普通头信息
		out.write(reponserText.getBytes(Http11Protocal.DEFAULT_ENCODING));
		//这里长度必须设置(长度是指 除了http头和一个换行后的 输出的文件的字节数) 不然导致浏览器出现 阻塞的问题
		out.write((Http11Protocal.contentLength.replaceAll("%\\{CONTENTLENGTH\\}%", findFile.available()+"")).getBytes(Http11Protocal.DEFAULT_ENCODING));
		//告诉浏览器 这是个长连接  可以针对该socket继续发送请求
		if(ifSocketKeepAlive){
			out.write("Connection: keep-alive\r\n".getBytes(Http11Protocal.DEFAULT_ENCODING));
		}else{
			out.write("Connection: close\r\n".getBytes(Http11Protocal.DEFAULT_ENCODING));
		}
		//输出一个\r\n表示 \r\n后是真正的请求数据
		out.write(Http11Protocal.headerNext.getBytes(Http11Protocal.DEFAULT_ENCODING));
		//这里写入请求文件的真实内容 如果是网页就是网页内容 如果是图片就是图片内容 使用2进制流
		byte[] bt=new byte[500000];
		int n=0;
		while((n=findFile.read(bt))!=-1){
			out.write(bt,0,n);
		}
		//out.write("\r\n".getBytes(Http11Protocal.DEFAULT_ENCODING));//输出终结符
		//将结果flush到客户端
		out.flush();
		//判断如果不是长链接（长链接是服务器控制的 客户端如果存在Connection: keep-alive 只是表示客户端支持长连接
		//服务器response输出Connection: keep-alive 才表示通知客户端是否要保存长连接） 就关闭该socket
		if(!ifSocketKeepAlive){
			out.close();
			httpProtocal.getCurProcessThread().clear();
		}		
	}

}
