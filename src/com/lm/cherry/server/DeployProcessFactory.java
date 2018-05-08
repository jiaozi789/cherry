package com.lm.cherry.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.server.javaee.J2EELoader;
import com.lm.cherry.tool.SystemUtils;
/**
 * 初始化根据配置的发布目录 创建deploy对象 并缓存
 * 作者:LM
 * 创建日期:2014-12-31下午05:25:02
 * 项目名称:cherry
 * 包名称:com.lm.cherry.server
 * 文件名:DeployProcessFactory.java
 */
public class DeployProcessFactory {
	private static final String SEPARATE_STR2 = "\\\\{1,}";
	private static final String SEPARATE_STR1 = "/{1,}";
	private static final String WEB_INF_WEB_XML = "/WEB-INF/web.xml";
	private static final String PATH_PREFIX = "/";
	/**
	 * 参数的开始和结束符 比如 %abc%
	 */
	private static final String PARAM_FLAG = "%";
	private HttpServer server;
	public DeployProcessFactory(HttpServer server){
		this.server=server;
	}
	/**
	 * 发布的应用缓存
	 */
	public static  List<Entry.App> eas=new ArrayList<Entry.App>();
	public static List<Entry.BackApp> ebs=new ArrayList<Entry.BackApp>();
	public  static List<Entry.J2EEApp> j2eeapps=new ArrayList<Entry.J2EEApp>();
	/**
	 * 将某个目录发布
	 * 作者:LM
	 * 创建日期:2014-12-31下午05:25:39
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
	 * 将某个目录发布
	 * 作者:LM
	 * 创建日期:2014-12-31下午05:25:39
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
		//这里发布的应用也有可能是个静态的网页 而不是一个标准的web应用 这时判断 WEB-INF和web.xml是否存在
		String realDir=ea.getDir();
		File dirFile=new File(realDir+WEB_INF_WEB_XML);
		//发布j2ee应用
		if(dirFile.exists()){
			J2EELoader j2eeLoader=new J2EELoader(ea,parent,server);
			j2eeLoader.load();
			ea.setJ2eeLoder(j2eeLoader);
			j2eeapps.add(ea);
		//发布静态网页应用
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
	 * 将某个代理发布
	 * 作者:LM
	 * 创建日期:2014-12-31下午05:25:39
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
	 * 通过路径获取app
	 * 作者:LM
	 * 创建日期:2015-1-5下午02:06:53
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
	 * 获取请求的文件的路径 比如请求的路径 可能/mdm /mdm/index.html
	 * 作者:LM
	 * 创建日期:2015-1-5下午06:13:24
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
