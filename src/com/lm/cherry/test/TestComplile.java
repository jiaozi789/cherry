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
	       *  -g                         �������е�����Ϣ
			  -g:none                    �������κε�����Ϣ
			  -g:{lines,vars,source}     ֻ����ĳЩ������Ϣ
			  -nowarn                    �������κξ���
			  -verbose                   ����йر���������ִ�еĲ�������Ϣ
			  -deprecation               ���ʹ���ѹ�ʱ�� API ��Դλ��
			  -classpath <·��>            ָ�������û����ļ���ע�ʹ�������λ��
			  -cp <·��>                   ָ�������û����ļ���ע�ʹ�������λ��
			  -sourcepath <·��>           ָ����������Դ�ļ���λ��
			  -bootclasspath <·��>        �����������ļ���λ��
			  -extdirs <Ŀ¼>              ���ǰ�װ����չĿ¼��λ��
			  -endorseddirs <Ŀ¼>         ����ǩ���ı�׼·����λ��
			  -proc:{none,only}          �����Ƿ�ִ��ע�ʹ����/����롣
			  -processor <class1>[,<class2>,<class3>...]Ҫ���е�ע�ʹ����������ƣ��ƹ�Ĭ��
			����������
			  -processorpath <·��>        ָ������ע�ʹ�������λ��
			  -d <Ŀ¼>                    ָ��������ɵ����ļ���λ��
			  -s <Ŀ¼>                    ָ��������ɵ�Դ�ļ���λ��
			  -implicit:{none,class}     ָ���Ƿ�Ϊ��ʽ�����ļ��������ļ�
			  -encoding <����>             ָ��Դ�ļ�ʹ�õ��ַ�����
			  -source <�汾>               �ṩ��ָ���汾��Դ������
			  -target <�汾>               �����ض� VM �汾�����ļ�
			  -version                   �汾��Ϣ
			  -help                      �����׼ѡ�����Ҫ
			  -Akey[=value]              ���ݸ�ע�ʹ�������ѡ��
			  -X                         ����Ǳ�׼ѡ�����Ҫ
			  -J<��־>                     ֱ�ӽ� <��־> ���ݸ�����ʱϵͳ
	       */
	      Iterable options = Arrays.asList("-d", "C:/TEMP"); 
	      CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObjects);
	      boolean result = task.call();
	      if (result) {
	         System.out.println("����ɹ���");
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
