package com.lm.cherry.server;

import com.lm.cherry.protocal.http.Http11Protocal;
/**
 * ������Ӧ�� ��Ӧ�Ľӿ�
 *  ����:LM
 * ��������:2015-1-30����04:11:19
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.server
 * �ļ���:Response.java
 *
 */
public interface Response {
	/**
	 * ������Ӧ  ���뵱ǰ����Э����Ϣ
	 * @param httpProtocal
	 * @throws Exception
	 */
	public void processResponse(Http11Protocal httpProtocal)throws Exception;
}
