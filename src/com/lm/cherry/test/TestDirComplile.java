package com.lm.cherry.test;

import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public abstract class TestDirComplile {

	/**
	 * 作者:LM
	 * 创建日期:2015-1-23下午05:25:08
	 * @param args
	 */
	public static void main(String[] args) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Iterable options = Arrays.asList("-d", "C:/TEMP"); 
		Iterable<? extends JavaFileObject> sourcefiles = fileManager.getJavaFileObjects("C:\\TEMP\\test\\TestComplile.java");
	    CompilationTask task = compiler.getTask(null, fileManager, null, options, null, sourcefiles);
	    boolean result = task.call();
	    if (result) {
	         System.out.println("编译成功。");
	    }
	}


}
