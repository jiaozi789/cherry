package com.lm.cherry.server;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.conf.WorkConfig;
import com.lm.cherry.tool.CherryClassLoader;
import com.lm.cherry.tool.WarUtils;
/**
 * 啟動http服務器
 * 作者:LM
 * 创建日期:2015-1-29上午11:15:32
 * 项目名称:cherry
 * 包名称:com.lm.cherry.server.html
 * 文件名:HtmlHttpServer.java
 */
public class J2EEHttpServer extends HttpServer{
	/**
	 * window下路径分隔符和linux下的路径分隔符
	 */
	private static final String PATH_SEP_WINDOW_STR = "\\";
	private static final String PATH_SEP_STR = "/";
	/**
	 * 扫描的对应的包后缀
	 */
	private static final String WAR = ".war";
	/**
	 * lib库的目录 需要用到apache的 jspc 
	 */
	private static final String LIB = "/lib";
	private ClassLoader pubClassLoader;
	/**
	 * 加载默认的javaee app路径
	 */
	public static final String defaultAppPath="/app";
	public ClassLoader getPubClassLoader() {
		return pubClassLoader;
	}
	public void setPubClassLoader(ClassLoader pubClassLoader) {
		this.pubClassLoader = pubClassLoader;
	}
	public J2EEHttpServer(int port, Entry entry) throws MalformedURLException, Exception {
		super(port, entry);
		pubClassLoader=new CherryClassLoader.CommonClassLoader(new File(WorkConfig.getWorkConfPath()+LIB).toURI().toURL());
		((CherryClassLoader.CommonClassLoader)pubClassLoader).loadCommonLibrary(WorkConfig.getWorkConfPath()+LIB);
	}
	/**
	 * 发布app
	 * 作者:LM
	 * 创建日期:2015-1-5下午02:08:25
	 * @throws Exception 
	 */
	public void deployApp() throws Exception{
		 List<Entry.J2EEApp> lea=getEntry().getJ2eeapps();
		 for(Entry.J2EEApp ea : lea){
			 deployApp(ea);
		 }
		 deployDirApp();
	}
	/**
	 * 发布某个j2eeApp对象
	 * @param ea
	 * @throws Exception
	 */
	private void deployApp(Entry.J2EEApp ea) throws Exception{
		long startTime=new Date().getTime();
		 getLog().info("开始发布应用："+ea.getPath()+" "+ea.getDir()+"");
		 getDeployFactory().processDeploy(ea,pubClassLoader);
		 long endTime=new Date().getTime();
		 long useTime=(endTime-startTime)/1000;
		 getLog().info("发布应用："+ea.getPath()+" "+ea.getDir()+"成功");
		 getLog().info("用时："+useTime+"(秒)");
	}
	/**
	 * 发布app目录下的所有应用
	 * @throws Exception 
	 */
	public void deployDirApp() throws Exception{
		String cherryHome=WorkConfig.getWorkConfPath();
		String appHome=cherryHome+defaultAppPath;
		File dir=new File(appHome);
		File[] listFile=dir.listFiles();
		for(File file:listFile){
			if(file.isDirectory()){
				Entry.J2EEApp ea=new Entry.J2EEApp();
				ea.setId(file.getName());
				ea.setDir(file.getAbsolutePath());
				ea.setPath(PATH_SEP_STR+file.getName());
				deployApp(ea);
			}else{
				if(file.isFile() && file.getName().endsWith(WAR)){
					String fileName=file.getName().split(PATH_SEP_WINDOW_STR+WAR)[0];
					String destDir=appHome+PATH_SEP_STR+fileName;
					WarUtils.unWar(file, new File(destDir));
					Entry.J2EEApp ea=new Entry.J2EEApp();
					ea.setId(fileName);
					ea.setDir(destDir);
					ea.setPath(PATH_SEP_STR+fileName);
					deployApp(ea);
				}
			}
		}
		
		
		
	}
}
