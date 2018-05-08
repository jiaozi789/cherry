package com.lm.cherry.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lm.cherry.tool.SystemUtils;
/**
 * ���������ļ�����
 * ����:LM
 * ��������:2014-12-31����04:10:30
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.work
 * �ļ���:WorkConfig.java
 */
public class WorkConfig {
	/**
	 * server��ؽڵ�
	 */
	private static final String NODE_SERVER_PROTOCOL = "protocol";
	private static final String NODE_SERVER_TYPE = "type";
	private static final String NODE_SERVER_PORT = "port";
	/**
	 * �߳���س���
	 */
	private static final String NODE_THREAD_RETRY_COUNT = "retryCount";
	private static final String NODE_THREAD_ACCEPT_TIMEOUT = "acceptTimeout";
	private static final String NODE_THREAD_SEND_TIMEOUT = "sendTimeout";
	private static final String NODE_THREAD_MAX = "max";
	private static final String NODE_THREAD_BLOCK_MAX = "blockMax";
	private static final String NODE_THREAD_NAME = "name";
	/**
	 * root������س���
	 */
	private static final String NODE_BACK_APP = "backApp";
	private static final String NODE_J2EEAPP = "j2eeapp";
	private static final String NODE_APP = "app";
	private static final String NODE_LOG = "log";
	private static final String NODE_THREAD = "thread";
	private static final String NODE_DIR = "dir";
	private static final String CHERRY_HOME = "CHERRY_HOME";
	/**
	 * �����ļ��ķ������ڵ�
	 */
	private static final String NODE_SERVER = "server";
	/**
	 * ������
	 */
	private static final String ERROR_NOT_DEF = "δ����server�ڵ�";
	private static final String ERROR_NOT_FOUND = "δ�ҵ������ļ�";
	/**
	 * ·����׼������
	 */
	private static final String REGEX = "\\\\";
	/**
	 * ·���ָ���
	 */
	private static final String REPLACEMENT = "/";
	/**
	 * �û�����Ŀ¼
	 */
	private static final String USER_DIR = "user.dir";
	/**
	 * ��ʼ��xml�ļ���λ��
	 */
	private static final String xmlPath="conf/server.xml";
	/**
	 * ��ȡ��̬��ҳ��appĿ¼
	 */
	public static List<Entry> entry=new ArrayList<Entry>();
	private static boolean ifInit=false;
	/**
	 * ��ȡ��ǰ�ĳ���Ĺ���Ŀ¼ Ҳ�����ҵ�xmlpath·���ĸ�Ŀ¼
	 * ����:LM
	 * ��������:2014-12-31����04:09:56
	 * @return
	 * @throws Exception
	 */
	public static String getWorkConfPath() throws Exception{
		String userDir=System.getProperty(USER_DIR);
		File file=new File(userDir+REPLACEMENT+xmlPath);
		if(file.exists()){
			userDir=userDir.replaceAll(REGEX, REPLACEMENT);
			return userDir;
		}
		URL url=WorkConfig.class.getResource(REPLACEMENT+xmlPath);
		if(url==null){
			String jarPath=WorkConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			if(jarPath.startsWith(REPLACEMENT)){
				jarPath=jarPath.substring(1);
			}
			String workPath=jarPath.substring(0,jarPath.lastIndexOf(REPLACEMENT));
			workPath=workPath.substring(0,workPath.lastIndexOf(REPLACEMENT));
			if(workPath==null){
				throw new Exception(ERROR_NOT_FOUND);
			}else{
				return workPath;
			}
		}else{
			userDir=WorkConfig.class.getResource(REPLACEMENT).toString();
			userDir=userDir.replaceAll(REGEX, REPLACEMENT);
			return userDir;
		}
	}
	/**
	 * ���ȶ�ȡ��Ŀ¼ Ȼ��û�ж�ȡ�� �ٶ�ȡ������ĸ�Ŀ¼ �Ƿ����conf/init.xml�ļ�
	 * ����:LM
	 * ��������:2014-12-31����03:59:49
	 */
	public  void loadInitConifg(String path){
		try {
			if(WorkConfig.ifInit)return;
			WorkConfig.ifInit=true;
			String thisHome=getWorkConfPath();
			String absPath=path;
			if(SystemUtils.isEmpty(path)){
				absPath=thisHome+REPLACEMENT+xmlPath;
			}
			InputStream input=new FileInputStream(absPath);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			Document document = db.parse(input);
			NodeList nodeList=document.getElementsByTagName(NODE_SERVER);
			if(nodeList.getLength()==0){
				throw new Exception(ERROR_NOT_DEF);
			}
			for(int i=0;i<nodeList.getLength();i++){
				Node node=nodeList.item(i);
				if(node.getNodeName().equals(NODE_SERVER)){
					entry.add(getServerDefine(node));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * �������е�Ŀ¼����
	 * ����:LM
	 * ��������:2015-1-4����04:13:11
	 * @param document
	 * @return
	 * @throws Exception 
	 */
	public static Entry getServerDefine(Node node) throws Exception{
		Entry edir=new Entry();
		Entry.Dir home=new Entry.Dir();
		home.setName(CHERRY_HOME);
		home.setPath(getWorkConfPath());
		edir.getDir().add(home);
		edir.setPort(Integer.parseInt(node.getAttributes().getNamedItem(NODE_SERVER_PORT).getNodeValue()));
		edir.setType(node.getAttributes().getNamedItem(NODE_SERVER_TYPE).getNodeValue());
		edir.setProtocol(node.getAttributes().getNamedItem(NODE_SERVER_PROTOCOL).getNodeValue());
		NodeList nodeList=node.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++){
			Node node1=nodeList.item(i);
			Entry entry=new Entry();
			if(node1.getNodeName().equals(NODE_DIR)){
				Entry.Dir ed=getDirDefine(node1);
				if(ed!=null)
					edir.getDir().add(ed);
			}
			if(node1.getNodeName().equals(NODE_THREAD)){
				Entry.Thread ed=getThreadDefind(node1);
				if(ed!=null)
					edir.setThread(ed);
			}
			if(node1.getNodeName().equals(NODE_LOG)){
				Entry.LogC ed=getLogDefine(node1);
				if(ed!=null)
					edir.setLog(ed);
			}
			if(node1.getNodeName().equals(NODE_APP)){
				Entry.App ed=getAppDefind(node1);
				if(ed!=null)
					edir.getApps().add(ed);
			}
			if(node1.getNodeName().equals(NODE_J2EEAPP)){
				Entry.J2EEApp ed=getJ2eeAppDefind(node1);
				if(ed!=null)
					edir.getJ2eeapps().add(ed);
			}
			if(node1.getNodeName().equals(NODE_BACK_APP)){
				Entry.BackApp ed=getBackAppDefind(node1);
				if(ed!=null)
					edir.getBapps().add(ed);
			}
		}
		
		return edir;
	}
	/**
	 * �������е�Ŀ¼����
	 * ����:LM
	 * ��������:2015-1-4����04:13:11
	 * @param document
	 * @return
	 */
	public static Entry.Dir getDirDefine(Node node){
		Entry.Dir edir=new Entry.Dir();
		edir.setName(node.getAttributes().getNamedItem(NODE_THREAD_NAME).getNodeValue());
		edir.setPath(node.getFirstChild().getNodeValue());
		return edir;
	}
	/**
	 * ������־������
	 * ����:LM
	 * ��������:2015-1-4����04:13:11
	 * @param document
	 * @return
	 */
	public static Entry.LogC getLogDefine(Node node) throws Exception{
		Entry.LogC ek=new Entry.LogC();
		NamedNodeMap nnm=node.getAttributes();
		int length=nnm.getLength();
		for(int i=0;i<length;i++){
			Field fs=ek.getClass().getDeclaredField(nnm.item(i).getNodeName());
			fs.setAccessible(true);
			fs.set(ek, nnm.item(i).getNodeValue());
		}
		return ek;
	}
	/**
	 * ��ȡ�̵߳�����
	 * ����:LM
	 * ��������:2015-1-4����04:15:41
	 * @param document
	 * @return
	 */
	public static Entry.Thread getThreadDefind(Node node){
		Entry.Thread ek=new Entry.Thread();
		Node minNode=node.getAttributes().getNamedItem(NODE_THREAD_BLOCK_MAX);
		if(minNode!=null)
			ek.setBlockMax(Integer.parseInt(minNode.getNodeValue()));
		Node maxNode=node.getAttributes().getNamedItem(NODE_THREAD_MAX);
		if(maxNode!=null)
			ek.setMax(Integer.parseInt(maxNode.getNodeValue()));
		
		Node sendTimeoutNode=node.getAttributes().getNamedItem(NODE_THREAD_SEND_TIMEOUT);
		if(sendTimeoutNode!=null)
			ek.setSendTimeout(Integer.parseInt(sendTimeoutNode.getNodeValue()));
		
		Node acceptTimeoutNode=node.getAttributes().getNamedItem(NODE_THREAD_ACCEPT_TIMEOUT);
		if(acceptTimeoutNode!=null)
			ek.setAcceptTimeout(Integer.parseInt(acceptTimeoutNode.getNodeValue()));
		
		Node retryCountNode=node.getAttributes().getNamedItem(NODE_THREAD_RETRY_COUNT);
		if(retryCountNode!=null)
			ek.setRetryCount(Integer.parseInt(retryCountNode.getNodeValue()));
		return ek;
	}
	/**
	 * ��ȡ��̬��ҳ���������
	 * ����:LM
	 * ��������:2015-1-4����04:15:41
	 * @param document
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static Entry.App getAppDefind(Node node) throws Exception{
		Entry.App ek=new Entry.App();
		NamedNodeMap nnm=node.getAttributes();
		int length=nnm.getLength();
		for(int i=0;i<length;i++){
			Field fs=ek.getClass().getDeclaredField(nnm.item(i).getNodeName());
			fs.setAccessible(true);
			fs.set(ek, nnm.item(i).getNodeValue());
		}
		return ek;
	}
	/**
	 * ��ȡ��̬��ҳ���������
	 * ����:LM
	 * ��������:2015-1-4����04:15:41
	 * @param document
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static Entry.J2EEApp getJ2eeAppDefind(Node node) throws Exception{
		Entry.J2EEApp ek=new Entry.J2EEApp();
		NamedNodeMap nnm=node.getAttributes();
		int length=nnm.getLength();
		for(int i=0;i<length;i++){
			Field fs=ek.getClass().getDeclaredField(nnm.item(i).getNodeName());
			fs.setAccessible(true);
			fs.set(ek, nnm.item(i).getNodeValue());
		}
		return ek;
	}
	/**
	 * ��ȡ��̨ת������
	 * ����:LM
	 * ��������:2015-1-4����04:15:41
	 * @param document
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static Entry.BackApp getBackAppDefind(Node node) throws Exception{
		Entry.BackApp ek=new Entry.BackApp();
		NamedNodeMap nnm=node.getAttributes();
		int length=nnm.getLength();
		for(int i=0;i<length;i++){
			Field fs=ek.getClass().getDeclaredField(nnm.item(i).getNodeName());
			fs.setAccessible(true);
			fs.set(ek, nnm.item(i).getNodeValue());
		}
		return ek;
	}
	/**
	 * ��Ԫ����main
	 * ����:LM
	 * ��������:2015-1-4����05:20:31
	 * @param args
	 */
	public static void main(String[] args) {
		WorkConfig wk=new WorkConfig();
		wk.loadInitConifg(null);
	}
}
