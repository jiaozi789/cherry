package com.lm.cherry.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
/**
 * �������
 * ������Ҫ���ǵ�һ���� ��Ϊ���صķ�����libĿ¼��Ӧ�õ�web-inf�µ�class��lib�� ����ͨ��CherryClassLoader���ص�
 * ��������Ӧ�÷������ϵ���Щ������ͨ��sun.misc.Launcher.AppClassLoader ���Ա���ȡ����ʵ����classĿ¼�µ�Filter���� ���ܹ�ǿ��ת����Filter
 * ���� Filter filter=CherryClassLoader.loadClass("javax.servlet.Filter").newInstance(); �����Ǵ����CherryClassLoader.loadClass("javax.servlet.Filter")��ͨ��CherryClassLoader���ص�
 * ��Filter��class��ͨ��AppClassLoader���صĲ�ͬ����������صĶ���ʱ���ܹ�����ת���� ��Ϊ������J2EEHttpServer�����pubClassLoader�ĸ�classloader�ǿյ�Ҳ�Ͳ���AppClassLoader��
 * ����Ϊʲô����AppClassLoader ��Ϊ����û�취 ���ص���·������classpath·����ʹ�����޷���ȡ��Դ�ļ� ��������ʹ�÷��������ƶ�������
 * Object obj=CherryClassLoader.loadClass("javax.servlet.Filter").newInstance();
 * ͨ���������obj
 * ����:2015-5-19
 * ����:LM
 */
public class CherryClassLoader extends URLClassLoader {
	/**
	 * ������libĿ¼�µ�����jar�ļ���loader
	 * ����:2015-5-19
	 * ����:LM
	 */
	public static class CommonClassLoader extends URLClassLoader{
		public CommonClassLoader(URL[] classUrl) throws Exception {
			super(classUrl);
		}
		public CommonClassLoader(URL classUrl) throws Exception {
			this(new URL[]{classUrl});
		}
		public CommonClassLoader(URL classUrl,ClassLoader parent) throws Exception {
			super(new URL[]{classUrl},parent);
		}
		public void loadCommonLibrary(String path) throws MalformedURLException{
			File dir=new File(path);
			if(dir.isDirectory()){
				for(int i=0;i<dir.listFiles().length;i++){
					File tmpFile=dir.listFiles()[i];
					loadCommonLibrary(tmpFile.getAbsolutePath());
				}
			}else{
				if(dir.getAbsolutePath().endsWith(".jar")){
					this.addURL(dir.toURI().toURL());
				}
			}
		}

	}
	public CherryClassLoader(URL[] classUrl,ClassLoader parent) throws Exception {
		super(classUrl,parent);
	}
	public CherryClassLoader(URL classUrl) throws Exception {
		super(new URL[]{classUrl});
	}
	public CherryClassLoader(URL classUrl,ClassLoader parent) throws Exception {
		this(new URL[]{classUrl},parent);
	}
	public CherryClassLoader(URL classUrl,ClassLoader parent,String extLib) throws Exception {
		this(new URL[]{classUrl},parent);
		loadCommonLibrary(extLib);
	}
	public void deleteClass(String cls) throws ClassNotFoundException{
		Class clas=this.loadClass(cls);
		if(clas!=null){
			this.resolveClass(clas);
		}
	}
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException{  
		return (loadClass(name, true));
    }  
	public void loadCommonLibrary(String path) throws MalformedURLException{
		File dir=new File(path);
		if(dir.isDirectory()){
			for(int i=0;i<dir.listFiles().length;i++){
				File tmpFile=dir.listFiles()[i];
				loadCommonLibrary(tmpFile.getAbsolutePath());
			}
		}else{
			if(dir.getAbsolutePath().endsWith(".jar")){
				this.addURL(dir.toURI().toURL());
			}
		}
	}
	public void addClassPath(String dir) throws MalformedURLException{
		this.addURL(new File(dir).toURI().toURL());
	}

}

