package com.lm.cherry.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class TestComplile {
	   public static void main(String[] args) throws Exception {     
	      String source = "public class Main { public static void main(String[] args) {System.out.println(\"Hello World!\");} }";
	      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	      StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
	      StringSourceJavaObject sourceObject = new TestComplile.StringSourceJavaObject("Main", source);
	      Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(sourceObject);
	      /**
	       *  -g                         生成所有调试信息
			  -g:none                    不生成任何调试信息
			  -g:{lines,vars,source}     只生成某些调试信息
			  -nowarn                    不生成任何警告
			  -verbose                   输出有关编译器正在执行的操作的消息
			  -deprecation               输出使用已过时的 API 的源位置
			  -classpath <路径>            指定查找用户类文件和注释处理程序的位置
			  -cp <路径>                   指定查找用户类文件和注释处理程序的位置
			  -sourcepath <路径>           指定查找输入源文件的位置
			  -bootclasspath <路径>        覆盖引导类文件的位置
			  -extdirs <目录>              覆盖安装的扩展目录的位置
			  -endorseddirs <目录>         覆盖签名的标准路径的位置
			  -proc:{none,only}          控制是否执行注释处理和/或编译。
			  -processor <class1>[,<class2>,<class3>...]要运行的注释处理程序的名称；绕过默认
			的搜索进程
			  -processorpath <路径>        指定查找注释处理程序的位置
			  -d <目录>                    指定存放生成的类文件的位置
			  -s <目录>                    指定存放生成的源文件的位置
			  -implicit:{none,class}     指定是否为隐式引用文件生成类文件
			  -encoding <编码>             指定源文件使用的字符编码
			  -source <版本>               提供与指定版本的源兼容性
			  -target <版本>               生成特定 VM 版本的类文件
			  -version                   版本信息
			  -help                      输出标准选项的提要
			  -Akey[=value]              传递给注释处理程序的选项
			  -X                         输出非标准选项的提要
			  -J<标志>                     直接将 <标志> 传递给运行时系统
	       */
	      Iterable options = Arrays.asList("-d", "C:/TEMP"); 
	      CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObjects);
	      boolean result = task.call();
	      if (result) {
	         System.out.println("编译成功。");
	      }
	      SimpleJavaFileObject ss=new SimpleJavaFileObject(null,null){
	    	  
	      };
	   }
	 
	   static class StringSourceJavaObject extends SimpleJavaFileObject {
	 
	      private String content = null;
	      public StringSourceJavaObject(String name, String content) throws URISyntaxException {
	         super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension), Kind.SOURCE);
	         this.content = content;
	      }
	 
	      public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
	         return content;
	      }
	   }
	}
