package com.lm.cherry.server.error;

import java.io.IOException;
import java.net.Socket;
/**
 * 不同协议间如果出现了错误如果抛出
  *时间：2017-2-8 上午09:12:39
  *作者： LM
  *联系方式：973465719@qq.com
 *
 */
public interface SocketError {
	/**
	 * 抛出异常
	 *时间：2017-2-8 上午09:13:19
	 *作者： LM
	 *联系方式：973465719@qq.com
	 * @param socket
	 * @param status
	 * @param msg
	 * @throws IOException
	 */
	public void throwsError(Socket socket,Exception se) throws IOException;
}
