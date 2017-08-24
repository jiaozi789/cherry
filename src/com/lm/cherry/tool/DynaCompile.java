package com.lm.cherry.tool;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;
/**
 * ��̬�����ļ� �����ڴ��ַ�������
 * ����:LM
 * ��������:2015-1-29����09:51:06
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.util
 * �ļ���:DynaCompile.java
 */
public class DynaCompile {
	/**
	 * ��̬����javaԴ�ļ��� ĳ��Ŀ¼
	 * ����:LM
	 * ��������:2015-1-29����09:51:26
	 * @param sourceFilePath  Դ�ļ���·��
	 * @param outPutPath ���class��Ŀ¼
	 * @return
	 */
	public static boolean compileFile(String sourceFilePath,String outPutPath){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Iterable options = Arrays.asList("-d", outPutPath); 
		Iterable<? extends JavaFileObject> sourcefiles = fileManager.getJavaFileObjects(sourceFilePath);
	    CompilationTask task = compiler.getTask(null, fileManager, null, options, null, sourcefiles);
	    boolean result = task.call();
	    return result;
	}
	/**
	 * ��̬�����ڴ��е������ ����
	 * public class A{
	 * 		public void aaa(){
	 * 			System.out.print("ggg");
	 *      }
	 * }
	 * ����:LM
	 * ��������:2015-1-29����10:00:28
	 * @param name ���� ���� A
	 * @param content Դ���� ��������������������
	 * @param outPutPath �����class�Ĵ��Ŀ¼
	 * @return
	 */
	public static boolean compileMemoryClass(String name,final String content,String outPutPath){
		 JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	     StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
	     SimpleJavaFileObject sourceObject=new SimpleJavaFileObject(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension), Kind.SOURCE){
	    	  public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
	 	         return content;
	 	      }
	     };
	     Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(sourceObject);
	     Iterable options = Arrays.asList("-d", outPutPath); 
	     CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObjects);
	     boolean result = task.call();
	     return result;
	}
}
