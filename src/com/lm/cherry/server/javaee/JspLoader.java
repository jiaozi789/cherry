package com.lm.cherry.server.javaee;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspFactory;

import org.apache.jasper.JspC;
import org.apache.jasper.runtime.JspFactoryImpl;
import org.apache.jasper.security.SecurityClassLoad;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.conf.WorkConfig;
import com.lm.cherry.tool.CherryException;

/**
 * 通过配置文件的配置加载app
 * 日期:2015-5-19
 * 作者:LM
 */
public class JspLoader {
	/**
	 * 设置文件上一次被加载的事件 判断文件是否被修改过
	 */
	private static Map<String,Long> jspModifyDate=new HashMap<String,Long>();
	/**
	 * 设置jsp的对象实例
	 */
	private static Map<String,Object> jspObjects=new HashMap<String,Object>();
	/**
	 * 当前已经加载的jsp文件
	 */
	private static Map<String,Jsp> jspList=new HashMap<String,Jsp>();
	
	
	/**
	 * 这里j2eeloader对象
	 */
	private Entry.J2EEApp ea;
	private JspC jspc = null; 
	private boolean ifInit=false;
	/**
	 * 初始化jsp加载器
	 * @param ea
	 * @throws Exception
	 */
	public JspLoader(Entry.J2EEApp ea) throws Exception{
		this.ea=ea;
		this.jspc=new JspC();
	}
	/**
	 * 初始化jsp的处理工厂
	 * @throws Exception
	 */
	public void init() throws Exception{
		if(!ifInit){
			String workPath=WorkConfig.getWorkConfPath();
			String compileOutPath=workPath+"/tmp/"+ea.getPath();
			//讲编辑的jsp的目录加载到类加载路径中
			ea.getJ2eeLoder().getCherryLoder().addClassPath(compileOutPath);
			//设置通过apache的JspFactoryImpl来处理jsp
			JspFactoryImpl factory = new JspFactoryImpl();
		    SecurityClassLoad.securityClassLoad(ea.getJ2eeLoder().getCherryLoder());
		    JspFactory.setDefaultFactory(factory);
		    ifInit=true;
		}
	}
	/**
	 * 加载jsp 编译jsp并且运行
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Object load(String url) throws Exception{
		init();
		String workPath=WorkConfig.getWorkConfPath();//获取web服务器的更目录
		String compileOutPath=workPath+"/tmp/"+ea.getPath();//给web服务器的根目录下 建一个上下文名称的目录
		File file=new File(compileOutPath);
		if(!file.exists()){
			file.mkdirs();
		}
		if(!url.endsWith(".jsp")){//判断请求的url是否已jsp结尾 如果不是抛出异常
			throw new CherryException("非jsp文件不能解析");
		}
		String jspFile=url.substring(url.indexOf(ea.getPath())+ea.getPath().length());;//请求路径中去掉上下文 表示jsp的完整路径
		File oriJsp=new File(ea.getDir()+jspFile);//获取项目的绝对路径加上jsp的路径 就是jsp所在的位置了
		Object jspObject=null;//实例化后的jsp类的对象 
		Jsp jsp=jspList.get(url);
		if(jsp==null){
			jsp=new Jsp(jspFile,ea.getDir());
			jspList.put(url, jsp);
		}
		//添加jsp对象0 防止同时存在两个相同的请求同时访问 jsp的编译问题
		synchronized (jsp) {
			if(jspModifyDate.containsKey(url)){//判断当前url是否已经加载过jsp对象了
				Long preDate=jspModifyDate.get(url);//加载过 取出加载的时间戳
				if(oriJsp.lastModified()>preDate){//如果文件的最后修改日期 大于时间戳 就说明文件被修改过 需要重新编译
					jspc.setUriroot(ea.getDir());//web应用的root目录  
			        jspc.setOutputDir(compileOutPath);//.java文件和.class文件的输出目录  
			        jspc.setJspFiles(jspFile);//要编译的jsp  
			        jspc.setCompile(true);//是否编译 false或不指定的话只生成.java文件  
			        jspc.execute();  
			        jspModifyDate.put(url, oriJsp.lastModified());//被编译后 放入缓存中
				}else{
					jspObject=jspObjects.get(url);//如果没被修改过 直接取出缓存中的jspObject 
				}
			}else{//如果以前没编译过 直接编译 放入缓存中
				jspc.setUriroot(ea.getDir());//web应用的root目录  
		        jspc.setOutputDir(compileOutPath);//.java文件和.class文件的输出目录  
		        jspc.setJspFiles(jspFile);//要编译的jsp  
		        jspc.setCompile(true);//是否编译 false或不指定的话只生成.java文件  
		        jspc.execute();
		        jspModifyDate.put(url, oriJsp.lastModified());
			}
		}
		if(jspObject==null){//如果没有获取到jsp对象 必须通过类加载classloader加载当前jsp被编译后的class类
			String jspClassStr="org.apache.jsp"+jspFile.replace(".", "_").replace("/", ".");
			//URL[] jarUrl=ea.getJ2eeLoder().getServletContext().getResourcePaths(ea.getDir()+"/WEB-INF/lib");
			URLClassLoader ucl=new URLClassLoader(new URL[]{new File(compileOutPath).toURL()},ea.getJ2eeLoder().getCherryLoder());//通过URLClassLoader加载 
			
			Class jspClass=ucl.loadClass(jspClassStr);//加载类
			jspObject=jspClass.newInstance();//实例化对象
			jspObjects.put(url,jspObject);//放置到缓存中 下次 如果没有被修改就直接从缓存读取
		}
		return jspObject;
	}
	
}
