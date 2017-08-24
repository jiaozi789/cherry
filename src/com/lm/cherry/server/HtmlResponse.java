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
 * ����html�������Ӧ
 * ����:LM
 * ��������:2015-1-30����04:11:19
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.server
 * �ļ���:HtmlResponse.java
 */
public class HtmlResponse implements Response{
	public void processResponse(Http11Protocal httpProtocal) throws IOException {
		this.processResponse(httpProtocal,null);
	}
	/**
	 * ����html httpЭ�����Ӧ
	 * ����:LM
	 * ��������:2015-1-30����04:11:35
	 * @param httpProtocal
	 * @throws IOException
	 */
	public void processResponse(Http11Protocal httpProtocal,String rurl) throws IOException {
		//��ȡ�ͻ�������socket�������
		OutputStream out = httpProtocal.getCurProcessThread().getSocket().getOutputStream();
		//��ȡ�����url
		String url=(rurl!=null?rurl:httpProtocal.getRequestUrlNoParam());
		boolean ifSocketKeepAlive=httpProtocal.ifSupportKeepLive();
		//ͨ�������url���ʵ�ʵ��ļ�
		String file=httpProtocal.getCurProcessThread().getThreadPool().getHttpServer().getDeployFactory().getRequestFile(url);
		File destFile=new File(file);
		if(destFile.exists() && destFile.isDirectory()){
			String refererPage=httpProtocal.getRequestHead().get("Referer");
			if(SystemUtils.isNotEmpty(refererPage)){
				file=httpProtocal.getCurProcessThread().getThreadPool().getHttpServer().getDeployFactory().getRequestFile(refererPage);
			}
		}
		//�����ʵ��ļ�����������
		FileInputStream findFile=new FileInputStream(file);
		//��ȡ����ͨ��httpͷ��Ϣ ����������ļ����� ������ ���ڵ���Ϣ ���ie��ͼƬ�����͵�context-type�����ö� ������ʾͼƬ
		Object objEj=DeployProcessFactory.searchApp(url);
		String contextType=null;
		if(objEj!=null && objEj instanceof Entry.J2EEApp){
			Entry.J2EEApp ej=(Entry.J2EEApp)objEj;
			contextType=ContextTypeMap.getFileType(file,ej.getJ2eeLoder().getWebParse().getMimeMapping());
		}else{
			contextType=ContextTypeMap.getFileType(file);
		}
		String reponserText=httpProtocal.generaHeader(contextType);
		//д��Ĭ�ϱ������ͨͷ��Ϣ
		out.write(reponserText.getBytes(Http11Protocal.DEFAULT_ENCODING));
		//���ﳤ�ȱ�������(������ָ ����httpͷ��һ�����к�� ������ļ����ֽ���) ��Ȼ������������� ����������
		out.write((Http11Protocal.contentLength.replaceAll("%\\{CONTENTLENGTH\\}%", findFile.available()+"")).getBytes(Http11Protocal.DEFAULT_ENCODING));
		//��������� ���Ǹ�������  ������Ը�socket������������
		if(ifSocketKeepAlive){
			out.write("Connection: keep-alive\r\n".getBytes(Http11Protocal.DEFAULT_ENCODING));
		}else{
			out.write("Connection: close\r\n".getBytes(Http11Protocal.DEFAULT_ENCODING));
		}
		//���һ��\r\n��ʾ \r\n������������������
		out.write(Http11Protocal.headerNext.getBytes(Http11Protocal.DEFAULT_ENCODING));
		//����д�������ļ�����ʵ���� �������ҳ������ҳ���� �����ͼƬ����ͼƬ���� ʹ��2������
		byte[] bt=new byte[500000];
		int n=0;
		while((n=findFile.read(bt))!=-1){
			out.write(bt,0,n);
		}
		//out.write("\r\n".getBytes(Http11Protocal.DEFAULT_ENCODING));//����ս��
		//�����flush���ͻ���
		out.flush();
		//�ж�������ǳ����ӣ��������Ƿ��������Ƶ� �ͻ����������Connection: keep-alive ֻ�Ǳ�ʾ�ͻ���֧�ֳ�����
		//������response���Connection: keep-alive �ű�ʾ֪ͨ�ͻ����Ƿ�Ҫ���泤���ӣ� �͹رո�socket
		if(!ifSocketKeepAlive){
			out.close();
			httpProtocal.getCurProcessThread().clear();
		}		
	}

}
