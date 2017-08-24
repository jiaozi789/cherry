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
 * 类加载器
 * 这里需要考虑的一点是 因为加载的服务器lib目录和应用的web-inf下的class和lib下 都是通过CherryClassLoader加载的
 * 但是我们应用服务器上的这些代码是通过sun.misc.Launcher.AppClassLoader 所以比如取到了实例了class目录下的Filter对象 不能够强制转换成Filter
 * 比如 Filter filter=CherryClassLoader.loadClass("javax.servlet.Filter").newInstance(); 这样是错误的CherryClassLoader.loadClass("javax.servlet.Filter")是通过CherryClassLoader加载的
 * 而Filter的class是通过AppClassLoader加载的不同类加载器加载的对象时不能够互相转换的 因为我们在J2EEHttpServer定义的pubClassLoader的父classloader是空的也就不是AppClassLoader了
 * 这里为什么不用AppClassLoader 因为我们没办法 加载的类路径加入classpath路径而使程序无法获取资源文件 所以我们使用反射来控制对象例如
 * Object obj=CherryClassLoader.loadClass("javax.servlet.Filter").newInstance();
 * 通过反射操作obj
 * 日期:2015-5-19
 * 作者:LM
 */
public class CherryClassLoader extends URLClassLoader {
	/**
	 * 容器的lib目录下的所有jar的加载loader
	 * 日期:2015-5-19
	 * 作者:LM
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

