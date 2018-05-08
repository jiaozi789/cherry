package com.lm.cherry.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.server.javaee.J2EELoader;
import com.lm.cherry.tool.SystemUtils;
/**
 * ��ʼ���������õķ���Ŀ¼ ����deploy���� ������
 * ����:LM
 * ��������:2014-12-31����05:25:02
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.server
 * �ļ���:DeployProcessFactory.java
 */
public class DeployProcessFactory {
	private static final String SEPARATE_STR2 = "\\\\{1,}";
	private static final String SEPARATE_STR1 = "/{1,}";
	private static final String WEB_INF_WEB_XML = "/WEB-INF/web.xml";
	private static final String PATH_PREFIX = "/";
	/**
	 * �����Ŀ�ʼ�ͽ����� ���� %abc%
	 */
	private static final String PARAM_FLAG = "%";
	private HttpServer server;
	public DeployProcessFactory(HttpServer server){
		this.server=server;
	}
	/**
	 * ������Ӧ�û���
	 */
	public static  List<Entry.App> eas=new ArrayList<Entry.App>();
	public static List<Entry.BackApp> ebs=new ArrayList<Entry.BackApp>();
	public  static List<Entry.J2EEApp> j2eeapps=new ArrayList<Entry.J2EEApp>();
	/**
	 * ��ĳ��Ŀ¼����
	 * ����:LM
	 * ��������:2014-12-31����05:25:39
	 * @param dir
	 */
	public void processDeploy(Entry.App ea){	
		
		List<Entry.Dir> dirs=server.getEntry().getDir();
		for(Entry.Dir ed:dirs){
			String dir=ea.getDir().replaceAll(PARAM_FLAG+ed.getName()+PARAM_FLAG, ed.getPath());
			ea.setDir(dir);
		}
		eas.add(ea);
	}
	/**
	 * ��ĳ��Ŀ¼����
	 * ����:LM
	 * ��������:2014-12-31����05:25:39
	 * @param dir
	 * @throws Exception 
	 */
	public void processDeploy(Entry.J2EEApp ea,ClassLoader parent) throws Exception{	
		List<Entry.Dir> dirs=server.getEntry().getDir();
		for(Entry.Dir ed:dirs){
			String dir=ea.getDir().replaceAll(PARAM_FLAG+ed.getName()+PARAM_FLAG, ed.getPath());
			dir=dir.replaceAll(SEPARATE_STR2, PATH_PREFIX).replaceAll(SEPARATE_STR1, PATH_PREFIX);
			ea.setDir(dir);
		}
		if(ea.getDir().startsWith(PATH_PREFIX)){
			if(SystemUtils.isEmpty(ea.getPath())){
				String[] split=ea.getDir().split(PATH_PREFIX);
				String contextPath=PATH_PREFIX+split[split.length-1];
				ea.setPath(contextPath);
			}
		}
		//���﷢����Ӧ��Ҳ�п����Ǹ���̬����ҳ ������һ����׼��webӦ�� ��ʱ�ж� WEB-INF��web.xml�Ƿ����
		String realDir=ea.getDir();
		File dirFile=new File(realDir+WEB_INF_WEB_XML);
		//����j2eeӦ��
		if(dirFile.exists()){
			J2EELoader j2eeLoader=new J2EELoader(ea,parent,server);
			j2eeLoader.load();
			ea.setJ2eeLoder(j2eeLoader);
			j2eeapps.add(ea);
		//������̬��ҳӦ��
		}else{
			Entry.App eaHtml=new Entry.App();
			eaHtml.setDir(ea.getDir());
			eaHtml.setId(ea.getId());
			eaHtml.setIndex(ea.getIndex());
			eaHtml.setPath(ea.getPath());
			eas.add(eaHtml);
		}
	}
	/**
	 * ��ĳ��������
	 * ����:LM
	 * ��������:2014-12-31����05:25:39
	 * @param dir
	 */
	public static void processDeploy(Entry.BackApp ea){		
		ebs.add(ea);
	}
	public static Object searchAppByContextPath(String context){
		for(Entry.App ea :eas){
			if(ea.getPath().equals(context)){
				return ea;
			}
		}
		for(Entry.BackApp eb :ebs){
			if(eb.getPath().equals(context)){
				return eb;
			}
		}
		for(Entry.J2EEApp eb :j2eeapps){
			if(eb.getPath().equals(context)){
				return eb;
			}
		}
		return null;
	}
	/**
	 * ͨ��·����ȡapp
	 * ����:LM
	 * ��������:2015-1-5����02:06:53
	 * @param path
	 * @return
	 */
	public static Object searchApp(String path){
		for(Entry.App ea :eas){
			if(path.startsWith(ea.getPath()+PATH_PREFIX) || path.equals(ea.getPath())){
				return ea;
			}
		}
		for(Entry.BackApp eb :ebs){
			if(eb.getPath().equals(PATH_PREFIX)){
				return eb;
			}
			if(path.startsWith(eb.getPath()+PATH_PREFIX) || path.equals(eb.getPath())){
				return eb;
			}
		}
		for(Entry.J2EEApp eb :j2eeapps){
			if(eb.getPath().equals(PATH_PREFIX)){
				return eb;
			}
			if(path.startsWith(eb.getPath()+PATH_PREFIX) || path.equals(eb.getPath())){
				return eb;
			}
		}
		return null;
	}
	/**
	 * ��ȡ������ļ���·�� ���������·�� ����/mdm /mdm/index.html
	 * ����:LM
	 * ��������:2015-1-5����06:13:24
	 * @param url
	 * @return
	 */
	public String getRequestFile(String url){
		Object obj=searchApp(url);
		if(obj instanceof Entry.App){
			Entry.App app=(Entry.App)obj;
			String path=app.getPath();
			String dir=app.getDir();
			String file=dir;
			if(url.equals(path)){
				file+=PATH_PREFIX+app.getIndex();
			}else{
				String pathNext=url.split(path)[1];
				if(PATH_PREFIX.equals(pathNext) || "".equals(pathNext)){
					file+=PATH_PREFIX+app.getIndex();
				}else{
					file+=PATH_PREFIX+pathNext;
				}
			}
			return file;
		}else if(obj instanceof Entry.J2EEApp){
			Entry.J2EEApp app=(Entry.J2EEApp)obj;
			String path=app.getPath();
			String dir=app.getDir();
			String file=dir;
			if(url.equals(path)){
				file+=PATH_PREFIX+app.getIndex();
			}else{
				int startPos=url.indexOf(path)+path.length();
				String pathNext=url.substring(startPos);
				if(PATH_PREFIX.equals(pathNext) || "".equals(pathNext)){
					if(app.getIndex()!=null){
						file+=PATH_PREFIX+app.getIndex();
					}
				}else{
					file+=PATH_PREFIX+pathNext;
				}
			}
			return file;
		}
		return null;
	}
}
