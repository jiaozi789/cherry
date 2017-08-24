package com.lm.cherry.test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

import com.lm.cherry.tool.CherryClassLoader;



public class TestClassLoader {
	public static void main(String[] args) throws Exception {
		AClassLoader u=new AClassLoader(
					new URL[]{new File("E:/workspace/cherry/lib/jsp-api.jar").toURL(),
					new File("E:/workspace/cherry/lib/servlet-api.jar").toURL()
			},Thread.currentThread().getContextClassLoader());
		    Thread.currentThread().setContextClassLoader(u);
			u.addClassPath("E:/workspace/cherry/lib");
			Class c=u.loadClass("javax.servlet.Filter");
			System.out.println(c.getClassLoader());
			InputStream is=c.getClassLoader().getResourceAsStream("a.txt");
			System.out.println(is);
			System.out.println(c.getClassLoader().getResourceAsStream("a.txt"));
			System.out.println(c.getClassLoader() instanceof URLClassLoader);
//		CherryClassLoader u=new CherryClassLoader(
//				new URL[]{new File("E:/workspace/cherry/lib/jsp-api.jar").toURL(),
//				new File("E:/workspace/cherry/lib/servlet-api.jar").toURL()
//		},TestClassLoader.class.getClassLoader());
//		u.addClassPath("E:/workspace/cherry/lib");
//		Class c=u.loadClass("javax.servlet.Filter");
//		InputStream is=c.getClassLoader().getResourceAsStream("a.txt");
//		int i=0;
//		System.out.println(is);
	}
	    
}