package com.lm.cherry.server.error;

import java.io.IOException;
import java.net.Socket;
/**
 * ��ͬЭ�����������˴�������׳�
  *ʱ�䣺2017-2-8 ����09:12:39
  *���ߣ� LM
  *��ϵ��ʽ��973465719@qq.com
 *
 */
public interface SocketError {
	/**
	 * �׳��쳣
	 *ʱ�䣺2017-2-8 ����09:13:19
	 *���ߣ� LM
	 *��ϵ��ʽ��973465719@qq.com
	 * @param socket
	 * @param status
	 * @param msg
	 * @throws IOException
	 */
	public void throwsError(Socket socket,Exception se) throws IOException;
}
