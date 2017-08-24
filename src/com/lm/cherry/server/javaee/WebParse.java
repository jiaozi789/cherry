package com.lm.cherry.server.javaee;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * ���ڽ���web.xml����
 * @author Administrator
 */
public class WebParse {
	/**
	 * ���е�servlet�Ľ���
	 */
	private Map<String,Map> servlets=new HashMap<String,Map>();
	/**
	 * ���е�filter�Ľ���
	 */
	private Map<String,Map> filters=new HashMap<String,Map>();
	/**
	 * ���еļ�����
	 */
	private List<String> listeners=new ArrayList<String>();
	/**
	 * ���е�contextParam
	 */
	private Map<String,String> contextParam=new HashMap<String,String>();
	/**
	 * ������չ����Ӧ��mini����
	 */
	private List<Map<String,String>> mimeMapping=new ArrayList<Map<String,String>>();
	/**
	 * �쳣ӳ��
	 */
	private List<Map<String,String>> errorPage=new ArrayList<Map<String,String>>();
	/**
	 * Ĭ�ϵ�ҳ��
	 */
	private List<String> welcomeFileList=new ArrayList<String>();
	/**
	 * ��Ŀ�� contextPath ���� /aaa 
	 */
	private String contextPath=null;
	/**
	 * ��ǰ������Ŀ��webroot�ľ���·�� ���� c:/aaa
	 */
	private String contextDir=null;
	/**
	 * session��ʱʱ��Ĭ��Ϊ 30����
	 */
	private int sessionTimeOut=30;
	/**
	 * ���췽�� ֱ�ӽ���xml�ļ�
	 * @param webRoot
	 * @throws Exception
	 */
	public WebParse(String contextDir,String contextPath) throws Exception{
		this.contextDir=contextDir;
		this.contextPath=contextPath;
		String webXmlPath=contextDir+"/WEB-INF/web.xml";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			FileInputStream is=new FileInputStream(webXmlPath);
			Document document = db.parse(is);
			getServlet(document);
			getServletMapping(document);
			getFilter(document);
			getFilterMapping(document);
			getContextParam(document);
			getListeners(document);
			getSessionConfig(document);
			getMiniType(document);
			getErrorInfo(document);
			getWelcome(document);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * ������ʼ��context-param������
	 * ����:2015-5-19
	 * ����:LM
	 * @param document
	 */
	public void getContextParam(Document document){
		NodeList nl=document.getElementsByTagName("context-param");
		if(nl.getLength()>0){
			for(int i=0;i<nl.getLength();i++){
				Element initParam=(Element)nl.item(i);
				NodeList paramName=initParam.getElementsByTagName("param-name");
				NodeList paramValue=initParam.getElementsByTagName("param-value");
				Map<String,String> initMap=new HashMap<String,String>();
				for(int k=0;k<paramName.getLength();k++){
					contextParam.put(paramName.item(k).getTextContent().trim(), paramValue.item(k).getTextContent().trim());
				}
			}
		}
		
	}
	/**
	 * �������е�servlet��Ԫ��
	 * ����:2015-5-19
	 * ����:LM
	 * @param document
	 */
	public void getServlet(Document document){
		NodeList nodeList=document.getElementsByTagName("servlet");
		for(int i=0;i<nodeList.getLength();i++){
			Map servlet=new HashMap();
			Node node=nodeList.item(i);
			String servletName=((Element)node).getElementsByTagName("servlet-name").item(0).getTextContent().trim();
			String servletClass=((Element)node).getElementsByTagName("servlet-class").item(0).getTextContent().trim();
			NodeList loadOnEle=((Element)node).getElementsByTagName("load-on-startup");
			String loadOnStart=loadOnEle.getLength()>0?loadOnEle.item(0).getTextContent().trim():"0";
			NodeList initParamEle=((Element)node).getElementsByTagName("init-param");
			
			if(servlets.containsKey(servletName)){
				servlet=servlets.get(servletName);
			}
			servlet.put("servlet-name", servletName);
			servlet.put("servlet-class", servletClass);
			servlet.put("load-on-startup", loadOnStart);
			servlet.put("servlet-name", servletName);
			if(initParamEle.getLength()>0){
				Element initParam=(Element)initParamEle.item(0);
				NodeList paramName=initParam.getElementsByTagName("param-name");
				NodeList paramValue=initParam.getElementsByTagName("param-value");
				Map initMap=new HashMap();
				for(int k=0;k<paramName.getLength();k++){
					initMap.put(paramName.item(k).getTextContent(), paramValue.item(k).getTextContent());
				}
				servlet.put("init-param", initMap);
			}
			servlets.put(servletName, servlet);
		}
	}
	/**
	 * ����servlet-mapping��Ԫ��
	 * ����:2015-5-19
	 * ����:LM
	 * @param document
	 */
	public void getServletMapping(Document document){
		NodeList nodeList=document.getElementsByTagName("servlet-mapping");
		for(int i=0;i<nodeList.getLength();i++){
			Map servlet=new HashMap();
			Node node=nodeList.item(i);
			NodeList childList=node.getChildNodes();
			String servletName=((Element)node).getElementsByTagName("servlet-name").item(0).getTextContent().trim();
			if(servlets.containsKey(servletName)){
				servlet=servlets.get(servletName);
			}
			for(int j=0;j<childList.getLength();j++){
				Node cnode=childList.item(j);
				if(cnode.getNodeName().equals("servlet-name")){
					servlet.put("servlet-name", cnode.getTextContent().trim());
				}
				if(cnode.getNodeName().equals("url-pattern")){
					servlet.put("url-pattern", cnode.getTextContent().trim());
				}
				
			}
			servlet.put(servletName, servlet);
		}
	}
	/**
	 * �������е�servlet��Ԫ��
	 * ����:2015-5-19
	 * ����:LM
	 * @param document
	 */
	public void getFilter(Document document){
		NodeList nodeList=document.getElementsByTagName("filter");
		for(int i=0;i<nodeList.getLength();i++){
			Map filter=new HashMap();
			Node node=nodeList.item(i);
			String filterName=((Element)node).getElementsByTagName("filter-name").item(0).getTextContent().trim();
			String filterClass=((Element)node).getElementsByTagName("filter-class").item(0).getTextContent().trim();
			NodeList initParamEle=((Element)node).getElementsByTagName("init-param");
			
			if(filters.containsKey(filterName)){
				filter=filters.get(filterName);
			}
			filter.put("filter-name", filterName);
			filter.put("filter-class", filterClass);
			if(initParamEle.getLength()>0){
				Element initParam=(Element)initParamEle.item(0);
				NodeList paramName=initParam.getElementsByTagName("param-name");
				NodeList paramValue=initParam.getElementsByTagName("param-value");
				Map initMap=new HashMap();
				for(int k=0;k<paramName.getLength();k++){
					initMap.put(paramName.item(k).getTextContent().trim(), paramValue.item(k).getTextContent().trim());
				}
				filter.put("init-param", initMap);
			}
			filters.put(filterName, filter);
		}
	}
	/**
	 * ����filter-mapping��Ԫ��
	 *    url-pattern �����ж��
	 *    dispatcher �����ж��
	 *  ���urlpattern��ʽ1��  
	 *    <filter-mapping>
	 *       <filter-name>test</filter-name>
	 *       <url-pattern>/test</url-pattern>
	 *       <url-pattern>/t</url-pattern>
	 *   </filter-mapping>    
	 *       
	 *  ���urlpattern��ʽ2��
	 *     <filter-mapping>
	 *       <filter-name>test</filter-name>
	 *       <url-pattern>/test</url-pattern>
	 *   </filter-mapping>       
	 *    <filter-mapping>
	 *       <filter-name>test</filter-name>
	 *       <url-pattern>/t</url-pattern>
	 *   </filter-mapping>
	 *    
	 * ����:2015-5-19
	 * ����:LM
	 * @param document
	 */
	public void getFilterMapping(Document document){
		NodeList nodeList=document.getElementsByTagName("filter-mapping");
		for(int i=0;i<nodeList.getLength();i++){
			Map filter=new HashMap();
			Node node=nodeList.item(i);
			NodeList childList=node.getChildNodes();
			String filterName=((Element)node).getElementsByTagName("filter-name").item(0).getTextContent().trim();
			filterName=filterName.trim();
			if(filters.containsKey(filterName)){
				filter=filters.get(filterName);
			}
			List dispatcherList=new ArrayList();
			for(int j=0;j<childList.getLength();j++){
				Node cnode=childList.item(j);
				if(cnode.getNodeName().equals("filter-name")){
					filter.put("filter-name", cnode.getTextContent().trim());
				}
				if(cnode.getNodeName().equals("servlet-name")){
					filter.put("servlet-name", cnode.getTextContent().trim());
				}
				
				if(cnode.getNodeName().equals("url-pattern")){
					//����url-pattern��һ������ �п���֮ǰ�Ѿ������
					List urlList=new ArrayList();
					//����������ȡ����
					if(filter.containsKey("url-pattern")){
						urlList=(List)filter.get("url-pattern");
					}
					if(!urlList.contains(cnode.getTextContent().trim())){
						urlList.add(cnode.getTextContent().trim());
					}
					filter.put("url-pattern", urlList);
				}
				if(cnode.getNodeName().equals("dispatcher")){
					dispatcherList.add(cnode.getTextContent().trim());
				}
			}
			if(dispatcherList.size()==0){
				dispatcherList.add("REQUEST");
			}
			filter.put("dispatcher", dispatcherList);
			Object urlObj=filter.get("url-pattern");
			List url=null;
			if(urlObj==null){
				Object servletObj=filter.get("servlet-name");
				if(servletObj==null){
					continue;
				}
				String servletName=servletObj.toString();
				Object so=servlets.get(servletName);
				if(so==null){
					continue;
				}
				Map soMap=(Map)so;
				url=(List)soMap.get("url-pattern");
			}else{
				url=(List)urlObj;
			}
			filter.put("url-pattern", url);
			filters.put(filterName, filter);
		}
	}
	/**
	 * ����listener��Ԫ��
	 * ����:2015-5-19
	 * ����:LM
	 * @param document
	 */
	public void getListeners(Document document){
		NodeList nodeList=document.getElementsByTagName("listener");
		for(int i=0;i<nodeList.getLength();i++){
			Node node=nodeList.item(i);
			String listener=((Element)node).getElementsByTagName("listener-class").item(0).getTextContent().trim();
			listeners.add(listener);
		}
	}
	/**
	 * ����listener��Ԫ��
	 * ����:2015-5-19
	 * ����:LM
	 * @param document
	 */
	public void getSessionConfig(Document document){
		NodeList nodeList=document.getElementsByTagName("session-config");
		for(int i=0;i<nodeList.getLength();i++){
			Node node=nodeList.item(i);
			String sessionTimeOutStr=((Element)node).getElementsByTagName("session-timeout").item(0).getTextContent().trim();
			sessionTimeOut=Integer.parseInt(sessionTimeOutStr);
		}
	}
	/**
	 * ����mini����
	 * ����:2015-5-19
	 * ����:LM
	 * @param document
	 */
	public void getMiniType(Document document){
		NodeList nodeList=document.getElementsByTagName("mime-mapping");
		for(int i=0;i<nodeList.getLength();i++){
			Node node=nodeList.item(i);
			String extension=((Element)node).getElementsByTagName("extension").item(0).getTextContent().trim();
			String mimeType=((Element)node).getElementsByTagName("mime-type").item(0).getTextContent().trim();
			Map map=new HashMap();
			map.put("extension", extension);
			map.put("mimeType", mimeType);
			mimeMapping.add(map);
		}
	}
	/**
	 * �����쳣ҳ��
	 * ����:2015-5-19
	 * ����:LM
	 * @param document
	 */
	public void getErrorInfo(Document document){
		NodeList nodeList=document.getElementsByTagName("error-page");
		for(int i=0;i<nodeList.getLength();i++){
			Node node=nodeList.item(i);
			Map map=new HashMap();
			if(((Element)node).getElementsByTagName("error-code").getLength()>0){
				String errorCode=((Element)node).getElementsByTagName("error-code").item(0).getTextContent().trim();
				map.put("errorCode", errorCode);
			}
			if(((Element)node).getElementsByTagName("exception-type").getLength()>0){
				String exceptionType=((Element)node).getElementsByTagName("exception-type").item(0).getTextContent().trim();
				map.put("exceptionType", exceptionType);
			}
			String location=((Element)node).getElementsByTagName("location").item(0).getTextContent().trim();
			map.put("location", location);
			errorPage.add(map);
		}
	}
	/**
	 * ������ӭҳ��
	 * ����:2015-5-19
	 * ����:LM
	 * @param document
	 */
	public void getWelcome(Document document){
		NodeList nodeList=document.getElementsByTagName("welcome-file-list");
		for(int i=0;i<nodeList.getLength();i++){
			Node node=nodeList.item(i);
			NodeList fileNode=((Element)node).getElementsByTagName("welcome-file");
			for(int j=0;j<nodeList.getLength();j++){
				welcomeFileList.add(fileNode.item(j).getTextContent().trim());
			}
		}
	}
	public Map<String, String> getContextParam() {
		return contextParam;
	}
	public void setContextParam(Map<String, String> contextParam) {
		this.contextParam = contextParam;
	}
	public List<String> getListeners() {
		return listeners;
	}
	public void setListeners(List<String> listeners) {
		this.listeners = listeners;
	}
	public Map<String, Map> getFilters() {
		return filters;
	}
	public void setFilters(Map<String, Map> filters) {
		this.filters = filters;
	}
	public int getSessionTimeOut() {
		return sessionTimeOut;
	}
	public void setSessionTimeOut(int sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}
	public List<Map<String, String>> getErrorPage() {
		return errorPage;
	}
	public void setErrorPage(List<Map<String, String>> errorPage) {
		this.errorPage = errorPage;
	}
	public List<Map<String, String>> getMimeMapping() {
		return mimeMapping;
	}
	public void setMimeMapping(List<Map<String, String>> mimeMapping) {
		this.mimeMapping = mimeMapping;
	}
	public String getContextDir() {
		return contextDir;
	}
	public void setContextDir(String contextDir) {
		this.contextDir = contextDir;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public Map<String, Map> getServlets() {
		return servlets;
	}
	public void setServlets(Map<String, Map> servlets) {
		this.servlets = servlets;
	}
}
