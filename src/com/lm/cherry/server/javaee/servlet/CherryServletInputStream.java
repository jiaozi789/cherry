package com.lm.cherry.server.javaee.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;

import com.lm.cherry.tool.SystemUtils;
/**
 * ��ȡ���ݵ���
 * ����:2015-5-23
 * ����:LM
 */
public class CherryServletInputStream extends ServletInputStream {
	private String content;
	private byte[] contentByte;
	private int offset=-1;
	public CherryServletInputStream(String content,String charset) throws UnsupportedEncodingException{
		this.content=content;
		if(SystemUtils.isEmpty(charset)){
			charset="UTF-8";
		}
		this.contentByte=this.content.getBytes(charset);
	}
	public CherryServletInputStream(byte[] content){
		this.contentByte=content;
	}
	/**
	 * ��ȡÿһ���ֽڵ�����
	 * ����:2015-5-23
	 * ����:LM
	 * @return
	 * @throws IOException
	 */
	@Override
	public int read() throws IOException {
		offset++;
		if(offset==contentByte.length){
			return -1;
		}
		return contentByte[offset];
	}

}
