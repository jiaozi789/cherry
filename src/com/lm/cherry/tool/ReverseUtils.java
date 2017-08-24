package com.lm.cherry.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
/**
 * ���乤����
 * ����:2015-5-28
 * ����:LM
 */
public class ReverseUtils {
	/**
	 * ִ�ж���ĳ������
	 * ����:2015-5-28
	 * ����:LM
	 * @param obj
	 * @param method
	 * @param param
	 * @param types
	 * @return
	 * @throws ServletException
	 */
	public static Object invoke(Object obj, String method, Object[] param,
			Class... types) throws ServletException {
		try {
			if (types == null) {
				if(param!=null){
					types = new Class[param.length];
					int i = 0;
					for (Object pa : param) {
						types[i] = pa.getClass();
						i++;
					}
				}
			}
			Method meth = obj.getClass().getMethod(method, types);
			meth.setAccessible(true);
			return meth.invoke(obj, param);
		} catch (Exception e) {
			throw new ServletException(SystemUtils.getExceptionMsg(e));
		}

	}
	/**
	 * ��ȡĳ���������Ե�ֵ
	 * ����:2015-5-28
	 * ����:LM
	 * @return
	 * @throws ServletException 
	 */
	public static Object getFieldValue(Object obj,String filedName) throws ServletException{
		try {
			Field f=obj.getClass().getField(filedName);
			f.setAccessible(true);
			return f.get(obj);
		} catch (Exception e) {
			throw new ServletException(SystemUtils.getExceptionMsg(e));
		}
	}
	
	public static  boolean ifInstanceof(Class ocls,String instanceClass){
		if(ocls.getName().equals(instanceClass)){
			return true;
		}else{
			Class superClass=ocls.getSuperclass();
			if(superClass!=null){
				if(superClass.getName().equals(instanceClass)){
					return true;
				}else{
					if(ifInstanceof(superClass,instanceClass)){
						return true;
					}
				}
			}
			
			Class[] interf=ocls.getInterfaces();
			if(interf!=null && interf.length>0){
				for(Class ti:interf)
				{
					if(ti.getName().equals(instanceClass)){
						return true;
					}else{
						if(ifInstanceof(ti,instanceClass)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
