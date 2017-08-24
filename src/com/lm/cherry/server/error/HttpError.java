package com.lm.cherry.server.error;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.lm.cherry.protocal.http.Http11Protocal;
/**
 * HttpЭ����쳣�����
  *ʱ�䣺2017-2-8 ����09:18:54
  *���ߣ� LM
  *��ϵ��ʽ��973465719@qq.com
 *
 */
public class HttpError implements SocketError{
	public static final String ERROR_403="�������ܾ��˸�����";
	public static final String ERROR_404="�Ҳ��������·��";
	public static final String ERROR_500="�ڲ������������쳣";
	/**
	 * д�����socket��
	 */
	@Override
	public void throwsError(Socket socket, Exception se)
			throws IOException {
		StringBuffer sbf=new StringBuffer();
		OutputStream os=socket.getOutputStream();
		String status=se.getStatus();
		String msg=se.getMsg();
		byte[] contentByte=msg.getBytes(Http11Protocal.DEFAULT_ENCODING);
		sbf.append(Http11Protocal.statusCode.replaceAll("%\\{STATUS\\}%", status).replaceAll("%\\{STATUSTEXT\\}%", Http11Protocal.statusMap.get(status).toString()));
		sbf.append(Http11Protocal.contentLength.replaceAll("%\\{CONTENTLENGTH\\}%", contentByte.length+""));
		sbf.append(Http11Protocal.contentType.replaceAll("%\\{CONTENTTYPE\\}%", "text/html;charset=UTF-8"));
		sbf.append(Http11Protocal.connection.replaceAll("%\\{CONNECTION\\}%", "close"));
		sbf.append("\r\n");
		sbf.append(msg);
		os.write(sbf.toString().getBytes(Http11Protocal.DEFAULT_ENCODING));
		os.flush();
		socket.close();
	}
	/**
	 * ֱ���׳�404 
	 *ʱ�䣺2017-2-8 ����09:38:11
	 *���ߣ� LM
	 *��ϵ��ʽ��973465719@qq.com
	 * @param socket
	 * @param msg
	 * @throws IOException
	 */
	public void throws404Error(Socket socket) throws IOException{
		com.lm.cherry.server.error.Exception exe=new com.lm.cherry.server.error.Exception();
		exe.setStatus("404");
		exe.setMsg(ERROR_404);
		this.throwsError(socket, exe);
	}
	/**
	 * ֱ���׳�500
	 *ʱ�䣺2017-2-8 ����09:38:11
	 *���ߣ� LM
	 *��ϵ��ʽ��973465719@qq.com
	 * @param socket
	 * @param msg
	 * @throws IOException
	 */
	public void throws500Error(Socket socket,String appendMsg) throws IOException{
		com.lm.cherry.server.error.Exception exe=new com.lm.cherry.server.error.Exception();
		exe.setStatus("500");
		exe.setMsg(ERROR_500+appendMsg);
		this.throwsError(socket, exe);
	}
	/**
	 * ֱ���׳�403
	 *ʱ�䣺2017-2-8 ����09:38:11
	 *���ߣ� LM
	 *��ϵ��ʽ��973465719@qq.com
	 * @param socket
	 * @param msg
	 * @throws IOException
	 */
	public void throws403Error(Socket socket) throws IOException{
		com.lm.cherry.server.error.Exception exe=new com.lm.cherry.server.error.Exception();
		exe.setStatus("500");
		exe.setMsg(ERROR_403);
		this.throwsError(socket, exe);
	}
}
