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
 * 动态编译文件 或者内存字符串的类
 * 作者:LM
 * 创建日期:2015-1-29上午09:51:06
 * 项目名称:cherry
 * 包名称:com.lm.cherry.util
 * 文件名:DynaCompile.java
 */
public class DynaCompile {
	/**
	 * 动态编译java源文件到 某个目录
	 * 作者:LM
	 * 创建日期:2015-1-29上午09:51:26
	 * @param sourceFilePath  源文件的路径
	 * @param outPutPath 输出class的目录
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
	 * 动态编译内存中的类代码 比如
	 * public class A{
	 * 		public void aaa(){
	 * 			System.out.print("ggg");
	 *      }
	 * }
	 * 作者:LM
	 * 创建日期:2015-1-29上午10:00:28
	 * @param name 类名 比如 A
	 * @param content 源代码 比如上面比如的所有内容
	 * @param outPutPath 编译的class的存放目录
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
