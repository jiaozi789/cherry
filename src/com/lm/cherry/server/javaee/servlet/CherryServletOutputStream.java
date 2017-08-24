package com.lm.cherry.server.javaee.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import com.lm.cherry.protocal.http.Http11Protocal;
/**
 * �������outputstream��
 * ����:2015-5-25
 * ����:LM
 */
public class CherryServletOutputStream extends ServletOutputStream {
	private CherryHttpServletResponse reponse;
	private Http11Protocal http11;
	public CherryServletOutputStream(CherryHttpServletResponse reponse){
		this.reponse=reponse;
		this.http11=reponse.getHttp11();
	}
	/**
	 * д���ֽ�����
	 * ����:2015-5-25
	 * ����:LM
	 * @param paramInt
	 * @throws IOException
	 */
	@Override
	public void write(int paramInt) throws IOException {
		http11.getResponseContentByte().add(paramInt);
	}

}
