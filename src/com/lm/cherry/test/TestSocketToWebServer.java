package com.lm.cherry.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class TestSocketToWebServer {
	public static String readInput(InputStream input) {
		StringBuffer sb = new StringBuffer();
		char c = 0;
		byte b = -1;
		try {
			while ((b = (byte) input.read()) != -1) {
				c = (char) b;
				if (c == '\r') {
					input.read(); // 再读取一个\n
					break;
				} else if (c == '\n') {
					break;
				} else {
					sb.append(c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	public static void readHtml() throws UnknownHostException, IOException {
		String host = "localhost";
		int port = 8080;
		Socket socket = new Socket(host, port);

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket
				.getOutputStream()));
		BufferedReader in = new BufferedReader(new InputStreamReader(socket
				.getInputStream(), "UTF-8"));

		StringBuffer sb = new StringBuffer();
		// 请求的连接地址
		sb.append("GET /mdm/login.htm HTTP/1.1\r\n").append(
				"Host:" + host + "\r\n");
		sb.append("Accept-Encoding:gzip");
		sb.append("Connection: Keep-Alive\r\n");
		sb.append("\r\n"); // 内容

		out.write(sb.toString());
		out.flush();
		socket.shutdownOutput();
		// String str=readInput(socket
		// .getInputStream());
		// System.out.println(str);
		StringBuffer sbf = new StringBuffer();
		// 打印响应
		String line = "";
		int contentLength = 0;
		int totalLength = 0;
		boolean firstBlank = false;
		boolean ifDeal = false;
		// while ((line = in.readLine()) != null)
		// {
		// sbf.append(line);
		// System.out.println(line);
		// }
		//		
		while ((line = in.readLine()) != null
				&& (totalLength == 0 || contentLength == 0 || totalLength < contentLength)) {
			sbf.append(line);
			System.out.println(line);
			if (!firstBlank && "".equals(line)) {
				firstBlank = true;
			}
			if (firstBlank) {
				// if(ifDeal){
				totalLength += (line + "\r\n").getBytes("UTF-8").length;
				// }else{
				// ifDeal=true;
				// }
			}
			if (line.indexOf("Content-Length") >= 0) {
				contentLength = Integer.parseInt(line.split(":")[1].trim());
			}
			//System.out.println(contentLength + "--->" + totalLength);
		}
		out.close();
		in.close();
		socket.close();
		// System.out.println(sbf);
	}
	public static void readByte() throws UnknownHostException, IOException {
		String host = "10.9.37.130";
		int port = 7082;
		Socket socket = new Socket(host, port);

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket
				.getOutputStream()));


		StringBuffer sb = new StringBuffer();
		// 请求的连接地址
		sb.append("GET /icloud//commons/commons.js HTTP/1.1\r\n").append(
				"Host:" + host + "\r\n");
		sb.append("Connection: Keep-Alive\r\n");
		sb.append("\r\n"); // 内容

		out.write(sb.toString());
		out.flush();
		//socket.shutdownOutput();
		InputStream in = socket
		.getInputStream();
		// 打印响应
		int n=0;
		byte[] bt=new byte[2048];
		ByteArrayOutputStream ba=new ByteArrayOutputStream();
		while((n=in.read(bt))!=-1){
			ba.write(bt,0,n);
		}
		System.out.println(new String(ba.toByteArray(),"UTF-8"));
		out.close();
		in.close();
		socket.close();
		// System.out.println(sbf);
	}
	public static void readImage() throws UnknownHostException, IOException {
		String host = "localhost";
		int port = 8080;
		Socket socket = new Socket(host, port);

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket
				.getOutputStream()));
		InputStream is = socket.getInputStream();

		StringBuffer sb = new StringBuffer();
		// 请求的连接地址
		sb
				.append(
						"GET /mdm/imp.gif HTTP/1.1\r\n")
				.append(
						"Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n")
				.append("Accept-Encoding:gzip, deflate, sdch\r\n")
				.append("Accept-Language:zh-CN,zh;q=0.8\r\n")
				.append("Cache-Control:max-age=0\r\n")
				.append("Connection:keep-alive\r\n")
				.append("Host:10.9.37.130:7082\r\n")
				.append(
						"User-Agent:Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36\r\n")

				.append("\r\n"); // 内容

		out.write(sb.toString());
		out.flush();
		socket.shutdownOutput();
		// 打印响应
		String line = "";
		byte[] bt = new byte[1024];
		int n = 0;
		FileOutputStream fos = new FileOutputStream("c:/a.jpg");
		while ((n = is.read(bt)) != -1) {
			fos.write(bt, 0, n);
		}
		fos.close();
		out.close();
		is.close();
		socket.close();
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		readImage();
	}

}
