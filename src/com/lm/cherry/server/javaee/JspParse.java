package com.lm.cherry.server.javaee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Vector;

import com.lm.cherry.tool.FileUtils;
/**
 * ���ڽ���JSP�ļ��ĵ���
 * @author Administrator
 */
public class JspParse {
	/**
	 * �����ĵ�Ŀ¼
	 */
	private String contextDir;
	/**
	 * JSP�ļ�
	 */
	private String jspFile;
	/**
	 * ����ļ���Ŀ¼
	 */
	private String outDir;
	/**
	 * �ĵ������ı���
	 */
	private int offset;
	/**
	 * ��ȡ���ļ�������
	 */
	private String content=null;
	/**
	 * ��ǰ���ڽ������ַ���
	 */
	private String curStr=null;
	/**
	 * ���ɵ�java�ļ�
	 */
	private StringBuffer responseJava=new StringBuffer();
	/**
	 * ������Page��ǩ����������
	 */
	private Vector imports;
	private String contentType = null;
	private String session;
	private int buffer = 8*1024;
	private String autoFlush;
	private String errorPage = null;
	private boolean isErrorPage = false;
	private String pageExtends;//page��ǩ�ϵ�extends
	private boolean isThreadSafe = true;
	private String info;
	private String pageEncoding;
	public JspParse(String contextDir,String jspFile,String outDir){
		this.contextDir=contextDir;
		this.jspFile=jspFile;
		this.outDir=outDir;
		offset=0;
	}
	/**
	 * ����java�ļ�
	 * ����:2015-5-29
	 * ����:LM
	 * @throws FileNotFoundException 
	 */
	public void genernateJava() throws Exception{
		File jspObj=new File(jspFile);
		String content=FileUtils.getContent(jspObj);
		this.content=content;
		responseJava.append("import javax.servlet.*;\r\n");
		responseJava.append("import javax.servlet.http.*;\r\n");
		responseJava.append("import javax.servlet.jsp.*;\r\n");
		String fileName=jspFile.split("\\.")[0];
		String javaName="cherry"+fileName.substring(0,1).toUpperCase()+fileName.substring(1)+"Jsp";
		responseJava.append("import javax.servlet.jsp.*;\r\n");
		scanIndex(0);
	}
	public static void main(String[] args) throws Exception {
		JspParse jp=new JspParse("E:\\workspace\\tomcat\\webapps\\test\\index.jsp","E:\\workspace\\tomcat\\webapps\\test\\index.jsp","E:\\workspace\\tomcat\\webapps\\test");
		jp.genernateJava();
	}
	/**
	 * ���ؽ���������ַ�������
	 * ����:2015-6-12
	 * ����:LM
	 * @param idx
	 * @return
	 */
	public int scanPage(int idx){
		String nextContext=this.content.substring(idx);
		int scaIdx=nextContext.indexOf("%>");
		String contentPage=nextContext.substring(0,scaIdx);
		contentPage=contentPage.trim().replaceAll(" {1,}", " ");
		String[] psplit=contentPage.split(" ");
		for(String tmppsplit:psplit){
			if(tmppsplit.indexOf("=")>0){
				String name=tmppsplit.split("=")[0];
				String value=tmppsplit.split("=")[1];
				if("import".equals(name)){
					value=value.replaceAll("\"", "");
					String[] importPkg=value.split(",");
					imports.addAll(Arrays.asList(importPkg));
				}
				if("pageEncoding".equals(name)){
					this.pageEncoding=value.replaceAll("\"", "");;
				}
				if("contentType".equals(name)){
					contentType=value.replaceAll("\"", "");;
				}
				if("session".equals(name)){
					session=value.replaceAll("\"", "");;
				}
				if("buffer".equals(name)){
					buffer=Integer.parseInt(value.replaceAll("\"", ""));
				}
				if("autoFlush".equals(name)){
					autoFlush=value.replaceAll("\"", "");;
				}
				if("errorPage".equals(name)){
					errorPage=value.replaceAll("\"", "");;
				}
				if("isErrorPage".equals(name)){
					isErrorPage=Boolean.parseBoolean(value.replaceAll("\"", ""));
				}
				if("extends".equals(name)){
					pageExtends=value.replaceAll("\"", "");;
				}
				if("isThreadSafe".equals(name)){
					isThreadSafe=Boolean.parseBoolean(value.replaceAll("\"", ""));
				}
				if("info".equals(name)){
					info=value.replaceAll("\"", "");;
				}
				if("pageEncoding".equals(name)){
					pageEncoding=value.replaceAll("\"", "");;
				}
			}
		}
		return idx+scaIdx+2;
	}
	public int scanExpression(int idx){
		return 0;
	}
	/**
	 * ��ĳ���㿪ʼ�����ĵ�
	 * ����:2015-6-12
	 * ����:LM
	 * @param idx
	 */
	public void scanIndex(int idx){
		char chr=this.content.charAt(idx);
		//�����<��ͷ�� ��Ҫ�����Ƿ��� <%��ı��ʽ
		if(chr=='<'){
			if(idx+1<this.content.length()){
				char nextChr=this.content.charAt(idx+1);
				if(nextChr=='%'){
					if(this.content.charAt(idx+2)=='@'){
						int nextIndex=scanPage(idx);
						if(nextIndex<=this.content.length())
							scanIndex(nextIndex);
					}
					//������������ľ��Ǳ��ʽ
					if(this.content.charAt(idx+2)!='@'){
						int nextIndex=scanExpression(idx);
						if(nextIndex<=this.content.length())
							scanIndex(nextIndex);
					}
				}
			}
		}
	}
	
	
}
