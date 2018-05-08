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
 * 解析配置文件的类
 * 作者:LM
 * 创建日期:2014-12-31下午04:10:30
 * 项目名称:cherry
 * 包名称:com.lm.cherry.work
 * 文件名:WorkConfig.java
 */
public class WorkConfig {
	/**
	 * server相关节点
	 */
	private static final String NODE_SERVER_PROTOCOL = "protocol";
	private static final String NODE_SERVER_TYPE = "type";
	private static final String NODE_SERVER_PORT = "port";
	/**
	 * 线程相关常量
	 */
	private static final String NODE_THREAD_RETRY_COUNT = "retryCount";
	private static final String NODE_THREAD_ACCEPT_TIMEOUT = "acceptTimeout";
	private static final String NODE_THREAD_SEND_TIMEOUT = "sendTimeout";
	private static final String NODE_THREAD_MAX = "max";
	private static final String NODE_THREAD_BLOCK_MAX = "blockMax";
	private static final String NODE_THREAD_NAME = "name";
	/**
	 * root配置相关常量
	 */
	private static final String NODE_BACK_APP = "backApp";
	private static final String NODE_J2EEAPP = "j2eeapp";
	private static final String NODE_APP = "app";
	private static final String NODE_LOG = "log";
	private static final String NODE_THREAD = "thread";
	private static final String NODE_DIR = "dir";
	private static final String CHERRY_HOME = "CHERRY_HOME";
	/**
	 * 配置文件的服务器节点
	 */
	private static final String NODE_SERVER = "server";
	/**
	 * 错误常量
	 */
	private static final String ERROR_NOT_DEF = "未定义server节点";
	private static final String ERROR_NOT_FOUND = "未找到配置文件";
	/**
	 * 路径标准化符号
	 */
	private static final String REGEX = "\\\\";
	/**
	 * 路径分隔符
	 */
	private static final String REPLACEMENT = "/";
	/**
	 * 用户操作目录
	 */
	private static final String USER_DIR = "user.dir";
	/**
	 * 初始化xml文件的位置
	 */
	private static final String xmlPath="conf/server.xml";
	/**
	 * 获取静态网页的app目录
	 */
	public static List<Entry> entry=new ArrayList<Entry>();
	private static boolean ifInit=false;
	/**
	 * 获取当前的程序的工作目录 也就是找到xmlpath路径的根目录
	 * 作者:LM
	 * 创建日期:2014-12-31下午04:09:56
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
	 * 首先读取根目录 然后没有读取到 再读取加载类的根目录 是否存在conf/init.xml文件
	 * 作者:LM
	 * 创建日期:2014-12-31下午03:59:49
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
	 * 解析所有的目录命名
	 * 作者:LM
	 * 创建日期:2015-1-4下午04:13:11
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
	 * 解析所有的目录命名
	 * 作者:LM
	 * 创建日期:2015-1-4下午04:13:11
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
	 * 解析日志的配置
	 * 作者:LM
	 * 创建日期:2015-1-4下午04:13:11
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
	 * 获取线程的配置
	 * 作者:LM
	 * 创建日期:2015-1-4下午04:15:41
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
	 * 获取静态网页代理的配置
	 * 作者:LM
	 * 创建日期:2015-1-4下午04:15:41
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
	 * 获取静态网页代理的配置
	 * 作者:LM
	 * 创建日期:2015-1-4下午04:15:41
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
	 * 获取后台转发配置
	 * 作者:LM
	 * 创建日期:2015-1-4下午04:15:41
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
	 * 单元测试main
	 * 作者:LM
	 * 创建日期:2015-1-4下午05:20:31
	 * @param args
	 */
	public static void main(String[] args) {
		WorkConfig wk=new WorkConfig();
		wk.loadInitConifg(null);
	}
}
