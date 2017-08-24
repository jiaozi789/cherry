package com.lm.cherry.server.javaee.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import com.lm.cherry.protocal.http.Http11Protocal;
/**
 * 输出流到outputstream中
 * 日期:2015-5-25
 * 作者:LM
 */
public class CherryServletOutputStream extends ServletOutputStream {
	private CherryHttpServletResponse reponse;
	private Http11Protocal http11;
	public CherryServletOutputStream(CherryHttpServletResponse reponse){
		this.reponse=reponse;
		this.http11=reponse.getHttp11();
	}
	/**
	 * 写入字节流到
	 * 日期:2015-5-25
	 * 作者:LM
	 * @param paramInt
	 * @throws IOException
	 */
	@Override
	public void write(int paramInt) throws IOException {
		http11.getResponseContentByte().add(paramInt);
	}

}
