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
	 * ��ʼ��xml�ļ���λ��
	 */
	private static String xmlPath="conf/server.xml";
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
		String userDir=System.getProperty("user.dir");
		File file=new File(userDir+"/"+xmlPath);
		if(file.exists()){
			userDir=userDir.replaceAll("\\\\", "/");
			return userDir;
		}
		URL url=WorkConfig.class.getResource("/"+xmlPath);
		if(url==null){
			String jarPath=WorkConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			if(jarPath.startsWith("/")){
				jarPath=jarPath.substring(1);
			}
			String workPath=jarPath.substring(0,jarPath.lastIndexOf("/"));
			workPath=workPath.substring(0,workPath.lastIndexOf("/"));
			if(workPath==null){
				throw new Exception("δ�ҵ������ļ�");
			}else{
				return workPath;
			}
		}else{
			userDir=WorkConfig.class.getResource("/").toString();
			userDir=userDir.replaceAll("\\\\", "/");
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
				absPath=thisHome+"/"+xmlPath;
			}
			InputStream input=new FileInputStream(absPath);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			Document document = db.parse(input);
			NodeList nodeList=document.getElementsByTagName("server");
			if(nodeList.getLength()==0){
				throw new Exception("δ����server�ڵ�");
			}
			for(int i=0;i<nodeList.getLength();i++){
				Node node=nodeList.item(i);
				if(node.getNodeName().equals("server")){
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
		home.setName("CHERRY_HOME");
		home.setPath(getWorkConfPath());
		edir.getDir().add(home);
		edir.setPort(Integer.parseInt(node.getAttributes().getNamedItem("port").getNodeValue()));
		edir.setType(node.getAttributes().getNamedItem("type").getNodeValue());
		edir.setProtocol(node.getAttributes().getNamedItem("protocol").getNodeValue());
		NodeList nodeList=node.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++){
			Node node1=nodeList.item(i);
			Entry entry=new Entry();
			if(node1.getNodeName().equals("dir")){
				Entry.Dir ed=getDirDefine(node1);
				if(ed!=null)
					edir.getDir().add(ed);
			}
			if(node1.getNodeName().equals("thread")){
				Entry.Thread ed=getThreadDefind(node1);
				if(ed!=null)
					edir.setThread(ed);
			}
			if(node1.getNodeName().equals("log")){
				Entry.LogC ed=getLogDefine(node1);
				if(ed!=null)
					edir.setLog(ed);
			}
			if(node1.getNodeName().equals("app")){
				Entry.App ed=getAppDefind(node1);
				if(ed!=null)
					edir.getApps().add(ed);
			}
			if(node1.getNodeName().equals("j2eeapp")){
				Entry.J2EEApp ed=getJ2eeAppDefind(node1);
				if(ed!=null)
					edir.getJ2eeapps().add(ed);
			}
			if(node1.getNodeName().equals("backApp")){
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
		edir.setName(node.getAttributes().getNamedItem("name").getNodeValue());
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
		Node minNode=node.getAttributes().getNamedItem("blockMax");
		if(minNode!=null)
			ek.setBlockMax(Integer.parseInt(minNode.getNodeValue()));
		Node maxNode=node.getAttributes().getNamedItem("max");
		if(maxNode!=null)
			ek.setMax(Integer.parseInt(maxNode.getNodeValue()));
		
		Node sendTimeoutNode=node.getAttributes().getNamedItem("sendTimeout");
		if(sendTimeoutNode!=null)
			ek.setSendTimeout(Integer.parseInt(sendTimeoutNode.getNodeValue()));
		
		Node acceptTimeoutNode=node.getAttributes().getNamedItem("acceptTimeout");
		if(acceptTimeoutNode!=null)
			ek.setAcceptTimeout(Integer.parseInt(acceptTimeoutNode.getNodeValue()));
		
		Node retryCountNode=node.getAttributes().getNamedItem("retryCount");
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
