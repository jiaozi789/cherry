package com.lm.cherry.tool;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

public class SystemUtils {


	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判�??:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对�??
	 * @return boolean 返回的布尔�??
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj.equals(""))
			return true;
		if (pObj.equals("null"))
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元�??>0)<br>
	 * 实用于对如下对象做判�??:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对�??
	 * @return boolean 返回的布尔�??
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj.equals(""))
			return false;
		if (pObj.equals("null"))
			return false;
		if (pObj.equals(" "))
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 提取完整的错误信息
	 * 
	 * @Time 2014-11-3 下午04:34:03 create
	 * @param ex
	 * @return
	 * @author qq.c
	 */
	public static String getExceptionMsg(Throwable ex){
		StackTraceElement[] err = ex.getStackTrace();
		String msg =("错误详细信息：" + err[0].getClassName() + "." + err[0].getMethodName() + err[0].getLineNumber() + "行出现" + ex.getMessage() + "异常！");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		ex.printStackTrace(new PrintStream(baos));  
		String exception = baos.toString();   
		return msg+exception;
	}
	/**
	 * trim字符串
	 * 日期:2015-5-25
	 * 作者:LM
	 * @return
	 */
	public static String trim(Object str){
		if(str==null){
			return "";
		}
		return str.toString().trim();
	}
	/**
	 * 将路径全部格式为 /格式
	 * 日期:2015-5-27
	 * 作者:LM
	 * @param path
	 * @return
	 */
	public static String encodePath(String path){
		path=path.replaceAll("\\\\{1,}", "/");		
		path=path.replaceAll("/{1,}", "/");
		return path;
	}

}