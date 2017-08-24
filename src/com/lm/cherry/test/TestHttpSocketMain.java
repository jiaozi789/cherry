package com.lm.cherry.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.GZIPOutputStream;


public class TestHttpSocketMain {
	public static void write(Socket socket,String html)throws IOException {
		InputStream is=socket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		String line = in.readLine();
		while((line)!=null && !"".equals(line)){
			System.out.println(line);
			line= in.readLine();
		}
		StringBuffer sbf=new StringBuffer();
		sbf.append("HTTP/1.1 200 OK\r\n");
		sbf.append("Content-Encoding:gzip\r\n");
		sbf.append("Content-Language:zh-CN\r\n");
		sbf.append("Content-Type:text/html;charset=UTF-8\r\n");
		sbf.append("Date:Wed, 31 Dec 2014 02:26:57 GMT\r\n");
		sbf.append("Server:Apache-Coyote/1.1\r\n");
		//sbf.append("Transfer-Encoding:chunked\r\n");//這裡分塊協議 可以研究一下
		sbf.append("Vary:Accept-Encoding\r\n");
		sbf.append("Content-Language: zh-CN\r\n");
		
		PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		GZIPOutputStream gos=new GZIPOutputStream(baos);
		gos.write(html.getBytes("UTF-8"));
		gos.flush();
		byte[] b=baos.toByteArray();
		gos.close();
		baos.close();
		sbf.append("Content-Length: "+html.length()+"\r\n");
		sbf.append("\r\n");
		sbf.append(new String(b,"UTF-8"));
		out.print(sbf.toString());
		out.flush();
		socket.close();
		System.out.println("over");
	}
	/**
	 * 
	 * 
	 * */
	public static void main(String[] args) throws IOException {
		ServerSocket ss=new ServerSocket(7890);
		while(true){
			Socket socket=ss.accept();
			write(socket,"<html><font color='red'>aaaaaaaaaaaa</font></html>");
		}
	}

	public static void main1(String[] args) throws UnknownHostException, IOException {
		Socket socket=new Socket();
		//socket.setSoTimeout(50); 
		socket.connect(new InetSocketAddress("10.9.37.130",7001));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		StringBuffer sb=new StringBuffer();
		sb.append("GET /console/login/LoginForm.jsp HTTP/1.1\r\n");
		sb.append("Host 10.9.37.130:7001\r\n");
		sb.append("Connection keep-alive\r\n");
        sb.append("Content-Type:application/x-www-form-urlencoded\r\n");
		sb.append("Cache-Control: max-age=0\r\n");
		sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,\\*/*;q=0.8\r\n");
		sb.append("User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36\r\n");
		sb.append("Accept-Encoding: gzip, deflate, sdch\r\n");
		sb.append("Accept-Language: zh-CN,zh;q=0.8\r\n");
		sb.append("Cookie: global_skin_style=gray\r\n");
		sb.append("\r\n");
		bw.write(sb.toString());
		bw.flush();
		String line = in.readLine();
		while((line)!=null && !"".equals(line)){
			System.out.println("" + line);
			line= in.readLine();
		}
		System.out.println("over");
		
	}
}
