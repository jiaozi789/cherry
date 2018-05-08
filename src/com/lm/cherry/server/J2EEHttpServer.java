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
 * ����http������
 * ����:LM
 * ��������:2015-1-29����11:15:32
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.server.html
 * �ļ���:HtmlHttpServer.java
 */
public class J2EEHttpServer extends HttpServer{
	/**
	 * window��·���ָ�����linux�µ�·���ָ���
	 */
	private static final String PATH_SEP_WINDOW_STR = "\\";
	private static final String PATH_SEP_STR = "/";
	/**
	 * ɨ��Ķ�Ӧ�İ���׺
	 */
	private static final String WAR = ".war";
	/**
	 * lib���Ŀ¼ ��Ҫ�õ�apache�� jspc 
	 */
	private static final String LIB = "/lib";
	private ClassLoader pubClassLoader;
	/**
	 * ����Ĭ�ϵ�javaee app·��
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
	 * ����app
	 * ����:LM
	 * ��������:2015-1-5����02:08:25
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
	 * ����ĳ��j2eeApp����
	 * @param ea
	 * @throws Exception
	 */
	private void deployApp(Entry.J2EEApp ea) throws Exception{
		long startTime=new Date().getTime();
		 getLog().info("��ʼ����Ӧ�ã�"+ea.getPath()+" "+ea.getDir()+"");
		 getDeployFactory().processDeploy(ea,pubClassLoader);
		 long endTime=new Date().getTime();
		 long useTime=(endTime-startTime)/1000;
		 getLog().info("����Ӧ�ã�"+ea.getPath()+" "+ea.getDir()+"�ɹ�");
		 getLog().info("��ʱ��"+useTime+"(��)");
	}
	/**
	 * ����appĿ¼�µ�����Ӧ��
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
